package xyz.ixidi.jumi.riotapi.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigInteger

data class LolChampionSelectSession(
    val actions: List<List<Action>>,
    val allowBattleBoost: Boolean,
    val allowDuplicatePicks: Boolean,
    val allowLockedEvents: Boolean,
    val allowRerolling: Boolean,
    val allowSkinSelection: Boolean,
    val bans: Bans,
    @JsonProperty("benchChampionIds")
    val benchChampions: Set<Champion>,
    val benchEnabled: Boolean,
    val boostableSkinCount: Int,
    val chatDetails: ChatDetails,
    val counter: Long,
    val entitledFeatureState: EntitledFeatureState,
    val gameId: Long,
    val hasSimultaneousBans: Boolean,
    val hasSimultaneousPicks: Boolean,
    val isCustomGame: Boolean,
    val isSpectating: Boolean,
    val localPlayerCellId: Long,
    val lockedEventIndex: Int,
    val myTeam: List<Player>,
    val recoveryCounter: Long,
    val rerollsRemaining: Int,
    val skipChampionSelect: Boolean,
    val theirTeam: List<Player>,
    val timer: Timer,
    val trades: List<Trade>
) {

    data class Action(
        val actorCellId: Long,
        @JsonProperty("championId")
        val champion: Champion,
        val completed: Boolean,
        val id: Long,
        val isAllyAction: Boolean,
        val isInProgress: Boolean,
        val pickTurn: Int,
        val type: String
    )

    data class Bans(
        val myTeamBans: Set<Int>,
        val numBans: Int,
        val theirTeamBans: Set<Int>
    )

    data class ChatDetails(
        val chatRoomName: String,
        val chatRoomPassword: String?
    )

    data class EntitledFeatureState(
        val additionalRerolls: Int,
        val unlockedSkinIds: Set<Int>
    )

    data class Player(
        val assignedPosition: String,
        val cellId: Long,
        @JsonProperty("championId")
        val champion: Champion,
        val championPickIntent: Int,
        val entitledFeatureType: String,
        val selectedSkinId: Int,
        @JsonProperty("spell1Id") val firstSpellId: BigInteger,
        @JsonProperty("spell2Id") val secondSpellId: BigInteger,
        val summonerId: Long,
        val team: Int,
        val wardSkinId: Long
    )

    data class Timer(
        val adjustedTimeLeftInPhase: Long,
        val internalNowInEpochMs: Long,
        val isInfinite: Boolean,
        val phase: String,
        val totalTimeInPhase: Int
    )

    data class Trade(
        val cellId: Long,
        val id: Long,
        val state: String
    )

}