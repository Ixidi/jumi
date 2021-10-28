package xyz.ixidi.jumi.riotapi.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class LolMatchMakingReadyCheck(
    val declinerIds: Set<Long>,
    val dodgeWarning: DodgeWarning,
    val playerResponse: PlayerResponse,
    val state: State,
    val suppressUx: Boolean,
    val timer: Double
) {

    enum class DodgeWarning {

        @JsonProperty("None")
        NONE,
        @JsonProperty("Warning")
        WARNING,
        @JsonProperty("Penalty")
        PENALTY,
        UNKNOWN;

    }

    enum class PlayerResponse {

        @JsonProperty("None")
        NONE,
        @JsonProperty("Accepted")
        ACCEPTED,
        @JsonProperty("Declined")
        DECLINED,
        UNKNOWN;

    }

    enum class State {

        @JsonProperty("Invalid")
        INVALID,
        @JsonProperty("InProgress")
        IN_PROGRESS,
        @JsonProperty("EveryoneReady")
        EVERYONE_READY,
        @JsonProperty("StrangerNotReady")
        STRANGER_NOT_READY,
        @JsonProperty("PartyNotReady")
        PARTY_NOT_READY,
        @JsonProperty("Error")
        ERROR,
        UNKNOWN;

    }


}