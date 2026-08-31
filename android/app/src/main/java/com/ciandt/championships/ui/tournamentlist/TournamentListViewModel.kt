package com.ciandt.championships.ui.tournamentlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ciandt.championships.data.Tournament
import com.ciandt.championships.data.TournamentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TournamentListViewModel : ViewModel() {

    // Collects the repository's own StateFlow (rather than snapshotting it once) so a
    // tournament created via the Lab 03 TournamentBridge TurboModule shows up here without
    // needing a fresh ViewModel instance.
    val tournaments: StateFlow<List<Tournament>> = TournamentRepository.tournaments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TournamentRepository.getTournaments(),
        )
}
