import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.gradleup.shadow")
}

base {
    archivesName.set("${rootProject.property("archives_base_name")}-bukkit")
}

val shadeThisThing: Configuration by configurations.creating {
    isCanBeConsumed = true
    isTransitive = true
}

// TODO migrate to only including sources sets for compile, test and javadoc tasks
tasks.withType<JavaCompile>().configureEach {
    source(project(":common").sourceSets.main.get().allSource)
}

tasks.withType<Javadoc>().configureEach {
    source(project(":common").sourceSets.main.get().allJava)
}

tasks.named<JavaCompile>("compileTestJava") {
    exclude("**/*")
}


dependencies {
    implementation(project(":common"))

    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.geysermc.floodgate:api:2.0-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    shadeThisThing(implementation("org.kohsuke:github-api:1.326") {
        exclude(group = "commons-io", module = "commons-io")
        exclude(group = "org.apache.commons", module = "commons-lang3")
    })


    shadeThisThing(implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")!!)
    shadeThisThing(implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")!!)

    shadeThisThing(implementation("com.github.retrooper:packetevents-spigot:2.5.0")!!)
    shadeThisThing(implementation("dev.jorel:commandapi-bukkit-shade:9.5.3")!!)
    shadeThisThing(implementation("net.kyori:adventure-platform-bukkit:4.3.4")!!)
    implementation("org.incendo:cloud-paper:2.0.0-beta.10")
}

tasks.withType<ShadowJar> {
    configurations = listOf(shadeThisThing)
    isEnableRelocation = true
    relocationPrefix = "${project.property("maven_group")}.${project.property("archives_base_name")}.shaded"
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    from(project(":common").sourceSets.main.get().resources)
    inputs.property("version", project.version)
    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(
            "version" to project.version,
        )
    }
}