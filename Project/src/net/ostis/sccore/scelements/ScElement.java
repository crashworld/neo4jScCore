package net.ostis.sccore.scelements;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:39
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
     * Method that sets type of sc element
     * @param type type of element
     */
    public abstract void setType(String type);

    /**
     * Method that get type of sc element
     * @return type of element
     */
    public abstract String getType();
}
