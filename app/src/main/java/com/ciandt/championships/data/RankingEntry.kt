package com.ciandt.championships.data

data class RankingEntry(
    val personName: String,
    val points: Int,
    val modalities: List<String>,
)
