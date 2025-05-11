package org.krediya.project.domain.usecase

import org.krediya.project.domain.model.Post
import org.krediya.project.domain.interfaces.PostRepository
import org.krediya.project.util.StatusResult

open class GetPostsUseCase(val postRepository: PostRepository) {
    suspend operator fun invoke(): StatusResult<List<Post>> = postRepository.getPosts()
}