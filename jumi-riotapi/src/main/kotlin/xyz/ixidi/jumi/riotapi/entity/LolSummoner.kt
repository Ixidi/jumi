package xyz.ixidi.jumi.riotapi.entity

data class LolSummoner(
    val accountId: Long,
    val displayName: String,
    val internalName: String,
    val nameChangeFlag: Boolean,
    val percentCompleteForNextLevel: Int,
    val profileIconId: Int,
    val puuid: String,
    val rerollPoints: RerollPoints,
    val summonerId: Long,
    val summonerLevel: Int,
    val unnamed: Boolean,
    val xpSinceLastLevel: Int,
    val xpUntilNextLevel: Int
) {

    data class RerollPoints(
        val currentPoints: Int,
        val maxRolls: Int,
        val numberOfRolls: Int,
        val pointsCostToRoll: Int,
        val pointsToReroll: Int
    )

}