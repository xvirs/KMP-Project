package org.krediya.project.data.datasource

import io.ktor.client.call.*
import kotlinx.serialization.json.Json
import org.krediya.project.data.model.PostDto
import org.krediya.project.data.network.BaseClient
import org.krediya.project.data.interfaces.PostsDataSourceInterface
import org.krediya.project.util.StatusResult

class PostsDataSourceImpl(
    private val baseClient: BaseClient
) : PostsDataSourceInterface {

    override suspend fun getPosts(): StatusResult<List<PostDto>> {
        val result = baseClient.get(
            endpoint = "/posts",
            errorMessage = "Error al obtener los posts"
        )

        return if (result.httpResponse != null) {
            try {
                val responseBody: String = result.httpResponse.body()
                val posts: List<PostDto> = Json.decodeFromString(responseBody)
                StatusResult.Success(posts)
            } catch (e: Exception) {
                StatusResult.Error("Error de parseo: ${e.message}")
            }

        } else {
            StatusResult.Error(result.errorMessage)
        }
    }
}
