package com.rutkoski.orders.kafka;

public class Event<T> {
    OperationEnum operation;
    private T entity;

    public Event() {
    }

    public OperationEnum getOperation() {
        return operation;
    }

    public void setOperation(OperationEnum operation) {
        this.operation = operation;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
