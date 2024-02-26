plugins {
	java
	kotlin("jvm") version "1.9.20-RC"
}

group = "fgu.code.challenges"
version = "1.0.0"
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

tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "fgu.word.count.Application"
	}

	from(sourceSets.main.get().output)
}