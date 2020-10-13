package com.boost.voucherpool.voucher

import com.boost.voucherpool.specialOffer.SpecialOffer
import com.boost.voucherpool.specialOffer.SpecialOfferRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class VoucherApiControllerTest {
    private lateinit var specialOfferRepository: SpecialOfferRepository
    private lateinit var voucherRepository: VoucherRepository
    private lateinit var voucherService: VoucherService
    private lateinit var controller: VoucherApiController

    @BeforeEach
    fun setUp() {
        specialOfferRepository = mock()
        voucherRepository = mock()
        voucherService = mock()
        controller = spy(VoucherApiController(
                voucherRepository,
                specialOfferRepository,
                voucherService
        ))
    }

    @Test
    fun `when redeem voucher - valid`() {
        val voucher = Voucher(
                id = "12345678",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "JOYCODE",
                expirationDate = LocalDate.now(),
                active = true,
                usageDate = null
        )

        val specialOffer = SpecialOffer("JOYCODE", 0.25)
        whenever(voucherRepository.findByIdAndRecipientId(any(), any())).thenReturn(voucher)
        whenever(voucherService.validateVoucher(voucher)).thenReturn(true)
        whenever(specialOfferRepository.findByName(any())).thenReturn(specialOffer)

        controller.redeemVoucher("12345678", "xizzat@gmail.com")

        verify(voucherService, times(1)).validateVoucher(voucher)
        verify(specialOfferRepository, times(1)).findByName(voucher.specialOfferId)
        verify(voucherRepository, times(1)).save(voucher)
    }

    @Test
    fun `when redeem voucher - expired`() {
        val voucher = Voucher(
                id = "12345678",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "JOYCODE",
                expirationDate = LocalDate.parse("2015-01-01"),
                active = true,
                usageDate = null
        )

        val specialOffer = SpecialOffer("JOYCODE", 0.25)
        whenever(voucherRepository.findByIdAndRecipientId(any(), any())).thenReturn(voucher)
        whenever(voucherService.validateVoucher(voucher)).thenReturn(false)
        whenever(specialOfferRepository.findByName(any())).thenReturn(specialOffer)

        controller.redeemVoucher("12345678", "xizzat@gmail.com")

        verify(voucherService, times(1)).validateVoucher(voucher)
        verify(specialOfferRepository, times(0)).findByName(voucher.specialOfferId)
        verify(voucherRepository, times(0)).save(voucher)
    }

    @Test
    fun `when redeem voucher - special offer not exist`() {
        val voucher = Voucher(
                id = "12345678",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "JOYCODE",
                expirationDate = LocalDate.parse("2015-01-01"),
                active = true,
                usageDate = null
        )

        whenever(voucherRepository.findByIdAndRecipientId(any(), any())).thenReturn(voucher)
        whenever(voucherService.validateVoucher(voucher)).thenReturn(true)
        whenever(specialOfferRepository.findByName(any())).thenReturn(null)

        controller.redeemVoucher("12345678", "xizzat@gmail.com")

        verify(voucherService, times(1)).validateVoucher(voucher)
        verify(specialOfferRepository, times(1)).findByName(voucher.specialOfferId)
        verify(voucherRepository, times(0)).save(voucher)
    }
}