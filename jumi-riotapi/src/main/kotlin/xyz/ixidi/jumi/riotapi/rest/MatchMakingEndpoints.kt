package xyz.ixidi.jumi.riotapi.rest

import io.ktor.client.*
import io.ktor.client.statement.*

class MatchMakingEndpoints(
    client: HttpClient
) : RestEndpoints(client) {

    suspend fun deleteSearch(): HttpResponse = delete("/lol-matchmaking/v1/search")

    suspend fun postReadyCheckAccept(): HttpResponse = post("/lol-matchmaking/v1/ready-check/accept")
    suspend fun postReadyCheckDecline(): HttpResponse = post("/lol-matchmaking/v1/ready-check/decline")

}