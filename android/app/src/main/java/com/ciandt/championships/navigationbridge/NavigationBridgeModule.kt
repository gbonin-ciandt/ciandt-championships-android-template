package com.ciandt.championships.navigationbridge

import android.content.Intent
import com.ciandt.championships.MainActivity
import com.ciandt.championships.ui.navigation.Routes
import com.facebook.fbreact.specs.NativeNavigationBridgeSpec
import com.facebook.react.bridge.ReactApplicationContext

// Lab 02: lets the RN Tournament Detail screen push forward onto the native NavHost
// (History / Ranking) by starting a second, independent MainActivity instance parameterized
// to start directly at that destination — see .claude/plans/lab-02-brownfield-navigation.md
// for why this makes the back button return to RN Detail instead of the native list.
class NavigationBridgeModule(reactContext: ReactApplicationContext) :
    NativeNavigationBridgeSpec(reactContext) {

    // Guards against rapid repeated taps stacking multiple MainActivity instances.
    private var lastLaunchAtMs = 0L
    private val debounceMs = 800L

    override fun openHistory() {
        launchMainActivityAt(Routes.HISTORY)
    }

    override fun openRanking() {
        launchMainActivityAt(Routes.RANKING)
    }

    private fun launchMainActivityAt(startDestination: String) {
        val now = System.currentTimeMillis()
        if (now - lastLaunchAtMs < debounceMs) return
        lastLaunchAtMs = now

        val activity = reactApplicationContext.currentActivity ?: return
        activity.runOnUiThread {
            val intent = Intent(activity, MainActivity::class.java).apply {
                putExtra(Routes.EXTRA_START_DESTINATION, startDestination)
            }
            activity.startActivity(intent)
        }
    }
}
