package com.boost.voucherpool.recipient

import com.boost.voucherpool.voucher.VoucherRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RecipientApiControllerTest {
    private lateinit var recipientRepository: RecipientRepository
    private lateinit var voucherRepository: VoucherRepository
    private lateinit var controller: RecipientApiController

    @BeforeEach
    fun setUp() {
        recipientRepository = mock()
        voucherRepository = mock()
        controller = spy(RecipientApiController(
                recipientRepository,
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
}