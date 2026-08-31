package com.ciandt.championships.tournamentbridge

import com.ciandt.championships.data.Tournament
import com.ciandt.championships.data.TournamentFormat
import com.ciandt.championships.data.TournamentRepository
import com.ciandt.championships.data.TournamentStatus
import com.facebook.fbreact.specs.NativeTournamentBridgeSpec
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import java.util.UUID

// Lab 03: generates brackets/schedules natively (BracketGenerator) and, on creation,
// persists the new tournament through TournamentRepository so it shows up in the native
// TournamentListScreen without any extra RN-side plumbing.
class TournamentBridgeModule(reactContext: ReactApplicationContext) :
    NativeTournamentBridgeSpec(reactContext) {

    override fun generateBracket(formatKey: String, participantNames: ReadableArray): WritableMap {
        return BracketGenerator.generate(formatKey, participantNames.toStringList()).toWritableMap()
    }

    override fun createTournament(
        name: String,
        modality: String,
        formatKey: String,
        participantNames: ReadableArray,
    ): WritableMap {
        require(name.isNotBlank()) { "Tournament name is required" }
        val names = participantNames.toStringList()
        val bracket = BracketGenerator.generate(formatKey, names)

        TournamentRepository.addTournament(
            Tournament(
                id = UUID.randomUUID().toString(),
                name = name,
                modality = modality,
                format = TournamentFormat.valueOf(formatKey),
                participantCount = names.size,
                status = TournamentStatus.UPCOMING,
            ),
        )

        return bracket.toWritableMap()
    }

    private fun ReadableArray.toStringList(): List<String> =
        (0 until size()).map { getString(it).orEmpty() }
}
