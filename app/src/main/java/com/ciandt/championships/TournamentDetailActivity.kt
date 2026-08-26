package com.ciandt.championships

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ciandt.championships.data.Tournament
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

// Hosts the "TournamentDetail" RN surface (Lab 01). The tapped Tournament is passed in as
// Intent extras and forwarded to JS as initialProperties via getLaunchOptions().
class TournamentDetailActivity : ReactActivity() {

    override fun getMainComponentName(): String = "TournamentDetail"

    override fun createReactActivityDelegate(): ReactActivityDelegate =
        object : DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled) {
            override fun getLaunchOptions(): Bundle =
                Bundle().apply {
                    putString(EXTRA_TOURNAMENT_NAME, intent.getStringExtra(EXTRA_TOURNAMENT_NAME))
                    putString(EXTRA_MODALITY, intent.getStringExtra(EXTRA_MODALITY))
                    putString(EXTRA_FORMAT, intent.getStringExtra(EXTRA_FORMAT))
                    putString(EXTRA_FORMAT_KEY, intent.getStringExtra(EXTRA_FORMAT_KEY))
                    putInt(EXTRA_PARTICIPANT_COUNT, intent.getIntExtra(EXTRA_PARTICIPANT_COUNT, 0))
                    putString(EXTRA_STATUS, intent.getStringExtra(EXTRA_STATUS))
                }
        }

    companion object {
        private const val EXTRA_TOURNAMENT_NAME = "tournamentName"
        private const val EXTRA_MODALITY = "modality"
        private const val EXTRA_FORMAT = "format"
        private const val EXTRA_FORMAT_KEY = "formatKey"
        private const val EXTRA_PARTICIPANT_COUNT = "participantCount"
        private const val EXTRA_STATUS = "status"

        fun newIntent(context: Context, tournament: Tournament): Intent =
            Intent(context, TournamentDetailActivity::class.java).apply {
                putExtra(EXTRA_TOURNAMENT_NAME, tournament.name)
                putExtra(EXTRA_MODALITY, tournament.modality)
                putExtra(EXTRA_FORMAT, tournament.format.label)
                putExtra(EXTRA_FORMAT_KEY, tournament.format.name)
                putExtra(EXTRA_PARTICIPANT_COUNT, tournament.participantCount)
                putExtra(EXTRA_STATUS, tournament.status.label)
            }
    }
}
