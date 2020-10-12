package com.boost.voucherpool.voucher

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
        tags = ["Voucher"],
        value = "Voucher",
        description = "Voucher service."
)
interface VoucherApi {
    @ApiOperation(
            value = "Create Voucher",
            notes = "Create Voucher"
    )
    @ApiResponses(
            ApiResponse(code = 200, message = "Voucher is created")
    )
    @PostMapping("/api/voucher")
    fun createVoucher(
            @ApiParam(
                    value = "Representation of a voucher",
                    required = true
            )
            @RequestBody
            resource: Voucher
    ): ResponseEntity<Voucher>

    @ApiOperation(
            value = "Query voucher",
            notes = "Query a specific voucher by {id}",
            response = Voucher::class
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "No voucher found for {id}")
    )
    @RequestMapping(
            path = ["/api/voucher/{id}"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun voucherBy(
            @ApiParam(
                    value = "ID of voucher",
                    required = true
            )
            @PathVariable("id")
            id: String
    ): ResponseEntity<Voucher>
}