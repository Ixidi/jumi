package xyz.ixidi.jumi.riotapi.entity

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class QueueType(
    val queueId: Int
) {

    NORMAL_DRAFT(400),
    NORMAL_BLIND(430),
    RANKED_DRAFT_SOLO_DUO(420),
    RANKED_DRAFT_FLEX(440),
    UNKNOWN(-1);

    companion object {

        @JvmStatic
        @JsonCreator
        fun forValue(value: Int) = values().firstOrNull { it.queueId == value } ?: UNKNOWN

    }

    @JsonValue
    fun toValue() = queueId

}