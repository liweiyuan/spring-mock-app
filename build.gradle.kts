plugins {

    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("java")
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("groovy")

}

// 添加到build.gradle.kts文件中
configurations {
    all {
        // 解决可能的依赖冲突
        resolutionStrategy.force("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    }
}

group = "spring.app"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.codehaus.groovy:groovy-all:3.0.18")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Mock数据相关依赖
    implementation("com.github.javafaker:javafaker:1.0.2")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

sourceSets{
    main{
        java{
            srcDir("src/main/java")
        }
        kotlin{
            srcDir("src/main/kotlin")
        }
        groovy{
            srcDir("src/main/groovy")
        }
    }
}

// 设置编译顺序
tasks.named("compileJava") {
    dependsOn("compileKotlin")
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}