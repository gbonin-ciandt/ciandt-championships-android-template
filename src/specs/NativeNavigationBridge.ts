import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

// Lab 02: lets the RN Tournament Detail screen push forward onto the native NavHost
// (History / Ranking) instead of finishing back to the native list first.
export interface Spec extends TurboModule {
  openHistory(): void;
  openRanking(): void;
}

// Resolved lazily (on first call) rather than at module-eval time: this spec is
// imported as part of index.js's initial require graph, which runs before
// AppRegistry.registerComponent — before the New Architecture runtime is ready
// for TurboModuleRegistry lookups. Resolving eagerly here throws during that
// first pass and aborts registration entirely.
let cached: Spec | null = null;
function getBridge(): Spec {
  if (!cached) {
    cached = TurboModuleRegistry.getEnforcing<Spec>('NavigationBridge');
  }
  return cached;
}

export default {
  openHistory(): void {
    getBridge().openHistory();
  },
  openRanking(): void {
    getBridge().openRanking();
  },
};
