package net.ostis.sccore.scelements;

import java.util.List;

/**
 * Class for presentation SC arc.
 *
 * @author yaskoam
 */
public abstract class ScArc extends ScElement {

    /**
     * Gets start sc node of sc arc.
     * If start element of sc arc is another sc arc, then return null.
     *
     * @return founded sc node
     */
    public abstract ScNode getStartScNode();

    /**
     * Method that get end sc node of sc arc.
     * If end element of sc arc is another sc arc, then return null.
     *
     * @return founded sc node
     */
    public abstract ScNode getEndScNode();

    /**
     * Method that get end sc arc of sc arc.
     * If end element of sc arc is sc node, then return null.
     *
     * @return founded sc arc
     */
    public abstract ScArc getEndScArc();

    /**
     * Method that get all input sc arcs.
     *
     * @return all input sc arcs
     */
    public abstract List<ScArc> getAllInputScArcs();


}
