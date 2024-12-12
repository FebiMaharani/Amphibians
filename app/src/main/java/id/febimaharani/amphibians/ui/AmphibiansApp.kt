package id.febimaharani.amphibians.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import id.febimaharani.amphibians.R
import id.febimaharani.amphibians.ui.screens.AmphibiansViewModel
import id.febimaharani.amphibians.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class) 
@Composable
fun AmphibiansApp() { // menyusun tampilan menggunakan scaffold
    Scaffold( 
        modifier = Modifier.fillMaxSize(), // ukuran max layar
        topBar = { // mengatur topappbar  bagian atas aplikasi
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name), // menampilkan nama aplikasi dari string
                        style = MaterialTheme.typography.headlineMedium // mengatur gaya text judul
                    )
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(), mengisi ukuran maksimum layar konten
            color = MaterialTheme.colorScheme.background // mengatur warna background ssesuai tema
        ) {
            val amphibiansViewModel: AmphibiansViewModel =
                viewModel(factory = AmphibiansViewModel.Factory) // mengambil instance ViewModel 
            HomeScreen( // menampilkann HomeScreen dengan statuts UI ViewModel dari aksi retry
                amphibiansUiState = amphibiansViewModel.amphibiansUiState, // status UI layar beranda
                retryAction = amphibiansViewModel::getAmphibians, // coba ambil data lagi
                modifier = Modifier.fillMaxSize(),// mengisi ukuran maks layar homescreen
                contentPadding = it
            )
        }
    }
}
