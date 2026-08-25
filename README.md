# CI&T Championships — Android Template

Native starting point for **Lab 01 — Brownfield Bootstrap** of the RN Advanced Lab
(see `trilha-react-native`'s `/lab` page and
`_course-refs/rn-advanced-lab/APP-SPEC-ci-t-championships.md` for the full context).

This is a plain Kotlin + Jetpack Compose Android app — **no React Native yet**. It shows
a tournament list screen backed by in-memory mock data (soccer, pool, Mortal Kombat,
FIFA). The lab task is to embed a React Native module into this project without
breaking the existing native screen.

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
  MainActivity.kt
  data/                  Tournament, TournamentFormat, TournamentStatus, TournamentRepository (mock)
  ui/theme/              Compose Material 3 theme (cyan/purple, matches the course's Lab identity)
  ui/tournamentlist/     TournamentListScreen + ViewModel — the screen Lab 01 embeds RN alongside
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
  (`#14532D`), label `"NATIVE SCREEN"` — already wired into `TournamentListScreen`
- **React Native screens** (added in Lab 01+): purple, `OriginBadgeColors.ReactNative`
  (`#4C1D95`), label `"REACT NATIVE SCREEN"` — the RN side should render an equivalent
  banner (plain `View`/`Text` with the same color) at the top of every RN screen for
  visual parity with the native one

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

| Lab | What gets added |
|---|---|
| 01 — Brownfield Bootstrap | React Native embedded alongside this native screen |
| 02 — Brownfield Navigation | Native list → RN "create tournament" → back to native |
| 03 — Native Library Bridge | TurboModule wrapping a native bracket/Swiss-pairing generator |
| 04 — UI Thread vs JS Thread | Live standings screen, perf investigation |
| 05 — Godot Integration (optional) | Victory-celebration mini-game |

This repo is meant to become a **GitHub template repository** so each student can
"Use this template" to get their own copy to work from.
