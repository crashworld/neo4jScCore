package net.ostis.sccore.scelements;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:39
 */
public abstract class ScArc extends ScElement {
    public static final String SC_ARC_TYPE_PROPERTY = "_scArcType";

    /**
     * Method that get type of sc arc.
     * @return type of sc arc
     */
    public abstract String getType();
}
