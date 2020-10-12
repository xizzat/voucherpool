package com.boost.voucherpool.recipient

import com.boost.voucherpool.util.orNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class RecipientApiController @Autowired internal constructor(
        private val repository: RecipientRepository
) : RecipientApi {
    override fun createRecipient(recipient: Recipient): ResponseEntity<Recipient> {
        return recipient
                .let(repository::save)
                .let { ResponseEntity.ok(it) }
    }

    override fun recipientBy(id: String): ResponseEntity<Recipient> {
        val recipient = repository.findById(id).orNull()

        return recipient
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }
}