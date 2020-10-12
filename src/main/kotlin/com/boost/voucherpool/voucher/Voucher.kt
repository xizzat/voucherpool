package com.boost.voucherpool.voucher

import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

data class Voucher(
        @get:ApiModelProperty(
                hidden = true
        )
        var id: String? = null,
        @get:ApiModelProperty(
                value = "RecipientId",
                example = "xizzat",
                required = true,
                position = 1
        )
        var recipientId: String,
        @get:ApiModelProperty(
                value = "SpecialOfferId",
                example = "BOOSTFEAST",
                required = true,
                position = 2
        )
        var specialOfferId: String,
        @get:ApiModelProperty(
                value = "Expiration Date",
                example = "2020-10-12",
                required = true,
                position = 3
        )
        var expirationDate: LocalDate,
        var active: Boolean = true,
        var usageDate: LocalDate? = null
)