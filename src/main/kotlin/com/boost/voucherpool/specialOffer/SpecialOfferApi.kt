package com.boost.voucherpool.specialOffer

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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Api(
        tags = ["SpecialOffer"],
        value = "SpecialOffer",
        description = "SpecialOffer service."
)
interface SpecialOfferApi {
    @ApiOperation(
            value = "Create SpecialOffer",
            notes = "Create SpecialOffer"
    )
    @ApiResponses(
            ApiResponse(code = 200, message = "SpecialOffer is created")
    )
    @PostMapping("/api/special-offer")
    fun createSpecialOffer(
            @ApiParam(
                    value = "Representation of a specialOffer",
                    required = true
            )
            @RequestBody
            resource: SpecialOffer,
            @ApiParam(
                    value = "If auto assigned all voucher to current user",
                    example = "true",
                    required = true
            )
            @RequestParam
            applyAll: Boolean
    ): ResponseEntity<SpecialOffer>

    @ApiOperation(
            value = "Query specialOffer",
            notes = "Query a specific specialOffer by {name}",
            response = SpecialOffer::class
    )
    @ApiResponses(
            ApiResponse(code = 404, message = "No specialOffer found for {name}")
    )
    @RequestMapping(
            path = ["/api/special-offer/{name}"],
            method = [RequestMethod.GET],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseBody
    fun specialOfferBy(
            @ApiParam(
                    value = "Name of specialOffer",
                    required = true
            )
            @PathVariable("name")
            name: String
    ): ResponseEntity<SpecialOffer>
}