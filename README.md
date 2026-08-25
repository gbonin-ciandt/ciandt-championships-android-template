# CI&T Championships — Android Template

Native starting point for **Lab 01 — Brownfield Bootstrap** of the RN Advanced Lab
(see `trilha-react-native`'s `/lab` page and
`_course-refs/rn-advanced-lab/APP-SPEC-ci-t-championships.md` for the full context).

This is a plain Kotlin + Jetpack Compose Android app — **no React Native yet**. It shows
a tournament list screen backed by in-memory mock data (soccer, pool, Mortal Kombat,
FIFA), plus two more native screens (**History** and **Global Ranking**) already wired
into a Jetpack Navigation Compose graph. The lab task is to embed React Native into this
project and grow it into new screens without breaking the existing native ones.

This template intentionally ships with **more native screens than Lab 01 touches**.
The premise of the brownfield trail is that the native app already exists in production
and React Native is added feature by feature into it — so History and Global Ranking
are pre-built native destinations the RN screens students build in later labs will
navigate *into*, exercising the RN → native direction of interop (not just native → RN).

## Stack

- Kotlin 2.2.0, Jetpack Compose (Material 3)
- AGP 9.0.0, Gradle 9.1.0
- `compileSdk`/`buildToolsVersion` 37, `targetSdk` 36 (Android 16), `minSdk` 24
  - `compileSdk` is intentionally one ahead of `targetSdk`: React Native 0.87 (added in Lab 01) requires `compileSdk`/`buildTools` 37, while the app still targets the Android 16 (API 36) runtime
- Gradle version catalog (`gradle/libs.versions.toml`)
- `gradle.properties` opts out of AGP 9's built-in Kotlin and new DSL (`android.builtInKotlin=false`, `android.newDsl=false`) — matches the flags React Native 0.87+ expects when it's added in Lab 01, so upgrading later shouldn't require touching these

## Structure

```
app/src/main/java/com/ciandt/championships/
  MainActivity.kt        NavHost wiring (tournament_list / history / ranking routes)
  data/                  Tournament, TournamentFormat, TournamentStatus, TournamentRepository (mock)
                         RankingEntry, RankingRepository (mock)
  ui/theme/              Compose Material 3 theme (cyan/purple, matches the course's Lab identity)
  ui/navigation/         Routes — route constants shared by MainActivity's NavHost
  ui/tournamentlist/     TournamentListScreen + ViewModel — the screen Lab 01 embeds RN alongside
  ui/history/            HistoryScreen + ViewModel — native, pre-built, lists FINISHED tournaments
  ui/ranking/            RankingScreen + ViewModel — native, pre-built, global points ranking
```

## Opening in Android Studio

The Gradle wrapper JAR binary isn't committed to this template (kept out of git history
on purpose). On first open, Android Studio will detect the missing wrapper and offer to
generate/use one — accept that prompt, or run `gradle wrapper --gradle-version 9.1.0` once
if you have a local Gradle install. `gradle/wrapper/gradle-wrapper.properties` already
pins the version so the regenerated wrapper matches.

## Native vs React Native screen labeling

Every screen carries a small colored banner at the top identifying its origin —
`ui/common/OriginBadge.kt` — so it's obvious during brownfield debugging which
screens are native and which are React Native:

- **Native (Kotlin/Compose) screens**: green banner, `OriginBadgeColors.Native`
  (`#14532D`), label `"NATIVE SCREEN"` — already wired into `TournamentListScreen`,
  `HistoryScreen` and `RankingScreen`
- **React Native screens** (added in Lab 01+): purple, `OriginBadgeColors.ReactNative`
  (`#4C1D95`), label `"REACT NATIVE SCREEN"` — the RN side should render an equivalent
  banner (plain `View`/`Text` with the same color) at the top of every RN screen for
  visual parity with the native one

## Navigation

