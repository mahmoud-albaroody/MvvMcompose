package com.bitaqaty.reseller.domain

import com.bitaqaty.reseller.data.model.RequestBankTransferLogBody
import com.bitaqaty.reseller.data.model.RequestOneCardAccountsBody
import com.bitaqaty.reseller.data.repository.BBRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class BankTransferUseCase @Inject constructor(private val repo: BBRepository) {

    suspend fun searchBankTransfer(bankTransferLogBody: RequestBankTransferLogBody) =
        repo.searchBankTransfer(bankTransferLogBody)

    suspend fun onecardCountries() =
        repo.onecardCountries()

    suspend fun onecardAccount(requestOneCardAccountsBody: RequestOneCardAccountsBody) =
        repo.onecardAccount(requestOneCardAccountsBody)

    suspend fun senderCounters() = repo.senderCounters()

    suspend fun saveAccount() =
        repo.saveAccount()

    suspend fun senderAccountByCounter(id: String) =
        repo.senderAccountByCounter(id)

    suspend fun getSavedAccounts(userInfo: RequestOneCardAccountsBody) =
        repo.gatSavedAccounts(userInfo)
}
