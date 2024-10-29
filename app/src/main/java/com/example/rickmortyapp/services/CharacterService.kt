package com.example.rickmortyapp.services

import com.example.rickmortyapp.models.ApiResponse
import com.example.rickmortyapp.models.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService{
        @GET("character")
        suspend fun getCharacters(): ApiResponse

        @GET("character/{id}")
        suspend fun getCharacterById(@Path("id") id: Int) : Character
}
