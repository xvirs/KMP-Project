package org.krediya.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform