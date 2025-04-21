package org.krediya.project.data.repository

import org.krediya.project.data.interfaces.PostsDataSourceInterface
import org.krediya.project.data.mapper.toDomain
import org.krediya.project.domain.model.Post
import org.krediya.project.domain.repository.PostRepository
import org.krediya.project.util.StatusResult

class PostRepositoryImpl(
    private val dataSource: PostsDataSourceInterface
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return when (val result = dataSource.getPosts()) {
            is StatusResult.Success -> result.data.map { it.toDomain() }
            is StatusResult.Error -> {
                println("Error en Repository: ${result.message}")
                emptyList()
            }
        }
    }
}
