package com.example.hellynoteapp.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}