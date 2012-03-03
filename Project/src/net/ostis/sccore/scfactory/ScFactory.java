package net.ostis.sccore.scfactory;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:42
 */
public abstract class ScFactory {

    /**
     * Method that create sc node.
     * @param nodeName sc node name
     * @param nodeType sc node type
     * @return created sc node
     */
    public abstract ScNode createScNode(String nodeName,  String nodeType);

    /**
     * Method that create sc arc.
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @param type sc arc type
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScNode endScNode, String type);

    /**
     * Method that create sc arc.
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @param type sc arc type
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScArc endScArc, String type);
}
