plugins {

    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("java")
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("groovy")
    id("me.champeau.jmh") version "0.6.6"

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
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Groovy
    implementation("org.codehaus.groovy:groovy-all:3.0.18")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Mock 数据
    implementation("com.github.javafaker:javafaker:1.0.2")

    // Test dependencies
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.codehaus.groovy:groovy-all:3.0.18")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // JMH 依赖
    jmh("org.openjdk.jmh:jmh-core:1.32")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.32")

    // Mock数据相关依赖
    implementation("com.github.javafaker:javafaker:1.0.2")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


jmh {
    duplicateClassesStrategy.set(DuplicatesStrategy.EXCLUDE)
    failOnError.set(true)
    benchmarkMode.set(listOf("Throughput"))
    timeUnit.set("ms")
    fork.set(1)
    warmupIterations.set(2)
    iterations.set(5)
    warmup.set("1s")
    timeOnIteration.set("1s")
    includes.set(listOf(".*")) // 只基准测试匹配的类，可修改为特定类名
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