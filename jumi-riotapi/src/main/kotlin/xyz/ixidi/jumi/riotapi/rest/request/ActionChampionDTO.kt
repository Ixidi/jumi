package xyz.ixidi.jumi.riotapi.rest.request

import com.fasterxml.jackson.annotation.JsonProperty
import xyz.ixidi.jumi.riotapi.entity.Champion

data class ActionChampionDTO(
    @JsonProperty("championId")
    val champion: Champion
)