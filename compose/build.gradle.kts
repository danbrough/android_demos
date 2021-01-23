// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

  repositories {
    google()
    jcenter()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.0.0-alpha04")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven("https://jitpack.io")
  }
}


