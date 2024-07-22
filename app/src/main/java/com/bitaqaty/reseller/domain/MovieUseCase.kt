package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.SubSellerRepository

class MovieUseCase constructor(
    private val repo: SubSellerRepository,
) {
    suspend fun invoke(personId: Int) = repo.artistDetail(personId)
}