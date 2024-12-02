package itmo.is.project.model.resource;

public interface ResourceAmountHolder extends ResourceIdAmountHolder {

    Resource getResource();

    void setResource(Resource resource);

    @Override
    default Integer getResourceId() {
        return getResource().getId();
    }

    default ResourceAmount getResourceAmount() {
        return new ResourceAmount(getResource(), getAmount());
    }

    default void setResourceAmount(ResourceAmount resourceAmount) {
        setResource(resourceAmount.getResource());
        setAmount(resourceAmount.getAmount());
    }
}
