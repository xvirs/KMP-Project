package org.krediya.project.data.interfaces

import org.krediya.project.data.model.PostDto
import org.krediya.project.util.StatusResult

interface PostsDataSourceInterface {
    suspend fun getPosts(): StatusResult<List<PostDto>>
}