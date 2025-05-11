package org.krediya.project.data.repository

import org.krediya.project.data.interfaces.PostsDataSourceInterface
import org.krediya.project.data.mapper.toDomain
import org.krediya.project.domain.model.Post
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.util.StatusResult

class PostRepositoryImpl(
    private val dataSource: PostsDataSourceInterface
) : PostRepository {

    override suspend fun getPosts(): StatusResult<List<Post>> {
        return when (val result = dataSource.getPosts()) {
            is StatusResult.Success -> {
                val posts = result.data.map { it.toDomain() }
                StatusResult.Success(posts)
            }
            is StatusResult.Error -> {
                StatusResult.Error(result.message)
            }
        }
    }
}
