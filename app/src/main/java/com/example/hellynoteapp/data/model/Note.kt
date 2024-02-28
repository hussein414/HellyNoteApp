package com.example.hellynoteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val pickedDate:LocalDate,
    val pickedTime :LocalTime,
    @PrimaryKey val id: Int? = null
)
