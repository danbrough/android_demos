buildscript {


  dependencies {
    //classpath("com.android.tools.build:gradle:4.2.0-alpha09")
    classpath("com.android.tools.build:gradle:4.1.0-rc02")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    classpath(AndroidX.navigation.safeArgsGradlePlugin)


  }

  repositories {
    mavenLocal()
    google()
    mavenCentral()
    jcenter()
  }

}


apply("project.gradle.kts")

subprojects {

  repositories {
    mavenLocal()
    google()
    jcenter()
    //maven("https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
    maven("https://jitpack.io")
  }
  /*configurations.all {
    if (name.toLowerCase().contains("test")) {
      resolutionStrategy.dependencySubstitution {
        substitute(module(Libs.slf4j)).with(module(Libs.logback_classic))
      }
    }
  }*/
}



