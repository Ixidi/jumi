package xyz.ixidi.jumi.riotapi.rest

import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.ixidi.jumi.riotapi.entity.LolChampionSelectSession
import xyz.ixidi.jumi.riotapi.entity.LolChampionSelectSummoner
import xyz.ixidi.jumi.riotapi.entity.Champion
import xyz.ixidi.jumi.riotapi.rest.request.ActionChampionDTO

class ChampionSelectEndpoints(
    client: HttpClient
) : RestEndpoints(client) {

    suspend fun getSession(): LolChampionSelectSession = get("/lol-champ-select/v1/session")

    suspend fun getPickableChampions(): Set<Champion> = get("/lol-champ-select/v1/pickable-champion-ids")

    suspend fun getSummonerBySlotId(slotId: Int): LolChampionSelectSummoner = get("/lol-champ-select/v1/summoners/$slotId")

    suspend fun patchAction(actionId: Long, champion: Champion): HttpResponse = patch("/lol-champ-select/v1/session/actions/$actionId") {
        contentType(ContentType.Application.Json)
        body = ActionChampionDTO(champion)
    }

    suspend fun postCompleteAction(actionId: Long): HttpResponse = post("/lol-champ-select/v1/session/actions/$actionId/complete")

}