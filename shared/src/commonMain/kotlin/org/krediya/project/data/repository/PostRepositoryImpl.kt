package org.krediya.project.data.repository

import org.krediya.project.data.mapper.toDomain
import org.krediya.project.data.source.remote.PostApiService
import org.krediya.project.domain.model.Post
import org.krediya.project.domain.repository.PostRepository

class PostRepositoryImpl(
    private val apiService: PostApiService
) : PostRepository {
    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts().map { it.toDomain() }
    }
}