package deps

object ForgeDeps {
    fun get(minecraft: String): List<VersionedDependency> {
        return buildDependencies{


            modstitchModImplementation("blank:Advanced Team-forge-1.20.1:1.6.2")

            modstitchModImplementation("thedarkcolour:kotlinforforge:4.11.0")

            modstitchModRuntimeOnly ("top.theillusivec4.curios:curios-forge:5.14.1+1.20.1")
            modstitchModCompileOnly ("top.theillusivec4.curios:curios-forge:5.14.1+1.20.1:api")

            modstitchModRuntimeOnly ("curse.maven:cloth-config-348521:5729105")
            modstitchModRuntimeOnly ("curse.maven:crafttweaker-239197:5880672")

            modstitchModImplementation ("com.tterrag.registrate:Registrate:MC1.20-1.3.11")

/*
            modstitchModRuntimeOnly ("curse.maven:architectury-api-419699:5137938")

*/
        }
    }
}