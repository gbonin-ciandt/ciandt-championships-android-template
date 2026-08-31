package com.ciandt.championships

import android.content.Context
import android.content.Intent
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

// Hosts the "CreateTournament" RN surface (Lab 03). No launch args: it's a blank form, and
// the system back button finishing this Activity is enough to return to the native list.
class CreateTournamentActivity : ReactActivity() {

    override fun getMainComponentName(): String = "CreateTournament"

    override fun createReactActivityDelegate(): ReactActivityDelegate =
        DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, CreateTournamentActivity::class.java)
    }
}
