package net.ostis.sccore.scelements;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:40
 */
public abstract class ScArc extends ScElement {
    public static final String SC_ARC_TYPE_PROPERTY = "_scArcType";

    /**
     * Method that get end sc node of sc arc.
     * If end element of sc arc is another sc arc, then return null.
     * @return founded sc node
     */
    public abstract ScNode getEndScNode();
}

