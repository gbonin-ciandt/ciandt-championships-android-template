package com.ciandt.championships.data

/**
 * In-memory placeholder data. Lab 03 replaces bracket/pairing generation with a native
 * TurboModule; this repository only exists to give the native screens in Lab 01/02 real
 * content to render before React Native is introduced.
 */
object TournamentRepository {

    fun getTournaments(): List<Tournament> = listOf(
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
}
