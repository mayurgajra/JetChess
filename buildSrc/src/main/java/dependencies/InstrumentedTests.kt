package dependencies

object InstrumentedTests {
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val idlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espressoIdlingResource}"

    const val hiltAndroidTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val hiltAndroidTestCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
}