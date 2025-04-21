package org.krediya.project.data.mapper

import org.krediya.project.data.model.PostDto
import org.krediya.project.domain.model.Post

fun PostDto.toDomain(): Post = Post(
    id = id,
    userId = userId,
    title = title,
    body = body
)

fun Post.toDto(): PostDto = PostDto(
    id = id,
    userId = userId,
    title = title,
    body = body
)