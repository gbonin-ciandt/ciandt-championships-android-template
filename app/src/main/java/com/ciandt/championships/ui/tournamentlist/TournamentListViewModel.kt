package com.ciandt.championships.ui.tournamentlist

import androidx.lifecycle.ViewModel
import com.ciandt.championships.data.Tournament
import com.ciandt.championships.data.TournamentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TournamentListViewModel : ViewModel() {

    private val _tournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val tournaments: StateFlow<List<Tournament>> = _tournaments

    init {
        _tournaments.value = TournamentRepository.getTournaments()
    }
}
