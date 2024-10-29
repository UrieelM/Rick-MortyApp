package com.example.rickmortyapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickmortyapp.models.Character
import com.example.rickmortyapp.services.CharacterService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController){
    val scope = rememberCoroutineScope()
    var characters by remember {
        mutableStateOf(listOf<Character>())
    }
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            //la llamada a la API

            try {
                isLoading = true
                val BASE_URL = "https://rickandmortyapi.com/api/"
                val characterService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CharacterService::class.java)

                val response = characterService.getCharacters()
                characters = response.results
                Log.i("HomeScreenResponse",characters.toString())
                isLoading = false
            }
            catch (e:Exception){
                Log.e("HomeScreenError", "Error fetching characters", e)
                characters = listOf()
                isLoading = false
            }


        }
    }

    if(isLoading){
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }
    else{
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(characters){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(200.dp)
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("detail/${it.id}")
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = it.image,
                            contentDescription = it.name,
                            modifier = Modifier.fillMaxWidth().weight(3f),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = it.name,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )

                    }
                }
            }
        }
    }
}


