package org.krediya.project.domain.interfaces

import org.krediya.project.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
}