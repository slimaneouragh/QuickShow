package com.example.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "translation")
data class Translations(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val from: String,
    val to: String,
    val textFrom: String,
    val textTo: String,
    val saved: Boolean = false
)
