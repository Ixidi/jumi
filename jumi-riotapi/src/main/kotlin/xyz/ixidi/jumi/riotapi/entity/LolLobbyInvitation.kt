package xyz.ixidi.jumi.riotapi.entity

data class LolLobbyInvitation(
    val canAcceptInvitation: Boolean,
    val fromSummonerId: Long,
    val fromSummonerName: String,
    val gameConfig: GameConfig,
    val invitationId: String,
    val invitationType: String,
    val restrictions: List<Any>,
    val state: String,
    val timestamp: String
) {

    data class GameConfig(
        val gameMode: String,
        val inviteGameType: String,
        val mapId: Int,
        val queueId: Int
    )

}