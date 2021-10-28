package xyz.ixidi.jumi.riotapi.gateway

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import xyz.ixidi.jumi.riotapi.entity.*
import xyz.ixidi.jumi.riotapi.gateway.event.*
import kotlin.coroutines.CoroutineContext

class GatewayEventCaller(
    private val rawEventFlow: SharedFlow<RawEvent>,
    private val objectMapper: ObjectMapper,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : CoroutineScope {

    private companion object {
        private const val UPDATE_TYPE = "Update"
        private const val CREATE_TYPE = "Create"
    }

    private class Converter(
        val uriPattern: String,
        val types: Set<String>,
        val convert: suspend RawEvent.() -> GatewayEvent
    )

    override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob()

    private lateinit var _eventFlow: MutableSharedFlow<GatewayEvent>
    val eventFlow: SharedFlow<GatewayEvent>
        get() = _eventFlow

    private val converters = ArrayList<Converter>()

    init {
        converter("/lol-game-client-chat/v1/instant-messages", UPDATE_TYPE) {
            LolInstantMessageReceivedEvent(data.convert())
        }

        converter("/lol-lobby/v2/received-invitations", UPDATE_TYPE) {
            val rawArray = data as ArrayList<Any>
            val invitations: List<LolLobbyInvitation> = if (rawArray.isNotEmpty()) {
                rawArray.convert()
            } else {
                emptyList()
            }

            LolLobbyInvitationsUpdatedEvent(invitations)
        }

        converter("/lol-matchmaking/v1/search", CREATE_TYPE, UPDATE_TYPE) {
            LolMatchMakingSearchEvent(data.convert())
        }

        converter("/lol-matchmaking/v1/search", UPDATE_TYPE) {
            LolMatchMakingSearchEvent(data.convert())
        }

        converter("/lol-matchmaking/v1/ready-check", UPDATE_TYPE) {
            LolMatchMakingReadyCheckEvent(data.convert())
        }

        converter("/lol-champ-select/v1/session", UPDATE_TYPE) {
            LolChampionSelectSessionUpdateEvent(data.convert())
        }

        converter("/lol-champ-select/v1/summoners/", UPDATE_TYPE) {
            LolChampionSelectSummonerUpdateEvent(data.convert())
        }
    }

    private fun converter(uriPattern: String, vararg eventTypes: String, convert: suspend RawEvent.() -> GatewayEvent) {
        converters.add(Converter(uriPattern, eventTypes.toSet(), convert))
    }

    fun start() {
        _eventFlow = MutableSharedFlow(extraBufferCapacity = Int.MAX_VALUE)

        rawEventFlow
            .map { raw ->
                converters.firstOrNull {
                    compareUri(it.uriPattern, raw.uri) && it.types.contains(raw.eventType)
                }?.convert?.invoke(raw) ?: UnknownEvent(raw.uri, raw.eventType, raw.data)
            }
            .onEach {  _eventFlow.emit(it) }
            .launchIn(this)
    }

    fun close() {
        cancel()
    }


    private fun compareUri(patternUri: String, uri: String) =
        if (patternUri.endsWith("/")) uri.startsWith(patternUri) else patternUri == uri

    private inline fun <reified T> Any?.convert(): T = objectMapper.convertValue(this!!)

}