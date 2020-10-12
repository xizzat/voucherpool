package com.boost.voucherpool.recipient

import org.springframework.data.mongodb.repository.MongoRepository

interface RecipientRepository : MongoRepository<Recipient, String> {
    fun findByEmail(email: String): Recipient?
    fun existsByEmail(email: String): Boolean
}