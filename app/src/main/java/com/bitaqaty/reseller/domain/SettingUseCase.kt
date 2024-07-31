package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class SettingUseCase @Inject constructor(private val repo: BBRepository) {
    suspend fun getSystemSettings() =
        repo.systemSettings()

    suspend fun getProfile() =
        repo.getProfile()



}