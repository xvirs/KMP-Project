package org.krediya.project.domain.interfaces

import org.krediya.project.domain.model.Post
import org.krediya.project.util.StatusResult

interface PostRepository {
    suspend fun getPosts(): StatusResult<List<Post>>
}