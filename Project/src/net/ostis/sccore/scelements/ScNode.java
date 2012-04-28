package net.ostis.sccore.scelements;

import java.util.List;

import net.ostis.sccore.contents.Content;

/**
 * Class for presentation SC node.
 *
 * @author yaskoam
 */
public abstract class ScNode extends ScElement {
    /**
     * Method that get name of sc node.
     *
     * @return name of sc node
     */
    public abstract String getName();

    /**
     * Method that set name for sc node.
     *
     * @param name node name
     */
    public abstract void setName(String name);

    /**
     * Method that get all output sc arcs from sc node.
     *
     * @return list of sc arcs
     */
    public abstract List<ScArc> getAllOutputScArcs();

    /**
     * Method that get all input sc arcs to sc node.
     *
     * @return list of sc arcs
     */
    public abstract List<ScArc> getAllInputScArcs();

    /**
     * Method that get all sc arcs connected with node.
     *
     * @return list of sc arcs
     */
    public abstract List<ScArc> getAllScArc();

    /**
     * Method that set content in node.
     *
     * @param content object
     */
    public abstract void setContent(Content content);

    /**
     * Method that get content from node.
     *
     * @return content object
     */
    public abstract Content getContent();
}
