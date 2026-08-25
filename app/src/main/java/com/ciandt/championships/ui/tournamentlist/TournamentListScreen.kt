package com.ciandt.championships.ui.tournamentlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciandt.championships.data.Tournament
import com.ciandt.championships.data.TournamentRepository
import com.ciandt.championships.data.TournamentStatus
import com.ciandt.championships.ui.common.OriginBadge
import com.ciandt.championships.ui.theme.CiandtChampionshipsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentListScreen(
    viewModel: TournamentListViewModel = viewModel(),
    onNavigateToHistory: () -> Unit = {},
    onNavigateToRanking: () -> Unit = {},
) {
    val tournaments by viewModel.tournaments.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("CI&T Championships") })
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OriginBadge()
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onNavigateToHistory) { Text("Histórico") }
                TextButton(onClick = onNavigateToRanking) { Text("Ranking") }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(tournaments, key = { it.id }) { tournament ->
                    TournamentCard(tournament)
                }
            }
        }
    }
}

@Composable
private fun TournamentCard(tournament: Tournament) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(tournament.name, style = MaterialTheme.typography.titleMedium)
                StatusBadge(tournament.status)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${tournament.modality} · ${tournament.format.label} · ${tournament.participantCount} participants",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun StatusBadge(status: TournamentStatus) {
    val (container, content) = when (status) {
        TournamentStatus.UPCOMING ->
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        TournamentStatus.IN_PROGRESS ->
            MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        TournamentStatus.FINISHED ->
            MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(color = container, contentColor = content, shape = MaterialTheme.shapes.small) {
        Text(
            status.label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TournamentListPreview() {
    CiandtChampionshipsTheme {
        Column {
            OriginBadge()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(16.dp),
            ) {
                items(TournamentRepository.getTournaments()) { TournamentCard(it) }
            }
        }
    }
}
