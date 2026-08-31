package com.ciandt.championships.tournamentbridge

import com.facebook.fbreact.specs.NativeTournamentBridgeSpec
import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

// Local app-level TurboModule, not autolinked — registered manually in
// ChampionshipsApplication.getPackages().
class TournamentBridgePackage : BaseReactPackage() {

    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
        return if (name == NativeTournamentBridgeSpec.NAME) TournamentBridgeModule(reactContext) else null
    }

    override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
        return ReactModuleInfoProvider {
            mapOf(
                NativeTournamentBridgeSpec.NAME to ReactModuleInfo(
                    NativeTournamentBridgeSpec.NAME,
                    NativeTournamentBridgeSpec.NAME,
                    false, // canOverrideExistingModule
                    false, // needsEagerInit
                    false, // isCxxModule
                    true, // isTurboModule
                )
            )
        }
    }
}
