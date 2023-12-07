package com.github.vinizaan.vehiclevaultsystem.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Carro (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var modelo:String,
    var preco:String,
    var ano:String?,
    var kmRodados:String?
)