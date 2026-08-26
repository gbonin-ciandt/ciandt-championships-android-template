# Lab 02 — Brownfield Navigation (lab-02-solution)

Scope: exactly what `docs/rn-advanced-lab/02-brownfield-navigation.md` asks for. No bottom-tab
work (that was explicitly deferred after discussion with the user).

## Goal recap

- Replace the Lab 01 placeholder RN screen with a real Tournament Detail screen (name,
  modality, format, status, participant list, bracket/pairing structure when relevant).
- Add RN → native forward navigation: buttons on the RN screen open the existing native
  **History** and **Ranking** screens, pushed forward (not `finish()` + reopen).
- Back button from History/Ranking must return to the RN Detail screen, not the native list.
- No duplicate screens if the user mashes the navigation buttons.

## Back-stack design (the tricky part)

`TournamentDetailActivity` is already a separate Activity stacked on top of `MainActivity`
(Lab 01, standard launch mode, no flags). To satisfy "History's back button returns to RN
Detail, not the list," a **second, independent `MainActivity` instance** is started on top of
`TournamentDetailActivity`, parameterized to start directly at `HISTORY`/`RANKING` (skipping
`TOURNAMENT_LIST` entirely in that instance's own nav graph):

```
Task stack (bottom → top):
MainActivity#1 [TOURNAMENT_LIST]      <- untouched, original instance
TournamentDetailActivity (RN)
MainActivity#2 [HISTORY]              <- new instance, starts here directly
```

- Back from `MainActivity#2`'s `HISTORY`: Compose Navigation's back handling only intercepts
  back-press when there's more than one entry in that NavHost's back stack. With a single
  entry, it doesn't consume the event, so it falls through to the platform default — finishing
  `MainActivity#2` — which reveals `TournamentDetailActivity` (RN Detail) underneath. ✓
- Back from RN Detail (`TournamentDetailActivity.finish()`, unchanged from Lab 01): reveals the
  **original, untouched** `MainActivity#1` showing `TOURNAMENT_LIST`. ✓
- Normal in-app navigation (tapping "Histórico"/"Ranking" from the native list) is completely
  unaffected — same `MainActivity` instance, same `NavHostController`, pushes normally, back
  pops normally.

This avoids any custom `BackHandler` overrides or `popUpTo` tricks — it's the default Android
task-stack + default Compose Navigation back behavior, just parameterizing `MainActivity`'s
start destination per-launch.

Mash-protection: a simple timestamp debounce (~800ms) inside the native module before calling
`startActivity`, so rapid repeated taps from RN don't stack multiple `MainActivity#2` instances.

## Files to add

- `src/specs/NativeNavigationBridge.ts` — TurboModule spec (`openHistory(): void`,
  `openRanking(): void`), New Architecture only (`TurboModuleRegistry.getEnforcing`).
- `app/src/main/java/com/ciandt/championships/navigationbridge/NavigationBridgeModule.kt` —
  implements the generated `NativeNavigationBridgeSpec`; each method builds an
  `Intent(activity, MainActivity::class.java)` with `Routes.EXTRA_START_DESTINATION` and starts
  it on the current activity (via `reactApplicationContext.currentActivity`, wrapped in
  `runOnUiThread`), with the debounce guard.
- `app/src/main/java/com/ciandt/championships/navigationbridge/NavigationBridgePackage.kt` —
  `BaseReactPackage` exposing the module as a TurboModule (`isTurboModule = true`).
- `src/utils/tournamentBracket.js` — pure function `generateBracket(formatKey, participantNames)`:
  - `SINGLE_ELIMINATION`: real round-1 pairings from the participant list (padded with "Bye" if
    count isn't a power of 2), later rounds shown as "Winner of R{n}M{i}" placeholders (no real
    results exist yet — full result tracking is out of scope, Lab 03 replaces this generator
    with a native TurboModule anyway).
  - `SWISS`: a single round of sequential pairings (Swiss round 2+ depends on results we don't
    have).
  - `ROUND_ROBIN` / unrecognized: returns `null` (screen renders no bracket section, just the
    participant list, matching the doc's "if single-elimination or swiss" condition).

## Files to change

- `app/src/main/java/com/ciandt/championships/ui/navigation/Routes.kt` — add
  `const val EXTRA_START_DESTINATION = "startDestination"`.
- `app/src/main/java/com/ciandt/championships/MainActivity.kt` — read
  `intent.getStringExtra(Routes.EXTRA_START_DESTINATION) ?: Routes.TOURNAMENT_LIST` in
  `onCreate`, thread it into `CiandtChampionshipsApp(startDestination = ...)`, use it as the
  `NavHost`'s `startDestination`.
- `app/src/main/java/com/ciandt/championships/TournamentDetailActivity.kt` — add
  `EXTRA_FORMAT_KEY` (the raw `tournament.format.name` enum, e.g. `SINGLE_ELIMINATION`) alongside
  the existing localized `EXTRA_FORMAT` label, forwarded to JS as `formatKey` so the JS side
  doesn't need to pattern-match localized PT-BR strings to decide bracket rendering.
- `app/src/main/java/com/ciandt/championships/ChampionshipsApplication.kt` — append
  `NavigationBridgePackage()` to the list returned by `getPackages()` (it's a local module, not
  autolinked).
- `package.json` — add:
  ```json
  "codegenConfig": {
    "name": "NavigationBridgeSpec",
    "type": "modules",
    "jsSrcsDir": "src/specs",
    "android": { "javaPackageName": "com.ciandt.championships.navigationbridge" }
  }
  ```
- `src/screens/TournamentDetailScreen.js` — keep the purple banner and existing detail rows;
  add a synthesized participant list (`Player 1..N`, since the native `Tournament` model only
  carries `participantCount`, no real names — consistent with the doc's note that bracket
  generation is a placeholder Lab 03 later replaces natively); render the bracket via
  `generateBracket` when non-null; add two buttons ("View History" / "View Ranking") calling
  `NativeNavigationBridge.openHistory()` / `openRanking()`.

## Verification plan (manual, same adb-driven approach as Lab 01)

1. Build (`./gradlew assembleDebug`), confirm codegen produces `NativeNavigationBridgeSpec` in
   the right package (check generated sources under `app/build/generated/source/codegen`).
2. Cold start → tap a tournament card → confirm participant list + bracket render correctly for
   a single-elimination tournament (Sinuca Q3 2026, 8 participants) and a swiss one (FIFA Office
   Cup, 16 participants); confirm round-robin (Futebol de Botão H1) shows no bracket section.
3. Tap "View History" from the RN Detail screen → confirm native History screen opens, shows
   real data, back button returns to RN Detail (not the tournament list).
4. Repeat for "View Ranking".
5. From RN Detail (after a History/Ranking round-trip), back again → confirm it reaches the
   original native tournament list.
6. Rapid-tap "View History" a few times → confirm only one History screen is pushed (no stack
   of duplicates when repeatedly backing out).
7. Commit to `lab-02-solution` branch (new branch off `lab-01-solution`... actually off `main`,
   matching how `lab-01-solution` was branched) and push.

## Open items to confirm once mid-implementation (not blocking the plan)

- Exact codegen output class/package naming will be double-checked against the real generated
  file once `assembleDebug` runs (schema is well-documented but I'll verify rather than assume).
