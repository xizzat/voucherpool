package com.boost.voucherpool.voucher

import com.boost.voucherpool.specialOffer.SpecialOfferRepository
import com.boost.voucherpool.util.orNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class VoucherApiController @Autowired internal constructor(
        private val repository: VoucherRepository,
        private val specialOfferRepository: SpecialOfferRepository,
        private val voucherService: VoucherService
) : VoucherApi {
    override fun redeemVoucher(code: String, email: String): ResponseEntity<Any> {
        val voucher = repository.findByIdAndRecipientId(code, email)

        return voucher
                ?.takeIf { voucherService.validateVoucher(it) }
                ?.let { specialOfferRepository.findByName(it.specialOfferId) }
                ?.let {
                    voucher.apply {
                        active = false
                        usageDate = LocalDate.now()
                    }.let(repository::save)
                    return ResponseEntity.ok().body(it.discount)
                }
                ?: ResponseEntity.notFound().build()
    }

    override fun voucherBy(id: String): ResponseEntity<Voucher> {
        val recipient = repository.findById(id).orNull()

        return recipient
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }
}