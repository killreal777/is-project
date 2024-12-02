package itmo.is.project.model.resource;

public interface ResourceIdAmountHolder {

    Integer getResourceId();

    Integer getAmount();

    void setAmount(Integer amount);

    default ResourceIdAmount getResourceIdAmount() {
        return new ResourceIdAmount(getResourceId(), getAmount());
    }

    default void add(int amount) {
        setAmount(getAmount() + amount);
    }

    default void sub(int amount) {
        setAmount(getAmount() - amount);
    }
}
