package com.boost.voucherpool.specialOffer

import org.springframework.data.mongodb.repository.MongoRepository

interface SpecialOfferRepository : MongoRepository<SpecialOffer, String> {
    fun findByName(name: String): SpecialOffer?
    fun existsByName(name: String): Boolean
}