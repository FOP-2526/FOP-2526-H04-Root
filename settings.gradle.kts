dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://central.sonatype.com/repository/maven-snapshots/")
        maven("https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "H04-Root"
