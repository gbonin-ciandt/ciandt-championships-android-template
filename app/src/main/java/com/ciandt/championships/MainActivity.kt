package com.ciandt.championships

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ciandt.championships.ui.theme.CITChampionshipsTheme
import com.ciandt.championships.ui.tournamentlist.TournamentListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CITChampionshipsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TournamentListScreen()
                }
            }
        }
    }
}
