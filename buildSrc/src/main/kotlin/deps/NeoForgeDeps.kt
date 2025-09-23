package deps

object NeoForgeDeps {
    fun get(minecraft: String): List<VersionedDependency> {
        return buildDependencies{
            when (minecraft){
                "1.21.1" -> {
                    val enableApotheosis = false
                    val enableMalum = true
                    modstitchModImplementation ("maven.modrinth:curios:9.5.1+1.21.1")
                    modstitchModImplementation ("com.tterrag.registrate:Registrate:MC1.21-1.3.0+67")
                    modstitchJiJ ("com.tterrag.registrate:Registrate:MC1.21-1.3.0+67")

                    modstitchModImplementation ("curse.maven:curios-309927:6529130")
                }

                "1.21.4" -> {
                    modstitchModCompileOnly ("maven.modrinth:curios:10.0.1+1.21.4")
                    //modstitchModCompileOnly ("maven.modrinth:subtle-effects:bAQ7woyE")
                }
            }
        }
    }
}