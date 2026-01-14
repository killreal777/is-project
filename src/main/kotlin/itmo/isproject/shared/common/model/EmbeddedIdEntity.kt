package itmo.isproject.shared.common.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.MappedSuperclass
import java.io.Serializable

@MappedSuperclass
abstract class EmbeddedIdEntity<ID : Serializable> : AbstractEntity<ID>() {

    @EmbeddedId
    override var id: ID? = null
}