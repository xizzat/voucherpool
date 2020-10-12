package com.boost.voucherpool.recipient

import io.swagger.annotations.ApiModelProperty
import org.springframework.data.mongodb.core.index.Indexed

data class Recipient(
        @get:ApiModelProperty(
                value = "Name",
                example = "Izzat Taqiuddin",
                required = true,
                position = 1
        )
        val name: String,
        @get:ApiModelProperty(
                value = "Email address",
                example = "xizzat@gmail.com",
                required = true,
                position = 2
        )
        @Indexed
        val email: String
)