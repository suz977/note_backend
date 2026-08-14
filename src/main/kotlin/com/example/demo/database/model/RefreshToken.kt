package com.example.demo.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import kotlin.time.Clock
import kotlin.time.Instant

@Document("refreshToken")
data class RefreshToken(
    val userId: ObjectId,
    @Indexed(expireAfter = "0s")
    val expiresAt : Instant,
    val hashedToken : String,
    val createdAt : Instant = Clock.System.now(),
    )
