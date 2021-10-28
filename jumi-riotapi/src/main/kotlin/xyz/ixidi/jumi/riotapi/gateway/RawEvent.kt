package xyz.ixidi.jumi.riotapi.gateway

import java.util.*

data class RawEvent(
    val data: Any?,
    val eventType: String,
    val uri: String
)