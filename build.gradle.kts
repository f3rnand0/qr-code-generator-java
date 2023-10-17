plugins {
	java
	kotlin("jvm") version "1.9.20-RC"
}

group = "fgu.code.challenges"
version = "0.0.1-SNAPSHOT"

java {
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}

repositories {
	mavenCentral()
	google()
}
kotlin {
	jvmToolchain(17)
}