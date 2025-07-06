package org.tpuchal.model.enums;

public enum RocketStatus {
    ON_GROUND("On ground"),
    IN_SPACE("In space"),
    IN_REPAIR("In repair");

    private final String displayName;

    RocketStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
