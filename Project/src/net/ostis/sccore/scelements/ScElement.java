package net.ostis.sccore.scelements;

import java.util.List;

import net.ostis.sccore.types.ScElementTypes;

/**
 * Class for presentation SC element.
 * @author yaskoam
 */
public abstract class ScElement {
    /**
     * Method that returns true if element is arc and false if not.
     * @return true if is arc
     */
    public abstract boolean isScArc();
    /**
     * Method that returns true if element is node and false if not.
     * @return true if is node
     */
    public abstract boolean isScNode();

    /**
     * Method that sets type of sc element.
     * @param type type of element
     */
    public abstract void addType(ScElementTypes type);

    /**
     * Method that get all types of sc elemetn.
     * @return list of types
     */
    public abstract List<ScElementTypes> getTypes();

    /**
     * Method that set types of sc element.
     * @param types list of types name
     */
    public abstract void addTypes(List<ScElementTypes> types);

    /**
     * Method that remove type from sc element.
     * @param type name of type
     */
    public abstract void removeType(ScElementTypes type);
}
