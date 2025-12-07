package itmo.isproject.model.module

import itmo.isproject.model.IntIdEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@MappedSuperclass
abstract class ModuleBlueprint(

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_cost_id", referencedColumnName = "id", nullable = false, unique = true, updatable = false)
    var buildCost: BuildCost? = null,

    @NotBlank
    @Size(max = 48)
    @Column(name = "name", length = 48, nullable = false, unique = true, updatable = false)
    var name: String? = null

) : IntIdEntity()
