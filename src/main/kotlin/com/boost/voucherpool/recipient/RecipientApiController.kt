package com.boost.voucherpool.recipient

import com.boost.voucherpool.voucher.Voucher
import com.boost.voucherpool.voucher.VoucherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class RecipientApiController @Autowired internal constructor(
        private val repository: RecipientRepository,
        private val voucherRepository: VoucherRepository
) : RecipientApi {
    override fun createRecipient(recipient: Recipient): ResponseEntity<Recipient> {
        return recipient
                .let(repository::save)
                .let { ResponseEntity.ok(it) }
    }

    override fun recipientBy(email: String): ResponseEntity<List<Voucher>> {
        return voucherRepository.findAllByRecipientIdAndActiveTrue(email).let { ResponseEntity.ok(it) }
    }
}