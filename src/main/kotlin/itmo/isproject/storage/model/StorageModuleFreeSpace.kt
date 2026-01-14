package itmo.isproject.storage.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class StorageModuleFreeSpace(
    var storageModule: @NotNull StorageModule? = null,
    var freeSpace: @Min(0) @NotNull Int = 0
) {

    constructor(storageModuleFreeSpacePair: Pair<StorageModule, Int>) : this(
        storageModule = storageModuleFreeSpacePair.first,
        freeSpace = storageModuleFreeSpacePair.second
    )

    operator fun plusAssign(amount: Int) {
        this.freeSpace = this.freeSpace.plus(amount)
    }

    operator fun minusAssign(amount: Int) {
        this.freeSpace = this.freeSpace.minus(amount)
    }
}
