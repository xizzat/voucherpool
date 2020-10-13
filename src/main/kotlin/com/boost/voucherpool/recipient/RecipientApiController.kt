package com.boost.voucherpool.recipient

import com.boost.voucherpool.voucher.Voucher
import com.boost.voucherpool.voucher.VoucherRepository
import com.boost.voucherpool.voucher.VoucherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class RecipientApiController @Autowired internal constructor(
        private val repository: RecipientRepository,
        private val voucherService: VoucherService,
        private val voucherRepository: VoucherRepository
) : RecipientApi {
    override fun createRecipient(recipient: Recipient): ResponseEntity<Recipient> {
        return recipient.takeUnless { repository.existsByEmail(it.email) }
                ?.let(repository::save)
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }

    override fun recipientBy(email: String): ResponseEntity<List<Voucher>> {
        return voucherRepository.findAllByRecipientId(email)
                .filter { voucherService.validateVoucher(it) }
                .let { ResponseEntity.ok(it) }
    }
}