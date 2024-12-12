package id.febimaharani.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.febimaharani.amphibians.R
import id.febimaharani.amphibians.model.Amphibian
import id.febimaharani.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> LoadingScreen(modifier.size(200.dp)) // menampilkan loading di layar saat mengambil data
        is AmphibiansUiState.Success ->
            AmphibiansListScreen(
                amphibians = amphibiansUiState.amphibians,
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                contentPadding = contentPadding // menampilkan daftar amphiibian saat data berhasil di ambil
            )
        else -> ErrorScreen(retryAction, modifier) // menampilkan eroor di layar saat terjadi saat salah ambil data.
    }
}

// layar yang menampilkan pesan loading
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image( // menampilkan gambar loading saat ambil data
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

// layar menampilkan pesan eror dengan tombol coba  lagi
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column( 
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // menyusunn elemen secara vertikal dan horizontal.
    ) {
        Text(stringResource(R.string.loading_failed)) // menampilkan pesan kesalahan saat gagal ambil data
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry)) // tombol coba lagi untuk ambil data
        }
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp) // membuat kartu dengan sudut bulat
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.amphibian_title, amphibian.name, amphibian.type),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start //  menampilkkan nama dan tipe amphibian di bagian atas kartu..
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image), // gambar yang tampil saat gagal menampilkan gambar amphibian
                placeholder = painterResource(id = R.drawable.loading_img) // gambar placeholder saat memuat gambar
            )
            Text(
                text = amphibian.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify, // menampilkan deskripsi amphibian di bawah gambar
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
private fun AmphibiansListScreen(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp) // mengatur jarak
    ) {
        items(
            items = amphibians,
            key = { amphibian ->
                amphibian.name // nama amphibian digunakan sebagai kunci unik
            }
        ) { amphibian ->
            AmphibianCard(amphibian = amphibian, modifier = Modifier.fillMaxSize()) // menampilkan kartu setiap amphibian dalam daftar
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphibiansTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp) // preview layar loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen({}, Modifier.fillMaxSize()) // preview layar error
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansListScreenPreview() {
    AmphibiansTheme {
        val mockData = List(10) {
            Amphibian(
                "Lorem Ipsum - $it",
                "$it",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                        " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
                        " minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                        " ex ea commodo consequat.",
                imgSrc = "" // preview layar daftar amphibian
            )
        }
        AmphibiansListScreen(mockData, Modifier.fillMaxSize()) // menampilkan daftar dengan data contoh
    }
}
