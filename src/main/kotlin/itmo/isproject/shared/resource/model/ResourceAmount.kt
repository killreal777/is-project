package itmo.isproject.shared.resource.model

interface ResourceIdAmountHolder {
    val resourceId: Int?
    var amount: Int?

    operator fun plusAssign(amount: Int) {
        this.amount = this.amount?.plus(amount) ?: amount
    }

    operator fun minusAssign(amount: Int) {
        this.amount = this.amount?.minus(amount) ?: -amount
    }
}

data class ResourceIdAmount(
    override val resourceId: Int? = null,
    override var amount: Int? = null
) : ResourceIdAmountHolder

interface ResourceAmountHolder : ResourceIdAmountHolder {
    var resource: Resource?
    override val resourceId: Int?
        get() = resource?.id
}

data class ResourceAmount(
    override var resource: Resource? = null,
    override var amount: Int? = null
) : ResourceAmountHolder {

    constructor(resourceId: Number, resourceName: String, amount: Number) : this(
        Resource().apply {
            id = resourceId.toInt()
            name = resourceName
        },
        amount.toInt()
    )
}
