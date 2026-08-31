import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface BracketMatch {
  a: string;
  b: string;
}

export interface BracketRound {
  roundLabel: string;
  matches: BracketMatch[];
  // Only set for round-robin rounds with an odd participant count.
  resting?: string;
}

export interface Bracket {
  rounds: BracketRound[];
}

// Lab 03: generates brackets/schedules natively and persists newly created tournaments
// through TournamentRepository so they show up in the native TournamentListScreen.
export interface Spec extends TurboModule {
  generateBracket(formatKey: string, participantNames: string[]): Bracket;
  createTournament(
    name: string,
    modality: string,
    formatKey: string,
    participantNames: string[],
  ): Bracket;
}

// Resolved lazily for the same reason as NativeNavigationBridge.ts: CreateTournamentScreen
// is part of index.js's eager import graph, which runs before AppRegistry.registerComponent —
// before the New Architecture runtime is ready for TurboModuleRegistry lookups.
let cached: Spec | null = null;
function getBridge(): Spec {
  if (!cached) {
    cached = TurboModuleRegistry.getEnforcing<Spec>('TournamentBridge');
  }
  return cached;
}

export default {
  generateBracket(formatKey: string, participantNames: string[]): Bracket {
    return getBridge().generateBracket(formatKey, participantNames);
  },
  createTournament(
    name: string,
    modality: string,
    formatKey: string,
    participantNames: string[],
  ): Bracket {
    return getBridge().createTournament(name, modality, formatKey, participantNames);
  },
};
