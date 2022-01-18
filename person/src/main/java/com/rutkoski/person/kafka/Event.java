package com.rutkoski.person.kafka;

public class Event {
    OperationEnum operation;
    private Object entity;

    public Event(OperationEnum operation, Object entity) {
        this.operation = operation;
        this.entity = entity;
    }

    public OperationEnum getOperation() {
        return operation;
    }

    public void setOperation(OperationEnum operation) {
        this.operation = operation;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
