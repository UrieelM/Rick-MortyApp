package com.example.rickmortyapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.models.Location
import com.example.rickmortyapp.models.Origin
import com.example.rickmortyapp.services.CharacterService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun CharacterDetailScreen(id: Int, innerPaddingValues: PaddingValues) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var character by remember {
        mutableStateOf(Character(
            id = 0,
            name = "",
            species = "",
            status = "",
            image = "",
            location = Location(name = "", url = ""),
            gender = "",
            created = "",
            episode = listOf(""),
            origin = Origin(name = "", url = ""),
            type = "",
            url = "",
        ))
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            val BASE_URL = "https://rickandmortyapi.com/api/"
            val characterService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)
            isLoading = true
            character = characterService.getCharacterById(id)
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
                .background(Color(0xFF1A1A2E)), // Fondo oscuro
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF16213E)) // Fondo de la tarjeta
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(character.image),
                        contentDescription = character.name,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )


                    Text(
                        text = character.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color(0xFF00A8E1) // Color azul
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Status: ${character.status}",
                        style = TextStyle(fontSize = 16.sp, color = Color(0xFF00C4D7)) // Color cian
                    )
                    Text(
                        text = "Especie: ${character.species}",
                        style = TextStyle(fontSize = 16.sp, color = Color(0xFF00C4D7)) // Color cian
                    )
                    Text(
                        text = "Genero: ${character.gender}",
                        style = TextStyle(fontSize = 16.sp, color = Color(0xFF00C4D7)) // Color cian
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Text(
                        text = "Origen: ${character.origin.name}",
                        style = TextStyle(fontSize = 16.sp, color = Color(0xFFDA77B9)) // Color morado
                    )
                    Text(
                        text = "Localizacion: ${character.location.name}",
                        style = TextStyle(fontSize = 16.sp, color = Color(0xFFDA77B9)) // Color morado
                    )
                }
            }
        }
    }
}