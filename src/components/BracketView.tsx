import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import type { Bracket } from '../specs/NativeTournamentBridge';

interface BracketViewProps {
  bracket: Bracket | null;
}

// Renders { rounds: [{ roundLabel, matches: [{ a, b }], resting? }] } — shared by
// TournamentDetailScreen (JS-generated bracket, Lab 02) and CreateTournamentScreen
// (native-generated bracket, Lab 03). `resting` only appears on round-robin rounds
// with an odd participant count.
export default function BracketView({ bracket }: BracketViewProps) {
  if (!bracket) return null;

  return (
    <View>
      {bracket.rounds.map((round) => (
        <View key={round.roundLabel} style={styles.round}>
          <Text style={styles.roundLabel}>{round.roundLabel}</Text>
          {round.matches.map((match, index) => (
            <Text key={index} style={styles.match}>
              {match.a} vs {match.b}
            </Text>
          ))}
          {!!round.resting && (
            <Text style={styles.resting}>{round.resting} rests this round</Text>
          )}
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  round: {
    marginBottom: 12,
  },
  roundLabel: {
    fontSize: 14,
    fontWeight: '600',
    color: '#4C1D95',
    marginBottom: 4,
  },
  match: {
    fontSize: 14,
    color: '#1C1B1F',
    paddingVertical: 2,
  },
  resting: {
    fontSize: 13,
    fontStyle: 'italic',
    color: '#6B6B6B',
    paddingVertical: 2,
  },
});
