package com.boost.voucherpool.voucher

import com.boost.voucherpool.recipient.RecipientRepository
import com.boost.voucherpool.specialOffer.SpecialOfferRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class VoucherService @Autowired internal constructor(
        private val repository: VoucherRepository,
        private val recipientRepository: RecipientRepository,
        private val specialOfferRepository: SpecialOfferRepository
) {
    @Value("\${boost.voucher.duration}")
    var duration: Long = 0

    fun generateVoucherToAll(specialOfferId: String) {
        recipientRepository.findAll().forEach { recipient ->
            generateVoucher(recipient.email, specialOfferId)
        }
    }

    fun generateVoucher(recipientId: String, specialOfferId: String) {
        if (validateRecipient(recipientId) && validateSpecialOffer(specialOfferId)) {
            Voucher(
                    id = UUID.randomUUID().toString().substring(0, 8),
                    recipientId = recipientId,
                    specialOfferId = specialOfferId,
                    expirationDate = getExpirationDate()
            ).let(repository::save)
        } else {
            throw Exception("Recipient or SpecialOffer not exist!")
        }
    }

    fun validateVoucher(voucher: Voucher): Boolean {
        return if (!voucher.active) {
            false
        } else {
            voucher.expirationDate.isAfter((LocalDate.now()))
        }
    }

    fun getExpirationDate(): LocalDate = LocalDate.now().plusDays(duration);

    fun validateRecipient(recipientId: String): Boolean = recipientRepository
            .findByEmail(recipientId) != null

    fun validateSpecialOffer(specialOfferId: String): Boolean = specialOfferRepository
            .findByName(specialOfferId) != null
}