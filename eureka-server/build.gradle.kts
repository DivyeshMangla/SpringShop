dependencies {
    // This is the actual server dependency
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")

    // Exclude the common dependencies that don't apply to the server
    configurations.implementation.get().exclude(group = "org.springframework.boot", module = "spring-boot-starter-web")
    configurations.implementation.get().exclude(group = "org.springframework.cloud", module = "spring-cloud-starter-netflix-eureka-client")
}