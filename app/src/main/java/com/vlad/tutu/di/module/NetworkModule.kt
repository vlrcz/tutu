package com.vlad.tutu.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vlad.tutu.feature.auth.AuthConfig
import com.vlad.tutu.feature.auth.AuthInterceptor
import com.vlad.tutu.core.data.GithubApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun providesOkHttpClient(
            interceptors: Set<@JvmSuppressWildcards Interceptor>
        ): OkHttpClient {
            val okHttpClient = OkHttpClient.Builder()
            interceptors.forEach {
                okHttpClient.addInterceptor(it)
            }
            return okHttpClient
                .followRedirects(true)
                .build()
        }

        @Provides
        @IntoSet
        fun provideLoggingInterceptor(): Interceptor {
            return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Provides
        @Singleton
        fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(AuthConfig.API_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        fun providesApi(retrofit: Retrofit): GithubApi {
            return retrofit.create()
        }

        @Provides
        @Singleton
        fun providesMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }
    }

    @Binds
    @IntoSet
    abstract fun providerHeaderInterceptor(
        authInterceptor: AuthInterceptor
    ): Interceptor
}