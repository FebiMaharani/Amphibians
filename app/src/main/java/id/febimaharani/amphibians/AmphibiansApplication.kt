package id.febimaharani.amphibians

import android.app.Application
import id.febimaharani.amphibians.data.AppContainer
import id.febimaharani.amphibians.data.DefaultAppContainer

// kelas ini berfungsi sebagai titik masuk aplikasi dan mengatur dependency injection
class AmphibiansApplication : Application() {
    // instance AppContainer yang digunakan oleh kelas lain untuk mendapat dependensi
    // menyediakan aksess kke repository lain.
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate() // menginisialisasi instance AppContainer untuk dependency injection
        container = DefaultAppContainer() // menginisialisasi container dengan implementasi DefaultAppContainer
    }
}
