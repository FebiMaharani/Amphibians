package id.febimaharani.amphibians.data

import id.febimaharani.amphibians.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibiansRepository: AmphibiansRepository // Menyediakan akses ke AmphibiansRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) // Menggunakan converter untuk JSON
        .baseUrl(BASE_URL) // Menentukan base URL untuk API
        .build()

    // Retrofit service untuk membuat pemanggil API
    private val retrofitService: AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java) // membuat instance dari AmphibiansApiService
    }

    // Repository untuk mengatur suumber data API dan lokal
    override val amphibiansRepository: AmphibiansRepository by lazy {
        DefaultAmphibiansRepository(retrofitService) // menginisialisasi repoditory dengan service retrofit
    }
}
