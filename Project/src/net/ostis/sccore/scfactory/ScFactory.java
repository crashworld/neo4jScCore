package net.ostis.sccore.scfactory;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class that provide generation different elements of sc memory.
 * @author yaskoam
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

    /**
     * Method that generate sc constrain ( 0->0 ).
     * @param startNode first node of constrain
     * @param type type of sc arc
     * @param endNode end node of constrain
     * @return generated sc arc
     */
    public abstract ScArc generate_3_f_a_f(ScNode startNode, String type, ScNode endNode);

    /**
     * Method that generate sc constrain ( 0 -> | ).
     * @param startNode first node of constrain
     * @param type type of sc arc
     * @param endScArc end sc arc of constrain
     * @return generated sc arc
     */
    public abstract ScArc generate_3_f_a_f(ScNode startNode, String type, ScArc endScArc);


    /**
     * Method that generate sc constrain
     *    0
     * 0->|
     *    0 .
     * @param firstNode first node of constrain
     * @param firstType type of first generated sc arc of constrain
     * @param secondNode second node of constrain
     * @param secondType type of second generated sc arc of constrain
     * @param thirdNode third node of constrain
     */
    public abstract void generate_5_f_a_f_a_f(ScNode firstNode, String firstType,
        ScNode secondNode, String secondType, ScNode thirdNode);

    /**
     * Method that generate sc constrain
     * 0->0->0 .
     * @param firstNode first node of constrain
     * @param firstType type of first generated sc arc of constrain
     * @param secondNode second node of constrain
     * @param secondType type of second generated sc arc of constrain
     * @param thirdNode third node of constrain
     */
    public abstract void generate_5_f_a_f_a_f_1(ScNode firstNode, String firstType,
        ScNode secondNode, String secondType, ScNode thirdNode);

}
