plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":account"))
    implementation(project(":category"))
    implementation(project(":user"))          // для AppUserDetails (через @AuthenticationPrincipal)

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // ⬇️ для @AuthenticationPrincipal, UserDetails
    implementation("org.springframework.security:spring-security-core")

    // ⬇️ если используете Specification (JpaSpecificationExecutor)
//     implementation("org.springframework.data:spring-data-jpa")   // уже входит в starter-data-jpa
}