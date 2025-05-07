package org.krediya.project.presentation.model

import org.krediya.project.domain.model.Post

fun Post.toUI(): PostUI {
    return PostUI(
        userId = this.userId,
        id = this.id,
        title = this.title,
        body = this.body,
    )
}