plugins {
    id("java")
}

group = "com.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.apache.openwebbeans/openwebbeans-impl
    implementation("org.apache.openwebbeans:openwebbeans-impl:4.0.2")

// https://mvnrepository.com/artifact/org.eclipse.persistence/eclipselink
    implementation("org.eclipse.persistence:eclipselink:4.0.3")

    // https://mvnrepository.com/artifact/io.helidon.webserver/helidon-webserver
    implementation("io.helidon.webserver:helidon-webserver:4.0.9")

    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")

    implementation("jakarta.transaction:jakarta.transaction-api:2.0.0")

    implementation("com.google.code.gson:gson:2.11.0")


}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}