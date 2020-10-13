package com.boost.voucherpool.specialOffer

import com.boost.voucherpool.voucher.VoucherService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SpecialOfferApiControllerTest {
    private lateinit var specialOfferRepository: SpecialOfferRepository
    private lateinit var voucherService: VoucherService
    private lateinit var controller: SpecialOfferApiController

    @BeforeEach
    fun setUp() {
        specialOfferRepository = mock()
        voucherService = mock()
        controller = spy(SpecialOfferApiController(
                specialOfferRepository,
                voucherService
        ))
    }

    @Test
    fun `when creating specialOffer - unique`() {
        val specialOffer = SpecialOffer("JOYCODE", 0.25)
        whenever(specialOfferRepository.existsByName(any())).thenReturn(false)

        controller.createSpecialOffer(specialOffer = specialOffer, applyAll = false)
        verify(specialOfferRepository, times(1)).existsByName(any())
        verify(specialOfferRepository, times(1)).save(specialOffer)
    }

    @Test
    fun `when creating specialOffer - name is exist`() {
        val specialOffer = SpecialOffer("JOYCODE", 0.25)
        whenever(specialOfferRepository.existsByName(any())).thenReturn(true)

        controller.createSpecialOffer(specialOffer = specialOffer, applyAll = false)
        verify(specialOfferRepository, times(1)).existsByName(any())
        verify(specialOfferRepository, times(0)).save(specialOffer)
    }

    @Test
    fun `when creating specialOffer - apply voucher to all recipient`() {
        val specialOffer = SpecialOffer("JOYCODE", 0.25)
        whenever(specialOfferRepository.existsByName(any())).thenReturn(false)
        whenever(specialOfferRepository.save(specialOffer)).thenReturn(specialOffer)

        controller.createSpecialOffer(specialOffer = specialOffer, applyAll = true)
        verify(specialOfferRepository, times(1)).existsByName(any())
        verify(specialOfferRepository, times(1)).save(specialOffer)
        verify(voucherService, times(1)).generateVoucherToAll(specialOffer.name)
    }

    @Test
    fun `when creating specialOffer - apply voucher set to false`() {
        val specialOffer = SpecialOffer("JOYCODE", 0.25)
        whenever(specialOfferRepository.existsByName(any())).thenReturn(false)
        whenever(specialOfferRepository.save(specialOffer)).thenReturn(specialOffer)

        controller.createSpecialOffer(specialOffer = specialOffer, applyAll = false)
        verify(specialOfferRepository, times(1)).existsByName(any())
        verify(specialOfferRepository, times(1)).save(specialOffer)
        verify(voucherService, times(0)).generateVoucherToAll(specialOffer.name)
    }
}