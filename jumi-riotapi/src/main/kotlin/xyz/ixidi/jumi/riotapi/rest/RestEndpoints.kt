package xyz.ixidi.jumi.riotapi.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.text.get

abstract class RestEndpoints(
    protected val client: HttpClient
) {

    protected suspend inline fun <reified T> get(path: String, builder: HttpRequestBuilder.() -> Unit = {}): T = endpoint(HttpMethod.Get, path, builder)
    protected suspend inline fun <reified T> post(path: String, builder: HttpRequestBuilder.() -> Unit = {}): T = endpoint(HttpMethod.Post, path, builder)
    protected suspend inline fun <reified T> put(path: String, builder: HttpRequestBuilder.() -> Unit = {}): T = endpoint(HttpMethod.Put, path, builder)
    protected suspend inline fun <reified T> delete(path: String, builder: HttpRequestBuilder.() -> Unit = {}): T = endpoint(HttpMethod.Delete, path, builder)
    protected suspend inline fun <reified T> patch(path: String, builder: HttpRequestBuilder.() -> Unit = {}): T = endpoint(HttpMethod.Patch, path, builder)

    protected suspend inline fun <reified T> endpoint(method: HttpMethod, path: String, builder: HttpRequestBuilder.() -> Unit): T = client.request {
        this.method = method
        url {
            val p = if (path.startsWith("/")) path.drop(1) else path
            path(p)
            protocol = URLProtocol.HTTPS
        }
        builder()
    }

}