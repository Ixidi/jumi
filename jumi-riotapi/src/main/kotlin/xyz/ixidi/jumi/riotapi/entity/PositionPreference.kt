package xyz.ixidi.jumi.riotapi.entity

enum class PositionPreference(
    val systemId: String
) {

    TOP("TOP"),
    MIDDLE("MIDDLE"),
    JUNGLE("JUNGLE"),
    BOTTOM("BOTTOM"),
    SUPPORT("UTILITY"),
    FILL("FILL");

}