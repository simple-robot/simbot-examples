import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.7.20"
	kotlin("plugin.spring") version "1.7.20"
}

group = "simbot.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val simbotVersion = "3.0.0-M5"
val simbotMiraiVersion = "3.0.0.0-beta.6"

dependencies {
	// simbot
	implementation("love.forte.simbot.boot:simboot-core-spring-boot-starter:$simbotVersion")
	implementation("love.forte.simbot.component:simbot-component-mirai-core:$simbotMiraiVersion")

	// implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	runtimeOnly("io.r2dbc:r2dbc-h2")

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
