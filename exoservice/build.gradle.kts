buildscript {

  dependencies {
    classpath(Libs.com_android_tools_build_gradle)
    classpath(Libs.kotlin_gradle_plugin)
    classpath(Libs.navigation_safe_args_gradle_plugin)
  }

  repositories {
    google()
    jcenter()
    //    maven { url 'https://dl.bintray.com/kotlin/kotlin-eap' }
    maven {
      setUrl("https://dl.bintray.com/kotlin/kotlin-eap")
    }
  }
}


plugins {
  buildSrcVersions
}

allprojects {
  repositories {
    mavenLocal()
    google()
    jcenter()

    maven {
      setUrl("https://jitpack.io")
    }
    maven {
      setUrl("https://dl.bintray.com/kotlin/kotlin-eap")
    }
  }
  configurations.all {
    if (name.toLowerCase().contains("test")) {
      resolutionStrategy.dependencySubstitution {
        substitute(module(Libs.slf4j)).with(module(Libs.logback_classic))
      }
    }
  }
}

tasks.register("projectVersion") {
  this.description = "Prints Project.getVersionName()"
  doLast {
    println(ProjectVersions.getVersionName())
  }
}

tasks.register("nextProjectVersion") {
  this.description = "Prints Project.getIncrementedVersionName()"
  doLast {
    println(ProjectVersions.getIncrementedVersionName())
  }
}
