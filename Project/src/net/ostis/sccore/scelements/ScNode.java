package net.ostis.sccore.scelements;

import java.util.List;

/**
 * Class for presentation SC node.
 * @author yaskoam
 */
public abstract class ScNode extends ScElement {
    /** String constant for name of node attribute. */
    public static final String SC_NODE_NAME_PROPERTY = "_scNodeName";
    //public static final String SC_NODE_TYPE_PROPERTY = "_scNodeType";

    /**
     * Method that get name of sc node.
     * @return name of sc node
     */
    public abstract String getName();

    /**
     * Method that get all output sc arcs from sc node.
     * @return list of sc arcs
     */
    public abstract List<ScArc> getAllOutputScArcs();

    /**
     * Method that get all input sc arcs to sc node.
     * @return list of sc arcs
     */
    public abstract List<ScArc> getAllInputScArcs();

    /**
     * Method that get all sc arcs connected with node.
     * @return list of sc arcs
     */
    public abstract List<ScArc> getAllScArc();
}
