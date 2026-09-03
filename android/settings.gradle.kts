pluginManagement {
    includeBuild("../node_modules/@react-native/gradle-plugin")
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
    // This settings.gradle.kts lives inside the existing `android/` subfolder (RN was embedded
    // into the pre-existing native project, which keeps its android/ wrapper), so the JS project
    // root (where package.json lives) is one level up from here, not the settings directory itself.
    autolinkLibrariesFromCommand(workingDirectory = settings.layout.rootDirectory.dir("..").asFile)
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
includeBuild("../node_modules/@react-native/gradle-plugin")
