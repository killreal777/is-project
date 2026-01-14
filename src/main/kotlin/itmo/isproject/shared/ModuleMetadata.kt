package itmo.isproject.shared

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@PackageInfo
@ApplicationModule(
    id = "shared",
    displayName = "Shared",
    type = ApplicationModule.Type.OPEN
)
class ModuleMetadata