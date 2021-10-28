package xyz.ixidi.jumi.riotapi.gateway.event

import xyz.ixidi.jumi.riotapi.entity.LolChampionSelectSession

data class LolChampionSelectSessionUpdateEvent(val dto: LolChampionSelectSession) : GatewayEvent