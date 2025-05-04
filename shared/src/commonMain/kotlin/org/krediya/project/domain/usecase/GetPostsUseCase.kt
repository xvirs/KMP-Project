package org.krediya.project.domain.usecase

import org.krediya.project.domain.model.Post
import org.krediya.project.domain.interfaces.PostRepository

class GetPostsUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(): List<Post> = postRepository.getPosts()
}