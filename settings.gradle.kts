pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        // App (project-level) version catalog
        create("gallery") {
            from(files("gradle/gallery.versions.toml"))
        }
    }
}

rootProject.name = "MyGalleryApp"
include(":app")
