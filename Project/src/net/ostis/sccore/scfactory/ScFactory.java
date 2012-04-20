package net.ostis.sccore.scfactory;

import java.util.List;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class that provide generation different elements of sc memory.
 *
 * @author yaskoam
 */
public abstract class ScFactory {

    /**
     * Method that create sc node.
     *
     * @return created sc node
     */
    public abstract ScNode createScNode();

    /**
     * Method that create sc node with name.
     *
     * @param nodeName sc node name
     * @return created sc node
     */
    public abstract ScNode createScNode(String nodeName);

    /**
     * Method that create sc node with list of types and name.
     *
     * @param nodeName sc node name
     * @param types sc node types
     * @return created sc node
     */
    public abstract ScNode createScNode(String nodeName, List<String> types);

    /**
     * Method that create sc node with type and name.
     *
     * @param nodeName sc node name
     * @param type sc node type
     * @return created sc node
     */
    public abstract ScNode createScNode(String nodeName, String type);


    /**
     * Method that create sc arc to sc node.
     *
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScNode endScNode);

    /**
     * Method that create sc arc to sc node with type.
     *
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @param type type of sc arc
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScNode endScNode, String type);

    /**
     * Method that create sc arc to sc node with list of types.
     *
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @param types list of types
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScNode endScNode, List<String> types);

    /**
     * Method that create sc arc to sc arc.
     *
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScArc endScArc);

    /**
     * Method that create sc arc to sc arc with types.
     *
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @param type type of sc arc
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScArc endScArc, String type);

    /**
     * Method that create sc arc to sc arc with types.
     *
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @param types list of types
     * @return created sc arc
     */
    public abstract ScArc createScArc(ScNode startScNode, ScArc endScArc, List<String> types);

    /**
     * Method that generate sc constrain ( 0->0 ).
     *
     * @param startNode first node of constrain
     * @param types types of sc arc
     * @param endNode end node of constrain
     * @return generated sc arc
     */
    public abstract ScArc generate_3_f_a_f(ScNode startNode, List<String> types, ScNode endNode);

    /**
     * Method that generate sc constrain ( 0 -> | ).
     *
     * @param startNode first node of constrain
     * @param types types of sc arc
     * @param endScArc end sc arc of constrain
     * @return generated sc arc
     */
    public abstract ScArc generate_3_f_a_f(ScNode startNode, List<String> types, ScArc endScArc);


    /**
     * Method that generate sc constrain
     * 0
     * 0->|
     * 0 .
     *
     * @param firstNode first node of constrain
     * @param types1 types of first generated sc arc of constrain
     * @param secondNode second node of constrain
     * @param types2 types of second generated sc arc of constrain
     * @param thirdNode third node of constrain
     */
    public abstract void generate_5_f_a_f_a_f(ScNode firstNode, List<String> types1,
        ScNode secondNode, List<String> types2, ScNode thirdNode);

    /**
     * Method that generate sc constrain
     * 0->0->0 .
     *
     * @param firstNode first node of constrain
     * @param types1 types of first generated sc arc of constrain
     * @param secondNode second node of constrain
     * @param types2 types of second generated sc arc of constrain
     * @param thirdNode third node of constrain
     */
    public abstract void generate_5_f_a_f_a_f_1(ScNode firstNode, List<String> types1,
        ScNode secondNode, List<String> types2, ScNode thirdNode);

}
