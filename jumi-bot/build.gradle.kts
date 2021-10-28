dependencies {
    implementation(Dependencies.kotlinStd)
    implementation(Dependencies.kotlinCoroutines)
    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.kotlinxDateTime)
    implementation(Dependencies.kLogging)
    implementation(Dependencies.slf4jSimple)
    implementation(Dependencies.ktorClientCore)
    implementation(project(":jumi-riotapi"))
}