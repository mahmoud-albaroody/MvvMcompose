package com.bitaqaty.reseller.di

import android.content.Context
import com.bitaqaty.reseller.HiltApplication
import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.datasource.remote.ApiURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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
        return ApiURL.BASE_URL
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): HiltApplication {
        return app as HiltApplication
    }

    @Singleton
    @Provides
    fun provideContext(application: HiltApplication): Context {
        return application.applicationContext
    }

    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CALL_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB


    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val header = HttpLoggingInterceptor()
        header.apply { header.level = HttpLoggingInterceptor.Level.HEADERS }
        okHttpClient.callTimeout(CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.cache(cache)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(interceptor)

        return okHttpClient.build()
    }


    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {

        return Interceptor {

            val requestBuilder = it.request().newBuilder()
                //hear you can add all headers you want by calling 'requestBuilder.addHeader(name ,  value)'
                .header("Authorization", "")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Content-Type", "application/json")
                .header("locale", "en")
                .header("device-id", "96baed0207d76e88")
                .header("whitelabel-code", "BITAQATY_BUSINESS")
                .header("OS", "ANDROID")
                .header("Application-name", "BITAQATY_BUSINESS_MOBILE")
                .header(
                    "Authorization",
                    "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlenphdHN1YiIsImlzcyI6IkJpdGFxYXR5IEJ1c2luZXNzIiwiZXhwIjoxNzUzNzAzMDAwLCJpYXQiOjE3MjIxNjcwMDAsImp0aSI6IjA5OWM5OTQyLTk5ZjEtNDI0NS05MGFkLTczOWQ2NjY5NGI1ZiJ9.ink4jnrcFDgC8XNowRC8bOMahVYMQ1vXlR5mjxVHgZnQ0rAcGrZWWXmnsiNYe9F4aMCX-S25lJcJ_OClvgFvj6fldG57AjSfsrrYBQZL9oELKMd5cTVrtRMr9lbqYrCvpwkpmzuOrU-ZdvQ-Xe_gUoPSatmIOLXtkKzKRVYtbJBIu_NDQ18KuNF7s72rqsvO0cLZTljOa-gJ-A5_xVMLlEOAM1rcbTP0SK24eshkJ1NXMi1FNUtc5gxEKKfgZ8uAQ6M91nI0muZQAs-AFEMOrHirbenyfnAnHpQ4QQdRNOwhF6hv7l7IyN1PiEi6y8hvZgcbFulruTCTHLCAsrrJQQ"
                )
            it.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    internal fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
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
            // .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(converterFactory)
            // .addCallAdapterFactory(NetworkResponseAdapterFactory())
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