import deps.DependencyConfig
import net.neoforged.nfrtgradle.CreateMinecraftArtifacts

plugins {
    id("dev.isxander.modstitch.base") version "0.8.4"
    id("dev.isxander.modstitch.publishing") version "0.8.4"
}
val mod_version = property("mod_version") as String
val mod_id = property("mod_id") as String
val minecraft = property("deps.minecraft") as String
val libVersion = property("deps.lib_version") as String
val minecraftVersionSplit = minecraft.split('.')


var loader: String = name.split("-")[1]
fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String)?.let{
            consumer.invoke(it)
        }
}
fun propLib(consumer: (prop: String) -> Unit){
    prop("deps.lib_version", consumer)
}

val modv = property("mod_version") as String


modstitch {
    minecraftVersion = minecraft
    javaVersion = when (minecraft){
        "1.20.1" -> 17
        else -> 21
    }

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") {
            if (minecraft == "1.21.1") minecraftVersion.set("1.21")
            mappingsVersion = it
        }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = mod_id
        modName = property("mod_name") as String
        modVersion = property("mod_version") as String
        modGroup = property("mod_group_id") as String
        modAuthor = property("mod_authors") as String
        modDescription =
            property("mod_description") as String
        modLicense = "MIT"

        fun <K : Any, V : Any> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }

        replacementProperties.populate {
            put("mod_issue_tracker", property("mod_issue") as String)
            put("pack_format", when (property("deps.minecraft")) {
                "1.20.1" -> 15
                "1.21.1" -> 34
                "1.21.4" -> 46
                "1.21.8" -> 64
                "1.21.10" -> 69
                "1.21.11" -> 70.0
                else -> throw IllegalArgumentException("Please store the resource pack version for ${property("deps.minecraft")} in build.gradle.kts! https://minecraft.wiki/w/Pack_format")
            }.toString())

            prop("deps.fzzy_config_version"){
                put("fzzy_config_version", it)
            }

            put("lib_version", libVersion)
            put("common_networking_version", property("deps.common_networking") as String)

            put("target_minecraft", minecraft)
            put(
                "target_loader", when (loader) {
                    "neoforge" -> property("deps.neoforge") as String
                    else -> ""
                }
            )
            put("loader", loader)
            put(
                "target_fabricloader", when (loader) {
                    "fabric" -> "0.16.10"
                    else -> ""
                }
            )

            put("target_forge", findProperty("deps.forge") as? String ?: "")

        }
    }

    loom {

        fabricLoaderVersion = "0.16.10"

        // Configure loom like normal in this block.
        configureLoom {
            runConfigs.all {
                ideConfigGenerated(false)
            }
            accessWidenerPath.set(file("../../src/main/templates/${mod_id}.accesswidener"))
        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        prop("deps.forge") { forgeVersion = it }
        prop("deps.neoforge") { neoForgeVersion = it }
        prop("deps.mcp") { mcpVersion = it }

        configureNeoForge {

            runs {
                configureEach {
                    systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
                    disableIdeRun()
                    jvmArguments.add("-XX:+AllowEnhancedClassRedefinition")
                }
                register("client") {
                    client()
                }
                if(minecraftVersionSplit[2].toInt() >= 4 ){
                    register("clientData") {
                        clientData()
                        programArguments.addAll("--mod", mod_id, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
                    }

                    register("serverData") {
                        serverData()
                        programArguments.addAll("--mod", mod_id, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
                    }
                } else {
                    register("data") {
                        data()
                        programArguments.addAll("--mod", mod_id, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
                    }
                }

                register("server") {
                    server()
                }
                afterEvaluate{
                    this@runs.names.forEach {
                        val capitalizedName = it.replaceFirstChar(Char::uppercaseChar)
                        project.tasks.named<JavaExec>("run$capitalizedName") {
                            val toolchain = project.extensions.getByType<JavaToolchainService>()
                            javaLauncher.set(
                                toolchain.launcherFor {
                                    languageVersion.set(JavaLanguageVersion.of(project.modstitch.javaVersion.get()))
                                    vendor.set(JvmVendorSpec.JETBRAINS)
                                }
                            )
                        }
                    }
                }
            }


            mods {
                register("main") {
                    sourceSet(sourceSets.main.get())
                }
            }


        }

    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true

        configs.register("whats_your_build")

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("examplemod-fabric")
        // if (isModDevGradleRegular) configs.register("examplemod-neoforge")
        // if (isModDevGradleLegacy) configs.register("examplemod-forge")
    }
}

base {
    val meta = modstitch.metadata
    archivesName = "${meta.modName.get()}-$loader-$minecraft"
}

afterEvaluate {
    if (modstitch.isModDevGradle){
        tasks.getByName<CreateMinecraftArtifacts>("createMinecraftArtifacts"){
            dependsOn(tasks.getByName<ProcessResources>("generateModMetadata"))
        }
    }

}


stonecutter {
    constants.putAll(mapOf<String, Boolean>(
        "fabric" to loader.equals("fabric"),
        "neoforge" to loader.equals("neoforge"),
        "forge" to loader.equals("forge"),
        "vanilla" to loader.equals("vanilla"),
        "curios" to (loader.equals("forge") || loader.equals("neoforge")),
        "mas" to loader.equals("forge"),
    ))

}


dependencies {
    fun Dependency?.jij() = this?.also(::modstitchJiJ)
    fun String.implementation() = if (modstitch.isModDevGradleLegacy){
        //avoid the modstitch remap bug on 1.20.1
        add("modImplementation", this)
    } else {
        modstitchModImplementation(this)
    }
    fun String.runtimeOnly() = if (modstitch.isModDevGradleLegacy) {
        add("modRuntimeOnly", this)
    } else {
        modstitchModRuntimeOnly(this)
    }
    fun String.compileOnly() = if (modstitch.isModDevGradleLegacy) {
        add("modCompileOnly", this)
    } else {
        modstitchModCompileOnly(this)
    }


    propLib {
        "maven.modrinth:nirvana-library:$loader-$minecraft-$it".implementation()
    }
    modstitchModImplementation("maven.modrinth:common-network:${property("deps.common_network")}")

    prop("deps.fzzy_config_version"){
        val fzzyConfigVersion = findProperty("deps.fzzy_config_version")
        val fzzyMinecraftVersion = when (minecraft) {
            "1.21.1" -> "1.21"
            "1.21.4" -> "1.21.3"
            "1.21.8" -> "1.21.6"
            "1.21.10" -> "1.21.9"
            else -> minecraft
        }
        var fzzyString : String = "";

        modstitch.loom {
            prop("deps.fabric_api"){
                ("net.fabricmc.fabric-api:fabric-api:$it+${minecraft}").implementation()
            }
            fzzyString = "me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+${fzzyMinecraftVersion}";

        }

        modstitch.moddevgradle {
            if (modstitch.isModDevGradleLegacy){
                fzzyString = "me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+${fzzyMinecraftVersion}+forge";
            } else {
                if (minecraft == "1.21.8"){
                    fzzyString = "me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+1.21.7+neoforge";
                } else {
                    fzzyString = "me.fzzyhmstrs:fzzy_config:${fzzyConfigVersion}+${fzzyMinecraftVersion}+neoforge"
                }

            }

        }

        modstitchModCompileOnly(fzzyString)
        (fzzyString).runtimeOnly()
    }



    //lombok
    modstitchCompileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")

    DependencyConfig.getDependencies(loader, minecraft).forEach { dep ->
        dependencies.add(dep.configuration, dep.notation, dep.options)
    }

    modstitchImplementation("com.google.code.findbugs:jsr305:3.0.2")
}

msPublishing {

    mpp {
        changelog = file("../../changelog.md")
            .readLines()
            .joinToString("\n") { line ->
                if (line.isNotBlank()) {
                    "$line</br>"
                } else {
                    line
                }
            }
        type = BETA
        afterEvaluate {
            val finalFile = modstitch.finalJarTask.map { it.archiveFile.get() }
            file.set(finalFile)
            this@mpp.displayName = file.map { it.asFile.name }
        }

        //dryRun = true
        val cfOptions = curseforgeOptions {
            accessToken = file("D:\\curseforge-key.txt").readText()
            projectId = "1233310"
            minecraftVersions.add(minecraft)
            requires("nirvana-library")
        }
/*
        // Modrinth options used by both Fabric and Forge
        val mrOptions = modrinthOptions {
            accessToken = file("D:\\modrinth-key.txt").readText()
            version = "${loader}-${minecraft}-${modstitch.metadata.modVersion.get()}"
            projectId = "rp7ooqvq"
            minecraftVersions.add(minecraft)
            requires("nirvana-library")
        }
*/
        curseforge("toCurseForge") {
            from(cfOptions)
        }

/*
        modrinth("toModrinth") {
            from(mrOptions)
        }
*/

    }

}