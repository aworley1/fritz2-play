plugins {
    kotlin("multiplatform") version "1.4.0"
    id("dev.fritz2.fritz2-gradle") version "0.7.1"
}

repositories {
    jcenter()
}

kotlin {
    kotlin {
        jvm()
        js().browser()

        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(kotlin("stdlib"))
                }
            }
            val jvmMain by getting {
                dependencies {
                }
            }
            val jsMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
                }
            }
        }
    }
}