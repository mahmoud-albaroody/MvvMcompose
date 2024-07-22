package com.bitaqaty.reseller.di

import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.datasource.remote.ApiURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides BaseUrl as string
     */
    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return ApiURL.STAGING_BASE_URL
    }
    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("locale", "en")
                .header("device-id", "96baed0207d76e88")
                .header("whitelabel-code", "BITAQATY_BUSINESS")
                .header("OS", "ANDROID")
                .header("Application-name", "BITAQATY_BUSINESS_MOBILE")
                .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlenphdHN1YiIsImlzcyI6IkJpdGFxYXR5IEJ1c2luZXNzIiwiZXhwIjoxNzUzMTc4ODcwLCJpYXQiOjE3MjE2NDI4NzAsImp0aSI6IjA3ZDc1ZGQ3LTdhNWYtNDc4NC04NWVmLWI2MmRkODMxN2M5ZiJ9.InH74uudtBDuPVSWyfEWoT5I0QO1lbvXHbm9C2B2EuTC5CJmyX2zFsfblMDwARD6k8IluuXgsmambizyrMYtkmCcBCzsORa4onwVI8Kgge7BhcxV_Wr_sm611qoIku5ZVM-m7six5SKKs9_g4Yz7hpfw2WFmQ7mERiDP__avvO10xU9m6ugvjwiumX3giKMxziNGH4x0GESyPhV44qNoGZPY72kwN7Z_hF8KsRUA6b3SW3M3PqQNdzDEHhP1-mrHILmy-P3s3c_pOimrs1j6OReOGO-cMGyKgQ9jIYXfUqJcWnuz7klNeXkaxXdcX5XAw2IEFzlp20J5TuRIvtZ3Ow")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }
    /**
     * Provides converter factory for retrofit
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
    /**
     * Provides Api Service using retrofit
     */
    @Singleton
    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}