package com.boost.voucherpool.voucher

import com.boost.voucherpool.recipient.Recipient
import com.boost.voucherpool.specialOffer.SpecialOffer
import java.util.Date

data class Voucher(
        val id: String,
        var recipient: Recipient,
        var offer: SpecialOffer,
        var expirationDate: Date
)