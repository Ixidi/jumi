package xyz.ixidi.jumi.riotapi.gateway.event

import xyz.ixidi.jumi.riotapi.entity.LolMatchMakingReadyCheck

data class LolMatchMakingReadyCheckEvent(val entity: LolMatchMakingReadyCheck) : GatewayEvent