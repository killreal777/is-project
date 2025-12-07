package itmo.isproject.model

import org.springframework.data.util.ProxyUtils.getUserClass

abstract class AbstractEntity<ID> {

    abstract var id: ID?

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != getUserClass(other)) return false
        other as AbstractEntity<*>
        return this.id != null && this.id == other.id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "${this.javaClass.simpleName} (ID = $id)"
}