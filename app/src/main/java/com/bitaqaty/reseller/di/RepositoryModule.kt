package com.bitaqaty.reseller.di

import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.repository.BBRepository
import com.bitaqaty.reseller.domain.TransactionLogUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provides RemoteDataRepository for access api service method
     */
    @Singleton
    @Provides
    fun provideBBRepository(
        apiService: ApiService,
    ): BBRepository {
        return BBRepository(
            apiService
        )
    }

    @Singleton
    @Provides
    fun provideTransactionLogUseCase(
        apiService: BBRepository,
    ): TransactionLogUseCase {
        return TransactionLogUseCase(
            apiService
        )
    }
}