package com.example.rickmortyapp.models

data class ApiResponse(
    val info: Info,
    val results: List<Character>
)