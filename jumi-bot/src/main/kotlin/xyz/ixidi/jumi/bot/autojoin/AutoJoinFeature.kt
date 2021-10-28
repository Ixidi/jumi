package xyz.ixidi.jumi.bot.autojoin

import io.ktor.http.*
import mu.KotlinLogging
import xyz.ixidi.jumi.bot.feature.Feature
import xyz.ixidi.jumi.bot.feature.FeatureCreator
import xyz.ixidi.jumi.riotapi.gateway.event.LolLobbyInvitationsUpdatedEvent
import xyz.ixidi.jumi.riotapi.rest.RestClient

private val logger = KotlinLogging.logger {  }

class AutoJoinFeature : Feature<AutoJoinFeatureConfig>("autoJoin", AutoJoinFeatureConfig()) {

    override fun FeatureCreator.create(rest: RestClient) {
        on<LolLobbyInvitationsUpdatedEvent> {
            println("1")
            if (invitations.isEmpty()) return@on

            println("2")
            if (!config.joinIfAlreadyInLobby) {
                val response = rest.lobby.getLobby()
                if (response.status != HttpStatusCode.NotFound) {
                    logger.info { "Bot did not join lobby because it is already in another lobby and joinIfAlreadyInLobby is disabled." }
                    return@on
                }
            }

            println("3")
            val invitation = invitations.first()
            when (val c = config.joinCondition) {
                is AutoJoinFeatureConfig.JoinCondition.SpecifiedSummoner -> {
                    if (!c.name.contains(invitation.fromSummonerName)) {
                        logger.info { "Bot did not join lobby because it only accepts invitations from ${c.name}." }
                        return@on
                    }
                }
            }
            println("4")

            rest.lobby.postAcceptInvitation(invitation.invitationId)
            logger.info { "Bot has joined to ${invitation.fromSummonerName}'s lobby." }
        }
    }


}