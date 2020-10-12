package com.boost.voucherpool.specialOffer

import com.boost.voucherpool.voucher.VoucherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SpecialOfferApiController @Autowired internal constructor(
        private val repository: SpecialOfferRepository,
        private val voucherService: VoucherService
) : SpecialOfferApi {
    override fun createSpecialOffer(specialOffer: SpecialOffer, applyAll: Boolean): ResponseEntity<SpecialOffer> {

        val offer = specialOffer
                .takeUnless { repository.existsByName(specialOffer.name) }
                ?.let(repository::save)

        if (applyAll && offer!=null) {
            voucherService.generateVoucherToAll(specialOfferId = specialOffer.name)
        }

        return offer
                ?.let { ResponseEntity.ok(offer) }
                ?: ResponseEntity.badRequest().build()
    }

    override fun specialOfferBy(name: String): ResponseEntity<SpecialOffer> {
        val specialOffer = repository.findByName(name)

        return specialOffer
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }
}