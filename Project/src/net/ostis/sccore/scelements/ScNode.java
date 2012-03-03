package net.ostis.sccore.scelements;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:39
 */
public abstract class ScNode extends ScElement {
    public static final String SC_NODE_NAME_PROPERTY = "_scNodeName";
    public static final String SC_NODE_TYPE_PROPERTY = "_scNodeType";

    /**
     * Method that get name of sc node.
     * @return name of sc node
     */
    public abstract String getName();

     /**
     * Method that get type of sc node.
     * @return type of sc node
     */
    public abstract String getType();
}
