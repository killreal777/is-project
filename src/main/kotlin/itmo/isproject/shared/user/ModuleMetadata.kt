package itmo.isproject.shared.user

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@PackageInfo
@ApplicationModule(
    id = "user",
    displayName = "User",
    type = ApplicationModule.Type.OPEN
)
class ModuleMetadata