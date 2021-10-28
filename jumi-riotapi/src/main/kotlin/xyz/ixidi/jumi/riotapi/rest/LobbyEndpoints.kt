package xyz.ixidi.jumi.riotapi.rest

import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.ixidi.jumi.riotapi.rest.request.CreateLobbyDTO
import xyz.ixidi.jumi.riotapi.entity.PositionPreference
import xyz.ixidi.jumi.riotapi.entity.QueueType
import xyz.ixidi.jumi.riotapi.rest.request.ChoosePositionDTO

class LobbyEndpoints(
    client: HttpClient
) : RestEndpoints(client) {

    suspend fun postLobby(queueType: QueueType): HttpResponse = post("/lol-lobby/v2/lobby") {
        contentType(ContentType.Application.Json)
        body = CreateLobbyDTO(queueType.queueId)
    }

    suspend fun getLobby(): HttpResponse = get("/lol-lobby/v2/lobby")

    suspend fun postAcceptInvitation(invitationId: String): HttpResponse = post("/lol-lobby/v2/received-invitations/${invitationId}/accept")

    suspend fun putChoosePositions(first: PositionPreference, second: PositionPreference): HttpResponse = put("/lol-lobby/v2/lobby/members/localMember/position-preferences") {
        contentType(ContentType.Application.Json)
        body = ChoosePositionDTO(first.systemId, second.systemId)
    }


}