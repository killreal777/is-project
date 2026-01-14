package itmo.isproject

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ModularityTest {

    private val modules = ApplicationModules.of(IsProjectApplication::class.java)

    @Test
    fun `verify module structure`() {
        modules.verify()
    }

    @Test
    fun `generate documentation`() {
        Documenter(modules)
            .writeDocumentation()
            .writeIndividualModulesAsPlantUml()
    }
}
