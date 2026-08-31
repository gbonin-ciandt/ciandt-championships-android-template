package com.ciandt.championships.tournamentbridge

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

data class BracketMatch(val a: String, val b: String)

data class BracketRound(
    val roundLabel: String,
    val matches: List<BracketMatch>,
    val resting: String? = null,
)

data class Bracket(val rounds: List<BracketRound>)

fun Bracket.toWritableMap(): WritableMap {
    val roundsArray = Arguments.createArray()
    for (round in rounds) {
        val matchesArray = Arguments.createArray()
        for (match in round.matches) {
            matchesArray.pushMap(
                Arguments.createMap().apply {
                    putString("a", match.a)
                    putString("b", match.b)
                },
            )
        }
        roundsArray.pushMap(
            Arguments.createMap().apply {
                putString("roundLabel", round.roundLabel)
                putArray("matches", matchesArray)
                if (round.resting != null) putString("resting", round.resting) else putNull("resting")
            },
        )
    }
    return Arguments.createMap().apply { putArray("rounds", roundsArray) }
}

// Lab 03: pure, RN-free generation logic for the three supported tournament formats.
// formatKey matches TournamentFormat's enum name (SINGLE_ELIMINATION / ROUND_ROBIN / SWISS).
object BracketGenerator {

    fun generate(formatKey: String, participantNames: List<String>): Bracket {
        require(participantNames.size >= 2) { "At least 2 participants are required" }
        val rounds = when (formatKey) {
            "SINGLE_ELIMINATION" -> singleElimination(participantNames)
            "ROUND_ROBIN" -> roundRobin(participantNames)
            "SWISS" -> swissFirstRound(participantNames)
            else -> throw IllegalArgumentException("Unknown format: $formatKey")
        }
        return Bracket(rounds)
    }

    private fun nextPowerOfTwo(n: Int): Int {
        var power = 1
        while (power < n) power *= 2
        return power
    }

    // Pads the field with "Bye" entries up to the next power of two, then synthesizes every
    // subsequent round as placeholder "Winner of R{n}M{i}" labels (no real advancement, since
    // no results exist yet at creation time).
    private fun singleElimination(names: List<String>): List<BracketRound> {
        val size = nextPowerOfTwo(names.size)
        val padded = names + List(size - names.size) { "Bye" }
        val rounds = mutableListOf<BracketRound>()

        val firstRoundMatches = padded.chunked(2).map { BracketMatch(it[0], it[1]) }
        rounds += BracketRound("Round 1", firstRoundMatches)

        var matchesInRound = firstRoundMatches.size / 2
        var roundNumber = 2
        while (matchesInRound >= 1) {
            val matches = (0 until matchesInRound).map { i ->
                BracketMatch(
                    "Winner of R${roundNumber - 1}M${i * 2 + 1}",
                    "Winner of R${roundNumber - 1}M${i * 2 + 2}",
                )
            }
            rounds += BracketRound("Round $roundNumber", matches)
            matchesInRound /= 2
            roundNumber += 1
        }
        return rounds
    }

    // Only round 1 is generated: later rounds depend on round-1 results, which don't exist yet.
    private fun swissFirstRound(names: List<String>): List<BracketRound> {
        val matches = names.chunked(2).map { pair ->
            BracketMatch(pair[0], pair.getOrElse(1) { "Bye" })
        }
        return listOf(BracketRound("Round 1 pairings", matches))
    }

    // Standard circle method: pads to an even count with a virtual "Rest" slot when odd,
    // fixes one player and rotates the rest across n-1 rounds so every pair meets exactly
    // once. Any pairing against "Rest" becomes that round's `resting` participant instead
    // of a match.
    private fun roundRobin(names: List<String>): List<BracketRound> {
        val players = if (names.size % 2 != 0) names + "Rest" else names
        val n = players.size
        val arr = players.toMutableList()
        val rounds = mutableListOf<BracketRound>()

        repeat(n - 1) { roundIndex ->
            val matches = mutableListOf<BracketMatch>()
            var resting: String? = null
            for (i in 0 until n / 2) {
                val a = arr[i]
                val b = arr[n - 1 - i]
                when {
                    a == "Rest" -> resting = b
                    b == "Rest" -> resting = a
                    else -> matches += BracketMatch(a, b)
                }
            }
            rounds += BracketRound("Round ${roundIndex + 1}", matches, resting)

            val fixed = arr[0]
            val rotating = arr.subList(1, n).toMutableList()
            rotating.add(0, rotating.removeAt(rotating.size - 1))
            arr.clear()
            arr.add(fixed)
            arr.addAll(rotating)
        }
        return rounds
    }
}
