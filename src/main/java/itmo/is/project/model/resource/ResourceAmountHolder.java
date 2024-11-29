package itmo.is.project.model.resource;

public interface ResourceAmountHolder {

    Resource getResource();

    Integer getAmount();

    void setResource(Resource resource);

    void setAmount(Integer amount);

    default ResourceAmount getResourceAmount() {
        return new ResourceAmount(getResource(), getAmount());
    }

    default ResourceIdAmount getResourceIdAmount() {
        return new ResourceIdAmount(getResource().getId(), getAmount());
    }

    default void setResourceAmount(ResourceAmount resourceAmount) {
        setResource(resourceAmount.getResource());
        setAmount(resourceAmount.getAmount());
    }
}
