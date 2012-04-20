package net.ostis.sccore.scperformer;

import java.util.Iterator;
import java.util.List;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scfactory.ScFactoryImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Class that provide all general actions with sc memory.
 *
 * @author yaskoam
 */
public abstract class ScPerformer {

    /**
     * Method that return sc factory object for further working.
     *
     * @return sc factory object
     */
    public ScFactory getScFactory() {
        return ScFactoryImpl.getInstance();
    }

    /**
     * Method that start data base transaction for execute
     * different operation.
     */
    public abstract void startExecution();

    /**
     * Method that finish transaction.
     */
    public abstract void finishExecution();

    /**
     * Method that find sc node by name in memory.
     *
     * @param nodeName name of node
     * @return founded node
     */
    public abstract ScNode findScNodeByName(String nodeName);

    /**
     * Method that remove sc arc.
     * When it was deleted, connected with it sc arcs was deleted too.
     *
     * @param arc deleted sc arc
     */
    public abstract void deleteScArc(ScArc arc);

    /**
     * Method that remove sc node. When it was deleted,
     * connected with it sc arcs was deleted too.
     *
     * @param scNode deleted sc node
     */
    public abstract void deleteScNode(ScNode scNode);

    /**
     * Creates f_a_f iterator.
     *
     * @param first first element in constraint
     * @param secondType type of second element in constraint
     * @param third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    public abstract Iterator createIterator_3_f_a_f(ScElement first, List<ScElementTypes> arcTypes, ScElement third);

    /**
     * Creates f_a_a iterator.
     *
     * @param first first element in constraint
     * @param secondType type of second element in constraint
     * @param thirdType type of third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    public abstract Iterator createIterator_3_f_a_a(ScElement first, List<ScElementTypes> secondTypes,
        List<ScElementTypes> thirdTypes);

    /**
     * Creates a_a_f iterator.
     *
     * @param firstType type of first element in constraint
     * @param secondType type of second element in constraint
     * @param third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    public abstract Iterator createIterator_3_a_a_f(List<ScElementTypes> nodeTypes,
        List<ScElementTypes> arcTypes, ScElement third);

    public abstract Iterator createIterator_5_a_a_a_a_f(List<ScElementTypes> firstTypes, List<ScElementTypes> secondTypes,
        List<ScElementTypes> thirdTypes, List<ScElementTypes> fourthTypes, ScElement fifthElement);

    public abstract Iterator createIterator_5_a_a_f_a_a(List<ScElementTypes> firstTypes, List<ScElementTypes> secondTypes,
        ScElement thirdElement, List<ScElementTypes> fourthTypes, List<ScElementTypes> fifthTypes);

    public abstract Iterator createIterator_5_a_a_f_a_f(List<ScElementTypes> firstTypes, List<ScElementTypes> secondTypes,
        ScElement thirdElement, List<ScElementTypes> fourthTypes, ScElement fifthElement);

    public abstract Iterator createIterator_5_f_a_a_a_a(ScElement firstElement, List<ScElementTypes> secondTypes,
        List<ScElementTypes> thirdTypes, List<ScElementTypes> fourthTypes, List<ScElementTypes> fifthTypes);

    public abstract Iterator createIterator_5_f_a_a_a_f(ScElement firstElement, List<ScElementTypes> secondTypes,
        List<ScElementTypes> thirdTypes, List<ScElementTypes> fourthTypes, ScElement fifthElement);

    public abstract Iterator createIterator_5_f_a_f_a_a(ScElement firstElement, List<ScElementTypes> secondTypes,
        ScElement thirdElement, List<ScElementTypes> fourthTypes, List<ScElementTypes> fifthTypes);

    public abstract Iterator createIterator_5_f_a_f_a_f(ScElement firstElement, List<ScElementTypes> secondTypes,
        ScElement thirdElement, List<ScElementTypes> fourthTypes, ScElement fifthElement);
}
