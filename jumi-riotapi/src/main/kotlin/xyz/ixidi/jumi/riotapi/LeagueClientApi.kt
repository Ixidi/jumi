package xyz.ixidi.jumi.riotapi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import xyz.ixidi.jumi.riotapi.gateway.Gateway
import xyz.ixidi.jumi.riotapi.gateway.GatewayEventCaller
import xyz.ixidi.jumi.riotapi.gateway.event.GatewayEvent
import xyz.ixidi.jumi.riotapi.rest.RestClient
import xyz.ixidi.jumi.riotapi.util.fetchProcessInfo
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private const val CLIENT_PROCESS_NAME = "LeagueClientUx.exe"
private const val CLIENT_PROCESS_PORT = "app-port"
private const val CLIENT_PROCESS_TOKEN = "remoting-auth-token"

internal val logger = KotlinLogging.logger { }

class LeagueClientApi internal constructor(
    private val token: String,
    private val port: Int,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob()

    private val client = HttpClient(CIO) {
        CurlUserAgent()
        expectSuccess = false

        Charsets {
            register(Charsets.UTF_8)
        }

        install(JsonFeature) {
            serializer = JacksonSerializer()
        }

        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials("riot", token)
                }
            }
        }

        install(WebSockets)

        engine {
            https {
                //TODO add riot certificate
                trustManager = object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
                }
            }
        }

        defaultRequest {
            url {
                host = "127.0.0.1"
                port = this@LeagueClientApi.port
            }
        }
    }

    val rest = RestClient(client)
    private lateinit var gateway: Gateway
    private lateinit var gatewayEventCaller: GatewayEventCaller

    fun start() {
        gateway = Gateway(client)
        gateway.connect()

        gatewayEventCaller = GatewayEventCaller(gateway.rawEventFlow, jacksonObjectMapper())
        gatewayEventCaller.start()
    }

    fun <E : GatewayEvent> on(clazz: KClass<E>, coroutineScope: CoroutineScope = this, handler: suspend E.() -> Unit) {
        gatewayEventCaller.eventFlow
            .buffer(Channel.UNLIMITED)
            .filter { it::class.isSubclassOf(clazz) }
            .map { it as E }
            .onEach {
                runCatching {
                    handler(it)
                }.onFailure {
                    logger.catching(it)
                }
            }.launchIn(this)
    }

    inline fun <reified E : GatewayEvent> on(coroutineScope: CoroutineScope = this, noinline handler: suspend E.() -> Unit) = on(E::class, coroutineScope, handler)

    fun close() {
        gatewayEventCaller.close()
        gateway.close()
        close()
    }

}

fun startLeagueClientApiFromProcess(): LeagueClientApi {
    if (!System.getProperty("os.name").startsWith("Windows")) throw Exception("Currently only Windows is supported.")

    val flags = fetchProcessInfo(CLIENT_PROCESS_NAME).flags
    val token = flags[CLIENT_PROCESS_TOKEN]!!
    val port = flags[CLIENT_PROCESS_PORT]!!.toInt()
    println("$port $token")

    logger.info { "LeagueClientApi has been created from process." }
    return LeagueClientApi(token, port).apply { start() }
}