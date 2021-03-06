package com.boost.voucherpool.voucher

import org.springframework.data.mongodb.repository.MongoRepository

interface VoucherRepository : MongoRepository<Voucher, String> {
    fun findByIdAndRecipientId(code: String, recipientId: String): Voucher?
    fun findAllByRecipientId(email: String): List<Voucher>
}