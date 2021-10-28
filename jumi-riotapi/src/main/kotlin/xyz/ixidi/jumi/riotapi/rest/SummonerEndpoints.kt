package xyz.ixidi.jumi.riotapi.rest

import io.ktor.client.*
import xyz.ixidi.jumi.riotapi.entity.LolSummoner

class SummonerEndpoints(
    client: HttpClient
) : RestEndpoints(client) {

    suspend fun getCurrentSummoner(): LolSummoner = get("/lol-summoner/v1/current-summoner")

}