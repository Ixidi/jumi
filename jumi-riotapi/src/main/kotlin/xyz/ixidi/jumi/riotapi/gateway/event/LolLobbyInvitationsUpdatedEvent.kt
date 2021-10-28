package xyz.ixidi.jumi.riotapi.gateway.event

import xyz.ixidi.jumi.riotapi.entity.LolLobbyInvitation

data class LolLobbyInvitationsUpdatedEvent(val invitations: List<LolLobbyInvitation>) : GatewayEvent