package xyz.ixidi.jumi.riotapi.gateway.event

import xyz.ixidi.jumi.riotapi.entity.LolChampionSelectSummoner

data class LolChampionSelectSummonerUpdateEvent(val dto: LolChampionSelectSummoner) : GatewayEvent