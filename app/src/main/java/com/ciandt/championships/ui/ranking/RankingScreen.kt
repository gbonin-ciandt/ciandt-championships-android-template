package com.ciandt.championships.ui.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ciandt.championships.data.RankingEntry
import com.ciandt.championships.data.RankingRepository
import com.ciandt.championships.ui.common.OriginBadge
import com.ciandt.championships.ui.theme.CiandtChampionshipsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(viewModel: RankingViewModel = viewModel()) {
    val ranking by viewModel.ranking.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ranking global") })
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OriginBadge()
            RankingList(ranking)
        }
    }
}

@Composable
private fun RankingList(ranking: List<RankingEntry>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(ranking) { index, entry ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "#${index + 1}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(40.dp),
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(entry.personName, style = MaterialTheme.typography.titleMedium)
                        Text(
                            entry.modalities.joinToString(" · "),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Text("${entry.points} pts", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingPreview() {
    CiandtChampionshipsTheme {
        Column {
            OriginBadge()
            RankingList(RankingRepository.getRanking())
        }
    }
}
