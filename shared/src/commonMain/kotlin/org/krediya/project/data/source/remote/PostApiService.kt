package org.krediya.project.data.source.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.*
import org.krediya.project.data.model.PostDto

class PostApiService(private val httpClient: HttpClient) {

    suspend fun getPosts(): List<PostDto> {
        val response = httpClient.get("https://jsonplaceholder.typicode.com/posts")
        val responseText = response.bodyAsText()

        // Parser manual del JSON
        val jsonArray = Json.parseToJsonElement(responseText).jsonArray

        return jsonArray.mapNotNull { jsonElement ->
            try {
                val obj = jsonElement.jsonObject
                PostDto(
                    userId = obj["userId"]?.jsonPrimitive?.int ?: 0,
                    id = obj["id"]?.jsonPrimitive?.int ?: 0,
                    title = obj["title"]?.jsonPrimitive?.content ?: "",
                    body = obj["body"]?.jsonPrimitive?.content ?: ""
                )
            } catch (e: Exception) {
                println("Error parsing post: ${e.message}")
                null
            }
        }
    }
}