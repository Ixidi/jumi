package xyz.ixidi.jumi.riotapi.rest

import io.ktor.client.*

class RestClient(
    client: HttpClient
) {

    val lobby = LobbyEndpoints(client)
    val summoner = SummonerEndpoints(client)
    val matchMaking = MatchMakingEndpoints(client)
    val championSelect = ChampionSelectEndpoints(client)

}