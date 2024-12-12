package id.febimaharani.amphibians.network

import id.febimaharani.amphibians.model.Amphibian
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians") // menggunakan metode GET untuk mengambil data dari amphibians
    suspend fun getAmphibians(): List<Amphibian> 
    // mengembalikan daftar objek Amphibian
}
