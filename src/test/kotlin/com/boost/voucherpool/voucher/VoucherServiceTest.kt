package com.boost.voucherpool.voucher

import com.boost.voucherpool.recipient.Recipient
import com.boost.voucherpool.recipient.RecipientRepository
import com.boost.voucherpool.specialOffer.SpecialOffer
import com.boost.voucherpool.specialOffer.SpecialOfferRepository
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class VoucherServiceTest {
    private lateinit var repository: VoucherRepository
    private lateinit var recipientRepository: RecipientRepository
    private lateinit var specialOfferRepository: SpecialOfferRepository
    private lateinit var voucherService: VoucherService

    @BeforeEach
    fun setUp() {
        recipientRepository = mock()
        specialOfferRepository = mock()
        repository = mock()
        voucherService = spy(VoucherService(
                repository,
                recipientRepository,
                specialOfferRepository
        ))
    }

    @Test
    fun `validateVoucher - not used and not expired`() {
        val voucher = Voucher(
                id = "b52145f7",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "BOOSTFEAST",
                expirationDate = LocalDate.parse("2025-12-15"),
                active = true,
                usageDate = null
        )
        val result = voucherService.validateVoucher(voucher)

        assertThat(result, equalTo(true))
    }

    @Test
    fun `validateVoucher - used but not expired`() {
        val voucher = Voucher(
                id = "b52145f7",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "BOOSTFEAST",
                expirationDate = LocalDate.parse("2025-12-15"),
                active = false,
                usageDate = null
        )
        val result = voucherService.validateVoucher(voucher)

        assertThat(result, equalTo(false))
    }

    @Test
    fun `validateVoucher - used and expired`() {
        val voucher = Voucher(
                id = "b52145f7",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "BOOSTFEAST",
                expirationDate = LocalDate.parse("2010-12-15"),
                active = false,
                usageDate = null
        )
        val result = voucherService.validateVoucher(voucher)

        assertThat(result, equalTo(false))
    }

    @Test
    fun `validateVoucher - not used and expired`() {
        val voucher = Voucher(
                id = "b52145f7",
                recipientId = "xizzat@gmail.com",
                specialOfferId = "BOOSTFEAST",
                expirationDate = LocalDate.parse("2010-12-15"),
                active = true,
                usageDate = null
        )
        val result = voucherService.validateVoucher(voucher)

        assertThat(result, equalTo(false))
    }

    @Test
    fun `generateVoucherToAll`() {
        whenever(recipientRepository.findAll()).thenReturn(
                listOf(
                        Recipient(
                                name = "Izzat",
                                email = "xizzat@gmail.com"
                        ),
                        Recipient(
                                name = "Jack",
                                email = "jack@gmail.com"
                        )
                )
        )

        whenever(recipientRepository.findByEmail(any()))
                .thenReturn(Recipient(
                        name = "Izzat",
                        email = "xizzat@gmail.com"
                ))

        whenever(specialOfferRepository.findByName(any()))
                .thenReturn(SpecialOffer("JOY", 0.25))

        voucherService.generateVoucherToAll("JOY")
        verify(voucherService, times(2)).generateVoucher(any(), any())
    }
}