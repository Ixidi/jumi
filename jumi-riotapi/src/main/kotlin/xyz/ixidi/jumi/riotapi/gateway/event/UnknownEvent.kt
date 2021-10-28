package xyz.ixidi.jumi.riotapi.gateway.event

data class UnknownEvent(val uri: String, val eventType: String, val rawData: Any?) : GatewayEvent