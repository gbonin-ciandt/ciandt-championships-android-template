package com.ciandt.championships.ui.navigation

object Routes {
    const val TOURNAMENT_LIST = "tournament_list"
    const val HISTORY = "history"
    const val RANKING = "ranking"

    // Lab 02: lets a fresh MainActivity instance (started from the RN NavigationBridge) jump
    // straight to History/Ranking instead of TOURNAMENT_LIST, so its own back stack has no
    // TOURNAMENT_LIST entry to pop back to (back falls through to reveal RN Detail instead).
    const val EXTRA_START_DESTINATION = "startDestination"
}
