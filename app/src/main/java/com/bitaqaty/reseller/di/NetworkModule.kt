package com.bitaqaty.reseller.di

import android.content.Context
import com.bitaqaty.reseller.MainApplication
import com.bitaqaty.reseller.data.datasource.remote.ApiService
import com.bitaqaty.reseller.data.datasource.remote.ApiURL
import com.bitaqaty.reseller.utilities.Globals.lang
import com.bitaqaty.reseller.utilities.Utils.isCashInApp
import com.bitaqaty.reseller.utilities.Utils.isMadaApp
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
import javax.net.ssl.SSLContext


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
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Singleton
    @Provides
    fun provideContext(application: MainApplication): Context {
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
            var deviceType = ""
            if (isMadaApp()) {
                deviceType = "SUREPAY"
            } else if (isCashInApp()) {
                deviceType = "CACHIN"
            }
            val requestBuilder = it.request().newBuilder()
                //hear you can add all headers you want by calling 'requestBuilder.addHeader(name ,  value)'
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Content-Type", "application/json")
                .header("locale", lang)
                .header("device-id", "94d2eb615c7a291a")
                .header("whitelabel-code", "BITAQATY_BUSINESS")
                .header("OS", "ANDROID")
                .header("Application-name", "BITAQATY_BUSINESS_MOBILE")
                .header(
                    "Authorization",
                    "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlenphdHN1YiIsImlzcyI6IkJpdGFxYXR5IEJ1c2luZXNzIiwiZXhwIjoxNzUzNTY2MDIyLCJpYXQiOjE3MjIwMzAwMjIsImp0aSI6ImRkNWVmOWQ2LTRkZTMtNGZlNC05YTUxLTRiNjcyZTY5YTQyNiJ9.iF-1BQnUxfp5Q83S9XCbgbtQQZKuOXdoi5ePa4zyqk4mppJoMnuCdf7PyUpXWiHmNHdHV9zfTHbvOzQ_jDFjdFrRsuXGD9PtifkF7Uqp0CeQJYzsiVg_7ABb_DdALQUUDIp7qp4D4-PrXJtXpLedV7-mqQnQRcCqtnWIdwya89f6rVvzhX1aE-QBDxG27PdQgBnBdlexts7Oz2yjI7twz88Tdxwes_Eb116s409Wwj7W7GBAnjYGtP4E6ioXc3f3XZXNAS7iB0djnYjr6w1E_GXSlHFb8D3MHn3uLgUqlf9pxD2pXeSFN-8hfsce4Q2tnDwEu6qF2cb_rz6CNUnXrQ"
                )
//                .header("partner-device", "CACHIN")
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