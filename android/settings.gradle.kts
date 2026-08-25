pluginManagement {
    includeBuild("node_modules/@react-native/gradle-plugin")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("com.facebook.react.settings")
}
extensions.configure<com.facebook.react.ReactSettingsExtension> {
    // This settings.gradle.kts lives at the project root (no separate `android/` subfolder,
    // since RN was embedded into the pre-existing native project), so the working directory
    // for the autolinking command must be "." rather than the library default of "../".
    autolinkLibrariesFromCommand(workingDirectory = settings.layout.rootDirectory.asFile)
}
dependencyResolutionManagement {
    // PREFER_SETTINGS (not FAIL_ON_PROJECT_REPOS): the com.facebook.react plugin adds its own
    // project-level repository for JSC/Hermes artifacts, which FAIL_ON_PROJECT_REPOS rejects.
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ciandt-championships"
include(":app")
includeBuild("node_modules/@react-native/gradle-plugin")
