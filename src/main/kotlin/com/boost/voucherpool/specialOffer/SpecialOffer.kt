package com.boost.voucherpool.specialOffer

import io.swagger.annotations.ApiModelProperty

data class SpecialOffer(
        @get:ApiModelProperty(
                value = "Name",
                example = "BOOSTFEAST",
                required = true,
                position = 1
        )
        val name: String,
        @get:ApiModelProperty(
                value = "Discount",
                example = "0.25",
                required = true,
                position = 2
        )
        val discount: Double
)