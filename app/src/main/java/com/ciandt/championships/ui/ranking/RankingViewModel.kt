package com.ciandt.championships.ui.ranking

import androidx.lifecycle.ViewModel
import com.ciandt.championships.data.RankingEntry
import com.ciandt.championships.data.RankingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RankingViewModel : ViewModel() {

    private val _ranking = MutableStateFlow<List<RankingEntry>>(emptyList())
    val ranking: StateFlow<List<RankingEntry>> = _ranking

    init {
        _ranking.value = RankingRepository.getRanking()
    }
}
