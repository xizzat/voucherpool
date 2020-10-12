package com.boost.voucherpool.voucher

import com.boost.voucherpool.util.orNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class VoucherApiController @Autowired internal constructor(
        private val repository: VoucherRepository
) : VoucherApi {
    override fun createVoucher(voucher: Voucher): ResponseEntity<Voucher> {
        return voucher
                .apply {
                    id = UUID.randomUUID().toString().substring(0,8)
                }
                .let(repository::save)
                .let { ResponseEntity.ok(it) }
    }

    override fun voucherBy(id: String): ResponseEntity<Voucher> {
        val recipient = repository.findById(id).orNull()

        return recipient
                ?.let { ResponseEntity.ok(it) }
                ?: ResponseEntity.notFound().build()
    }
}