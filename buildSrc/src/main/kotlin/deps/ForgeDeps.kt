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
            modstitchJiJ ("com.tterrag.registrate:Registrate:MC1.20-1.3.11")

            modstitchModImplementation("curse.maven:mine-and-slash-reloaded-306575:7103653")
            modstitchModRuntimeOnly("curse.maven:dungeon-realm-1200770:7103646")
            modstitchModImplementation("curse.maven:library-of-exile-398780:7103648")
            modstitchModRuntimeOnly("curse.maven:playeranimator-658587:4587214")
            modstitchModRuntimeOnly("curse.maven:the-harvest-1201731:7103642")
            modstitchModRuntimeOnly("curse.maven:ancient-obelisks-1186288:7103644")

            modstitchModImplementation("curse.maven:charm-of-undying-316873:5159193")
/*
            modstitchModRuntimeOnly ("curse.maven:architectury-api-419699:5137938")

*/
        }
    }
}