package com.boost.voucherpool.recipient

import org.springframework.data.mongodb.repository.MongoRepository

interface RecipientRepository : MongoRepository<Recipient, String>