package net.ostis.sccore.scelements;

import org.neo4j.graphdb.Node;


/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:40
 */
public abstract class ScArc extends ScElement {

    public static final String SC_ARC_TYPE_PROPERTY = "_scArcType";

    /**
     * Gets start sc node of sc arc.
     * If start element of sc arc is another sc arc, then return null.
     * @return founded sc node
     */
    public abstract ScNode getStartScNode();

    /**
     * Method that get end sc node of sc arc.
     * If end element of sc arc is another sc arc, then return null.
     * @return founded sc node
     */
    public abstract ScNode getEndScNode();

    public abstract Node getArcConnectorNode();
}
