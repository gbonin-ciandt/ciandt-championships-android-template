package com.ciandt.championships.data

data class Tournament(
    val id: String,
    val name: String,
    val modality: String,
    val format: TournamentFormat,
    val participantCount: Int,
    val status: TournamentStatus,
)
