plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  kotlin("android.extensions")
  id("androidx.navigation.safeargs.kotlin")
}



android {


  compileSdkVersion(ProjectVersions.SDK_VERSION)
  ndkVersion = "21.3.6528147"

  defaultConfig {
    //buildToolsVersion("30.0.2")

    minSdkVersion(ProjectVersions.MIN_SDK_VERSION)
    targetSdkVersion(ProjectVersions.SDK_VERSION)
    versionCode = ProjectVersions.BUILD_VERSION
    versionName = ProjectVersions.VERSION_NAME
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")

    vectorDrawables {
      useSupportLibrary = false
    }
  }

  lintOptions {
    isAbortOnError = false
  }


  compileOptions {
    sourceCompatibility = ProjectVersions.JAVA_VERSION
    targetCompatibility = ProjectVersions.JAVA_VERSION
  }


  kotlinOptions {
    jvmTarget = "1.8"
  }

  kapt {
    correctErrorTypes = true
  }

}

tasks.withType<Test> {
  useJUnit()

  testLogging {
    events("standardOut", "started", "passed", "skipped", "failed")
    showStandardStreams = true
    outputs.upToDateWhen {
      false
    }
  }
}

dependencies {

  implementation(project(":media"))
  implementation(AndroidX.lifecycle.runtimeKtx)
  implementation(AndroidX.lifecycle.liveDataKtx)
  implementation(AndroidX.lifecycle.viewModelKtx)
  implementation(AndroidX.coreKtx)
  implementation(Kotlin.stdlib.jdk8)

  implementation(AndroidX.navigation.fragmentKtx)
  implementation(AndroidX.navigation.uiKtx)
  implementation(AndroidX.constraintLayout)
  implementation(AndroidX.preferenceKtx)
  implementation(AndroidX.media2.common)
  implementation(AndroidX.concurrent.futures)
  implementation(Google.android.material)

  //implementation("androidx.media2:media2-exoplayer:$media_version")
  //implementation("androidx.media2:media2-player:$media_version")
  implementation("com.google.guava:guava:_")


  //implementation(Libs.slf4j_android)
  implementation("org.slf4j:slf4j-api:_")
  //implementation(Libs.slf4j)
  implementation("com.github.danbrough.androidutils:menu:_")
  implementation("com.github.danbrough.androidutils:slf4j:_")
  implementation("com.github.danbrough.androidutils:misc:_")

/*  implementation("com.mikepenz:iconics-core:5.0.3")
  implementation("com.mikepenz:iconics-views:5.0.3")
  implementation ("com.mikepenz:fontawesome-typeface:5.9.0.0-kotlin")
  implementation("com.mikepenz:community-material-typeface:5.3.45.1-kotlin")*/



  implementation(Square.okHttp3.okHttp)


//  implementation(project(":menu"))

  androidTestImplementation(Testing.junit4)

  androidTestImplementation(AndroidX.test.core)
  androidTestImplementation(AndroidX.test.runner)
  androidTestImplementation(AndroidX.test.rules)

  androidTestImplementation("com.github.danbrough.androidutils:slf4j:_")


/*  testImplementation("ch.qos.logback:logback-classic:_")
  testImplementation("ch.qos.logback:logback-core:_")

  kapt("com.google.dagger:hilt-android-compiler:_")
  implementation("com.google.dagger:hilt-android:_")

  // For instrumentation tests
  androidTestImplementation("com.google.dagger:hilt-android-testing:_")

  // For local unit tests
  testImplementation("com.google.dagger:hilt-android-testing:_")*/


}
