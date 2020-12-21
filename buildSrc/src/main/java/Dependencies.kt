import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {
    const val kotlin = "1.4.21"
    const val compose = "1.0.0-alpha09"
    const val accompanist = "0.3.3.1"

    const val junit = "4.13"

    const val debugDrawer = "0.1.0-beta-04"

    const val viewModel = "2.2.0"
    const val koin = "2.2.1"
    const val ktor = "1.4.0"
    const val okHttp = "4.9.0"

    object AndroidX {
        const val core = "1.3.2"
        const val lifecycle = "2.3.0-rc01"
    }

}

object AndroidSdk {
    const val min = 23
    const val compile = 30
    const val target = compile

    const val androidGradlePlugin = "7.0.0-alpha03"
}

interface Dependency {
    val version: String
    val all: List<String>
}

interface AppDependency: Dependency
interface TestDependency: Dependency

interface GroupDependency {
    val dependencies: List<AppDependency>
}

fun DependencyHandler.provide(appDependency: AppDependency) {
    appDependency.all
        .map { name -> "$name:${appDependency.version}" }
        .forEach { dependency -> add("implementation", dependency) }
}

fun DependencyHandler.provideTest(testDependency: TestDependency) {
    testDependency.all
        .map { name -> "$name:${testDependency.version}" }
        .forEach { dependency -> add("testImplementation", dependency) }
}

fun DependencyHandler.provide(groupDependency: GroupDependency) {
    groupDependency.dependencies.forEach { appDependency -> provide(appDependency) }
}

object Koin : AppDependency {
    override val version: String = Versions.koin
    override val all: List<String> = listOf(
        "org.koin:koin-android",
        "org.koin:koin-androidx-scope",
        "org.koin:koin-androidx-viewmodel",
        "org.koin:koin-androidx-fragment",
        "org.koin:koin-androidx-compose",
        "org.koin:koin-androidx-ext"
    )
}

object Compose : AppDependency {
    override val version: String = Versions.compose
    override val all: List<String> = listOf(
        "androidx.compose.ui:ui",
        "androidx.ui:ui-tooling",
        "androidx.compose.foundation:foundation",
        "androidx.compose.material:material",
        "androidx.compose.material:material-icons-core",
        "androidx.compose.material:material-icons-extended"
    )
}

object AndroidX : GroupDependency {
    override val dependencies: List<AppDependency> = listOf(
        LifeCycle
    )

    object LifeCycle : AppDependency {
        override val version: String = Versions.AndroidX.lifecycle
        override val all: List<String> = listOf(
            "androidx.lifecycle:lifecycle-extensions",
            "androidx.lifecycle:lifecycle-viewmodel-ktx",
            "androidx.lifecycle:lifecycle-runtime-ktx"
        )
    }

    object Core : AppDependency {
        override val version: String = Versions.AndroidX.core
        override val all: List<String> = listOf(
            "androidx.core:core-ktx"
        )
    }
}

object jUnit : TestDependency {
    override val version: String = Versions.junit
    override val all: List<String> = listOf("junit:junit")

}