package com.boost.voucherpool.recipient

import com.boost.voucherpool.voucher.Voucher
import com.boost.voucherpool.voucher.VoucherRepository
import com.boost.voucherpool.voucher.VoucherService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class RecipientApiControllerTest {
    private lateinit var recipientRepository: RecipientRepository
    private lateinit var voucherRepository: VoucherRepository
    private lateinit var voucherService: VoucherService
    private lateinit var controller: RecipientApiController

    @BeforeEach
    fun setUp() {
        recipientRepository = mock()
        voucherRepository = mock()
        voucherService = mock()
        controller = spy(RecipientApiController(
                recipientRepository,
                voucherService,
                voucherRepository
        ))
    }

    @Test
    fun `when creating recipient - valid`() {
        val recipient = Recipient("Izzat", "xizzat@gmail.com")

        whenever(recipientRepository.existsByEmail(any())).thenReturn(false)
        controller.createRecipient(recipient)

        verify(recipientRepository, times(1)).existsByEmail(any())
        verify(recipientRepository, times(1)).save(recipient)
    }

    @Test
    fun `when creating recipient - email not unique`() {
        val recipient = Recipient("Izzat", "xizzat@gmail.com")

        whenever(recipientRepository.existsByEmail(any())).thenReturn(true)
        controller.createRecipient(recipient)

        verify(recipientRepository, times(1)).existsByEmail(any())
        verify(recipientRepository, times(0)).save(recipient)
    }

    @Test
    fun `when query recipient voucher`() {
        val recipient = Recipient("Izzat", "xizzat@gmail.com")
        val vouchers = listOf(
                Voucher(
                        id = "11111111",
                        recipientId = recipient.email,
                        specialOfferId = "JOY",
                        active = true,
                        expirationDate = LocalDate.now().plusDays(30)
                ),
                Voucher(
                        id = "22222222",
                        recipientId = recipient.email,
                        specialOfferId = "RIANG",
                        active = true,
                        expirationDate = LocalDate.now().plusDays(30)
                )
        )

        whenever(voucherRepository.findAllByRecipientId(recipient.email))
                .thenReturn(vouchers)
        controller.recipientBy(recipient.email)

        verify(voucherRepository, times(1)).findAllByRecipientId(any())
        verify(voucherService, times(2)).validateVoucher(any())
    }
}