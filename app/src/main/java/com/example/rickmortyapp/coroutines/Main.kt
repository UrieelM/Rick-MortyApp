package com.example.rickmortyapp.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    cAsync()
}

fun cLaunch(){
    runBlocking {
        launch {
            println("Mi super aplicacion de Rick & Morty")
            val data = consultaBaseDeDatos()
            println(data)
        }
    }
}

fun cAsync(){
    runBlocking {
        val result = async {
            println("Consultando datos")
            delay(3000)
            20
        }
        println(result.await())
    }
}

suspend fun consultaBaseDeDatos() : String{
    println("Consultando base de datos")
    delay(2000)
    return "Datos traidos"
}