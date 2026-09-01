package com.ciandt.championships.ui.history

import androidx.lifecycle.ViewModel
import com.ciandt.championships.data.Tournament
import com.ciandt.championships.data.TournamentRepository
import com.ciandt.championships.data.TournamentStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel : ViewModel() {

    private val _finishedTournaments = MutableStateFlow<List<Tournament>>(emptyList())
    val finishedTournaments: StateFlow<List<Tournament>> = _finishedTournaments

    init {
        _finishedTournaments.value = TournamentRepository.getTournaments()
            .filter { it.status == TournamentStatus.FINISHED }
    }
}
