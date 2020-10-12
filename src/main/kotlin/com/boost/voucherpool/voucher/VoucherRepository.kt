package com.boost.voucherpool.voucher

import org.springframework.data.mongodb.repository.MongoRepository

interface VoucherRepository : MongoRepository<Voucher, String>