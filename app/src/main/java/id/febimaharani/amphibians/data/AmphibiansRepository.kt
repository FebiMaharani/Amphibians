package id.febimaharani.amphibians.data

import id.febimaharani.amphibians.model.Amphibian
import id.febimaharani.amphibians.network.AmphibiansApiService

/**
 * Mengambil data amfibi.
 */
// TODO 1 : interface repository untuk mengatur pengambilan dataamfibi.
interface AmphibiansRepository {
    /** ambil daftar amfibi */
    // TODO 2 : mendefinisikan fungsi mengambil daftar amfibi dari data layer.
    suspend fun getAmphibians(): List<Amphibian>
}

/**
 * Implementasi repository untuk mengambil data amfibi dari layanan jaringan.
 */
 // TODO 3 : implementasi repository dengan dependensi ke AmphibiansApiServicce.
class DefaultAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService // TODO 4 : layanan API digunakan untuk mengakses data amfibi.
) : AmphibiansRepository {
    /** mengambil daftaramfibi */
    // TODO 5 : implementasi fungsi getAmphibians untuk mendapatkan data layer network.
    override suspend fun getAmphibians(): List<Amphibian> = amphibiansApiService.getAmphibians()
}
