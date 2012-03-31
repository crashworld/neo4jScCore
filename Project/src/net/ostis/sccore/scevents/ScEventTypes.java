package net.ostis.sccore.scevents;

/**
 *
 * Enum for various type of event in sc memory.
 * 
 * @author yaskoam
 */
public enum ScEventTypes {
    CREATE_SC_NODE("CREATE_SC_NODE"),
    DELETE_SC_NODE("DELETE_SC_NODE"),
    ATTACH_INPUT_TO_NODE("ATTACH_INPUT_TO_NODE"),
    ATTACH_OUTPUT_TO_NODE("ATTACH_OUTPUT_TO_NODE"),
    ATTACH_INPUT_TO_ARC("ATTACH_INPUT_TO_ARC"),
    DETACH_INPUT_FROM_NODE("DETACH_INPUT_FROM_NODE"),
    DETACH_OUTPUT_FROM_NODE("DETACH_OUTPUT_FROM_NODE"),
    DETACH_INPUT_FROM_ARC("DETACH_INPUT_FROM_ARC");

    private String typeName = "";
    
    private ScEventTypes(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
