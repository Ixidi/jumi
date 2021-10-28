package xyz.ixidi.jumi.riotapi.gateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import xyz.ixidi.jumi.riotapi.rest.RestClient
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

private val logger = KotlinLogging.logger {  }

class Gateway(
    private val client: HttpClient,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob()

    private lateinit var _rawEventFlow: MutableSharedFlow<RawEvent>
    val rawEventFlow: SharedFlow<RawEvent>
        get() = _rawEventFlow

    var connected = false
    private set

    private val jsonMapper = jacksonObjectMapper()

    fun connect() {
        if (connected) throw Exception("Gateway is already connected.")

        _rawEventFlow = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)
        launch {
            connected = true
            client.wss {
                send("[5, \"OnJsonApiEvent\"]")
                incoming
                    .consumeAsFlow()
                    .buffer(Channel.UNLIMITED)
                    .map { it.readBytes().decodeToString() }
                    .filter { it.isNotBlank() }
                    .map { jsonMapper.readValue(it, Array<Any>::class.java)[2] as HashMap<String, Any> }
                    .map { jsonMapper.convertValue(it, RawEvent::class.java) }
                    .collect { _rawEventFlow.emit(it) }
            }
            connected = false
        }
    }

    fun close() {
        cancel()
        connected = false
    }

}