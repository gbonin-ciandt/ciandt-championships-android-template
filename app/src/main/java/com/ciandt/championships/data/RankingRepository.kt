package com.ciandt.championships.data

object RankingRepository {

    fun getRanking(): List<RankingEntry> = listOf(
        RankingEntry(personName = "Ana Souza", points = 260, modalities = listOf("Sinuca", "FIFA")),
        RankingEntry(personName = "Bruno Lima", points = 190, modalities = listOf("Mortal Kombat")),
        RankingEntry(personName = "Carla Nunes", points = 165, modalities = listOf("Futebol", "Sinuca")),
        RankingEntry(personName = "Diego Alves", points = 130, modalities = listOf("FIFA")),
        RankingEntry(personName = "Elisa Prado", points = 95, modalities = listOf("Futebol")),
    ).sortedByDescending { it.points }
}
