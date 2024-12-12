package id.febimaharani.amphibians

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import id.febimaharani.amphibians.ui.AmphibiansApp
import id.febimaharani.amphibians.ui.theme.AmphibiansTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // memanggil  metode onCreate dari superClass
        setContent {
            AmphibiansTheme { // menerapkan tema aplikasi
                Surface(
                    modifier = Modifier.fillMaxSize(), // isi maks ukuran layar
                    color = MaterialTheme.colorScheme.background // atur warna background sesuai tema
                ) {
                    AmphibiansApp() // menampilkan tampilan utama aplikasi.
                }
            }
        }
    }
}
