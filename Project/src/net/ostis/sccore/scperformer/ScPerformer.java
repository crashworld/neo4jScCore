package net.ostis.sccore.scperformer;

import java.util.Iterator;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scfactory.ScFactoryImpl;

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
     * Method that sets transaction succesfull.
     */
    public abstract void successExecution();

    /**
     * Method that sets transaction failure.
     */
    public abstract void failureExecution();

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

    //============================================================
    public abstract Iterator createIterator(String type, Object... args);
    //============================================================

}
