package com.boost.voucherpool.recipient

import com.boost.voucherpool.voucher.Voucher
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Api(
        tags = ["Recipient"],
        value = "Recipient",
        description = "Recipient service."
)
interface RecipientApi {
    @ApiOperation(
            value = "Create Recipient",
            notes = "Create Recipient"
    )
    @ApiResponses(
            ApiResponse(code = 200, message = "Recipient is created")
    )
    @PostMapping("/api/recipient")
    fun createRecipient(
            @ApiParam(
                    value = "Representation of a recipient",
                    required = true
            )
            @RequestBody
            resource: Recipient
    ): ResponseEntity<Recipient>

    @ApiOperation(
            value = "Query recipient's valid voucher",
            notes = "Query a recipient's valid voucher by {email}",
            response = Recipient::class
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "No recipient found for {email}")
    )
    @RequestMapping(
            path = ["/api/recipient/{email}/voucher"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun recipientBy(
            @ApiParam(
                    value = "Email of recipient",
                    example = "xizzat@gmail.com",
                    required = true
            )
            @PathVariable("email")
            email: String
    ): ResponseEntity<List<Voucher>>
}