`MainActivity.kt` hosts a Jetpack Navigation Compose graph (`ui/navigation/Routes.kt`)
with three destinations today: `tournament_list` (start), `history`, `ranking`. The
tournament list screen has "Histórico"/"Ranking" text buttons that navigate to the two
native screens — this is the graph later labs extend: Lab 02's RN Tournament Detail
screen is added as a new destination reachable by tapping a tournament card, and it in
turn can navigate forward into the existing native `history`/`ranking` destinations.

## React Native 0.87 readiness (for Lab 01)

This template's toolchain is pre-aligned to React Native 0.87 (the current stable release)
so that adding RN in Lab 01 doesn't also require a toolchain migration. Relevant RN 0.87
minimum requirements/breaking changes to keep in mind when building that lab:

- **Node.js >= 22.13.0** required by the RN CLI/Metro tooling (not an Android Gradle
  setting, but needed once `package.json`/Metro are introduced)
- **Kotlin 2.0+ minimum**, RN 0.87 itself bundles **Kotlin 2.2.0** — this template is
  already on 2.2.0
- **`minCompileSdk` 34** for any native library the app depends on
- **`compileSdk`/`buildToolsVersion` 37** — already set here, with `targetSdk` kept at
  36 (Android 16) since target and compile SDK don't need to match
- **AGP 9** support is new in 0.87; this template opts out of AGP 9's built-in Kotlin and
  new DSL via `gradle.properties` (`android.builtInKotlin=false`, `android.newDsl=false`),
  matching what the RN upgrade helper recommends
- **Strict TypeScript API is the default** — deep imports into `Libraries/` won't work
  without opting back in via `react-native-legacy-deep-imports`; relevant once the RN side
  of Lab 01 is scaffolded
- **`InteractionManager` is removed** — use `requestIdleCallback` instead (relevant for
  Lab 04, UI thread vs JS thread work)
- `useTurboModules` feature flag removed (TurboModules are always on) — relevant for
  Lab 03's native library bridge, no flag needed to enable them

## Roadmap (future labs, not yet in this repo)

| Lab | What gets added | Criteria |
|---|---|---|
| 01 — Brownfield Bootstrap | React Native embedded alongside the native tournament list | Tapping a tournament card opens an RN screen that receives the tournament via props, renders the purple "REACT NATIVE SCREEN" badge, and can navigate back to the native list |
| 02 — Brownfield Navigation | Real RN **Tournament Detail** screen (bracket/table rendered per `TournamentFormat`) | From the RN detail screen, the student wires forward navigation into the pre-built native `history` and `ranking` destinations — exercising RN → native interop, not just native → RN |
| 03 — Native Library Bridge | RN **Create Tournament** form calling a native TurboModule | The TurboModule generates real bracket/Swiss pairings (replacing `TournamentRepository`'s hardcoded mocks) and returns them to the RN form |
| 04 — UI Thread vs JS Thread | **Match score entry** RN screen, reusing the Lab 02 detail screen | Screen ships with a deliberate JS-thread perf problem (janky input/list) the student must diagnose and fix |
| 05 — Godot Integration (optional) | Victory-celebration mini-game | Triggered after a match/tournament completes; optional stretch lab |

History and Global Ranking are **not** lab deliverables — they exist in this repo today
as native screens so labs 02+ have real, pre-existing native destinations to bridge into.

## For students

This is a **GitHub template repository** — you don't fork it, and you don't grab a new
copy for every lab.

1. Click **Use this template** (top of this repo's GitHub page) to create your own copy
   under your GitHub account.
2. Clone your copy and open it in Android Studio. Lab 01 starts here, with this native
   tournament list already working.
3. Work through Labs 01–05 directly inside your own copy, committing as you complete
   each one — your commit history becomes your progress log. You never re-template.
4. Stuck on a lab? This repo (the original template, not your copy) keeps reference
   solution branches per lab (`lab-02-solution`, `lab-03-solution`, ...) as an answer
   key. They are **not** copied into your fork automatically — check them here if
   you need to compare.

See also the `/lab` page on the course site for the full lab descriptions and prerequisites.
