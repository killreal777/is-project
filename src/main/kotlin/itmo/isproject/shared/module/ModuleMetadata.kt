package itmo.isproject.shared.module

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@PackageInfo
@ApplicationModule(
    id = "module",
    displayName = "Module",
    type = ApplicationModule.Type.OPEN
)
class ModuleMetadata