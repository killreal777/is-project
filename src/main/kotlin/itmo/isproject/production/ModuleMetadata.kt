package itmo.isproject.production

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@PackageInfo
@ApplicationModule(
    id = "production",
    displayName = "Production"
)
class ModuleMetadata