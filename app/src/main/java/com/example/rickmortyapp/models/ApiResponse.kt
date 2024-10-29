package com.example.rickmortyapp.models

data class ApiResponse(
    val info: Info,
    val characters: List<Character>
)