import type { Bracket, BracketMatch, BracketRound } from '../specs/NativeTournamentBridge';

// Naive placeholder bracket/pairing generator. Lab 03 replaces this with a native
// TurboModule that generates brackets for real — this only exists so Lab 02's RN screen
// has a pairing structure to render, using nothing but the participant count Lab 01 wired up.

function nextPowerOfTwo(n: number): number {
  let power = 1;
  while (power < n) power *= 2;
  return power;
}

function singleEliminationRounds(participantNames: string[]): BracketRound[] {
  const size = nextPowerOfTwo(participantNames.length);
  const padded = [...participantNames];
  while (padded.length < size) padded.push('Bye');

  const rounds: BracketRound[] = [];

  const firstRoundMatches: BracketMatch[] = [];
  for (let i = 0; i < padded.length; i += 2) {
    firstRoundMatches.push({ a: padded[i], b: padded[i + 1] });
  }
  rounds.push({ roundLabel: 'Round 1', matches: firstRoundMatches });

  let matchesInRound = firstRoundMatches.length / 2;
  let roundNumber = 2;
  while (matchesInRound >= 1) {
    const matches: BracketMatch[] = [];
    for (let i = 0; i < matchesInRound; i++) {
      matches.push({
        a: `Winner of R${roundNumber - 1}M${i * 2 + 1}`,
        b: `Winner of R${roundNumber - 1}M${i * 2 + 2}`,
      });
    }
    rounds.push({ roundLabel: `Round ${roundNumber}`, matches });
    matchesInRound = matchesInRound / 2;
    roundNumber += 1;
  }

  return rounds;
}

function swissFirstRound(participantNames: string[]): BracketRound[] {
  const matches: BracketMatch[] = [];
  for (let i = 0; i < participantNames.length; i += 2) {
    matches.push({ a: participantNames[i], b: participantNames[i + 1] ?? 'Bye' });
  }
  return [{ roundLabel: 'Round 1 pairings', matches }];
}

// formatKey is the raw TournamentFormat enum name (SINGLE_ELIMINATION / ROUND_ROBIN / SWISS).
// Returns { rounds } or null when the format has no bracket to show (round-robin, unknown).
export function generateBracket(formatKey: string, participantNames: string[]): Bracket | null {
  if (formatKey === 'SINGLE_ELIMINATION') {
    return { rounds: singleEliminationRounds(participantNames) };
  }
  if (formatKey === 'SWISS') {
    return { rounds: swissFirstRound(participantNames) };
  }
  return null;
}
