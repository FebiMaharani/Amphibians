package id.febimaharani.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import id.febimaharani.amphibians.AmphibiansApplication
import id.febimaharani.amphibians.data.AmphibiansRepository
import id.febimaharani.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// status UI untuk layar beranda 
sealed interface AmphibiansUiState {
    // status berhasil dengan daftar amphibian
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState 
    // status error saat mengambil data
    object Error : AmphibiansUiState 
    // status loading saat mengambil data
    object Loading : AmphibiansUiState 
}

// viewmodel berisi data aplikasi dan metode untuk mengambil data
class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set 
        // status UI yang bisa berubah dimulai dari status Loading
    init {
        getAmphibians() 
        // memanggil metode untuk mengambil data amphibian saat viewmodel diinisialisasi.
    }

    fun getAmphibians() {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading // mengatur status UI menjadi loading saat mengambil data
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansRepository.getAmphibians()) // mengambil data dan mengatur status menjadi sucsess jika berhasil
            } catch (e: IOException) {
                AmphibiansUiState.Error // status menjadi error jika terjadi IOExcecption
            } catch (e: HttpException) {
                AmphibiansUiState.Error // status menjadi eror jika terjadi HttpException 
            }
        }
    }

    /**
     * Factory untuk [AmphibiansViewModel] mengambil [AmphibiansRepository] sebagai dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository 
                // mengambil repository dari container aplikasi
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository) 
                // inisialisasi viewmodel dengan repository
            }
        }
    }
}
