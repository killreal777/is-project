package itmo.isproject.shared.resource.model

import itmo.isproject.shared.common.model.IntIdEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "resource")
class Resource(

    @NotBlank
    @Size(max = 32)
    @Column(name = "name", length = 32, nullable = false, unique = true, updatable = false)
    var name: String? = null

) : IntIdEntity()
