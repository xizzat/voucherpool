package com.boost.voucherpool.recipient

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Recipient(
        val id: String,
        val name: String,
        @Indexed
        val email: String
)