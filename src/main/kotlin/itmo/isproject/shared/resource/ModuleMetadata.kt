package itmo.isproject.shared.resource

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@PackageInfo
@ApplicationModule(
    id = "resource",
    displayName = "Resource",
    type = ApplicationModule.Type.OPEN
)
class ModuleMetadata