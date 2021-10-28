package xyz.ixidi.jumi.riotapi.gateway.event

import xyz.ixidi.jumi.riotapi.entity.LolInstantMessageReceived

data class LolInstantMessageReceivedEvent(val entity: LolInstantMessageReceived) : GatewayEvent
