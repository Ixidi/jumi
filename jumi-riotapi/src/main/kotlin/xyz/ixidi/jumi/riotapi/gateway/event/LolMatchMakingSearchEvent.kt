package xyz.ixidi.jumi.riotapi.gateway.event

import xyz.ixidi.jumi.riotapi.entity.LolMatchMakingSearch

data class LolMatchMakingSearchEvent(val entity: LolMatchMakingSearch) : GatewayEvent