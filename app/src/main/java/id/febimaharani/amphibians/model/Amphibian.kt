package id.febimaharani.amphibians.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// data class mendefinisikan ampphibians termasuk  nama, type, description, dan image url
@Serializable
data class Amphibian(
    val name: String, // nama amphibians
    val type: String, // tipe amphibians
    val description: String, // deskripsi amphibians
    @SerialName("img_src") val imgSrc: String // url gambar amphibians
)
