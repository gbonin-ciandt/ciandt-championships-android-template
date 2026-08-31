package com.ciandt.championships.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory tournament store. Seeded with placeholder data for Lab 01/02's native screens;
 * Lab 03's TournamentBridge TurboModule appends real tournaments created from RN via
 * [addTournament].
 */
object TournamentRepository {

    private val seed = listOf(
        Tournament(
            id = "t1",
            name = "Sinuca Q3 2026",
            modality = "Sinuca",
            format = TournamentFormat.SINGLE_ELIMINATION,
            participantCount = 8,
            status = TournamentStatus.IN_PROGRESS,
        ),
        Tournament(
            id = "t2",
            name = "FIFA Office Cup",
            modality = "FIFA",
            format = TournamentFormat.SWISS,
            participantCount = 16,
            status = TournamentStatus.UPCOMING,
        ),
        Tournament(
            id = "t3",
            name = "Mortal Kombat Friday",
            modality = "Mortal Kombat",
            format = TournamentFormat.SINGLE_ELIMINATION,
            participantCount = 8,
            status = TournamentStatus.FINISHED,
        ),
        Tournament(
            id = "t4",
            name = "Futebol de Botão H1",
            modality = "Futebol",
            format = TournamentFormat.ROUND_ROBIN,
            participantCount = 6,
            status = TournamentStatus.FINISHED,
        ),
    )

    private val _tournaments = MutableStateFlow(seed)
    val tournaments: StateFlow<List<Tournament>> = _tournaments.asStateFlow()

    fun getTournaments(): List<Tournament> = _tournaments.value

    fun addTournament(tournament: Tournament) {
        _tournaments.value = _tournaments.value + tournament
    }
}
