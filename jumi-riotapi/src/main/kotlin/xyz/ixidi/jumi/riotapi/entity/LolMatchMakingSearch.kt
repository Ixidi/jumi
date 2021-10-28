package xyz.ixidi.jumi.riotapi.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class LolMatchMakingSearch(
    val dodgeData: DodgeData,
    val errors: List<Error>,
    val estimatedQueueTime: Double,
    val isCurrentlyInQueue: Boolean,
    val lobbyId: String,
    val lowPriorityData: LowPriorityData,
    val queueId: Int,
    val readyCheck: ReadyCheck,
    val searchState: SearchState,
    val timeInQueue: Double
) {

    enum class SearchState {

        @JsonProperty("Invalid")
        INVALID,
        @JsonProperty("Found")
        FOUND,
        @JsonProperty("Searching")
        SEARCHING,
        @JsonProperty("Canceled")
        CANCELED,
        @JsonProperty("AbandonedLowPriorityQueue")
        ABANDONED_LOW_PRIORITY_QUEUE,
        @JsonProperty("Error")
        ERROR,
        @JsonProperty("ServiceError")
        SERVICE_ERROR,
        @JsonProperty("ServiceShutdown")
        SERVICE_SHUTDOWN,
        UNKNOWN;

    }

    data class DodgeData(
        val dodgerId: Long,
        val state: String
    )

    data class Error(
        val errorType: String,
        val id: Long,
        val message: String,
        val penalizedSummonerId: Long,
        val penaltyTimeRemaining: Int
    )

    data class LowPriorityData(
        val bustedLeaverAccessToken: String,
        val penalizedSummonerIds: Set<Long>,
        val penaltyTime: Double,
        val penaltyTimeRemaining: Double,
        val reason: String
    )

    data class ReadyCheck(
        val declinerIds: Set<Long>,
        val dodgeWarning: String,
        val playerResponse: String,
        val state: String,
        val suppressUx: Boolean,
        val timer: Double
    )

}