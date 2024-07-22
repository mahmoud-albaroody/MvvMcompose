package com.bitaqaty.reseller.di

import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.repository.SubSellerRepository
import com.bitaqaty.reseller.domain.MovieUseCase
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
    fun provideSubSellerRepository(
        apiService: ApiService,
    ): SubSellerRepository {
        return SubSellerRepository(
            apiService
        )
    }
    @Singleton
    @Provides
    fun provideMovieUseCase(
        apiService: SubSellerRepository,
    ): MovieUseCase {
        return MovieUseCase(
            apiService
        )
    }
}