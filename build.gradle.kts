plugins {
    id("java")
}

group = "com.itacademy.sigma_team"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.mongodb:mongodb-driver-sync:4.4.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("mysql:mysql-connector-java:8.0.26")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("com.h2database:h2:2.2.224")
    implementation("org.slf4j:slf4j-api:2.0.13")
}


tasks.test {
    useJUnitPlatform()
}