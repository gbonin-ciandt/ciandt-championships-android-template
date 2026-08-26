package com.ciandt.championships

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ciandt.championships.ui.history.HistoryScreen
import com.ciandt.championships.ui.navigation.Routes
import com.ciandt.championships.ui.ranking.RankingScreen
import com.ciandt.championships.ui.theme.CiandtChampionshipsTheme
import com.ciandt.championships.ui.tournamentlist.TournamentListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startDestination = intent.getStringExtra(Routes.EXTRA_START_DESTINATION)
            ?: Routes.TOURNAMENT_LIST
        setContent {
            CiandtChampionshipsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CiandtChampionshipsApp(startDestination = startDestination)
                }
            }
        }
    }
}

@Composable
private fun CiandtChampionshipsApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.TOURNAMENT_LIST,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.TOURNAMENT_LIST) {
            TournamentListScreen(
                onNavigateToHistory = { navController.navigate(Routes.HISTORY) },
                onNavigateToRanking = { navController.navigate(Routes.RANKING) },
            )
        }
        composable(Routes.HISTORY) {
            HistoryScreen()
        }
        composable(Routes.RANKING) {
            RankingScreen()
        }
    }
}
