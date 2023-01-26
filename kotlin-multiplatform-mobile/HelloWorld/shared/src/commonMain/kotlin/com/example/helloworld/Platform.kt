package com.example.helloworld

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform