package net.ostis.sccore.scperformer;

import java.util.Iterator;
import java.util.List;
import net.ostis.sccore.iterators.ScIterator_3_a_a_f;
import net.ostis.sccore.iterators.ScIterator_3_f_a_a;
import net.ostis.sccore.iterators.ScIterator_3_f_a_f;
import net.ostis.sccore.iterators.ScIterator_5_a_a_a_a_f;
import net.ostis.sccore.iterators.ScIterator_5_a_a_f_a_a;
import net.ostis.sccore.iterators.ScIterator_5_a_a_f_a_f;
import net.ostis.sccore.iterators.ScIterator_5_f_a_a_a_a;
import net.ostis.sccore.iterators.ScIterator_5_f_a_a_a_f;
import net.ostis.sccore.iterators.ScIterator_5_f_a_f_a_a;
import net.ostis.sccore.iterators.ScIterator_5_f_a_f_a_f;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.ReadableIndex;

import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScArcImpl;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;
import net.ostis.sccore.scevents.ScEvent;
import net.ostis.sccore.scevents.ScEventHandler;
import net.ostis.sccore.scevents.ScEventTypes;
import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scfactory.ScFactoryImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Class that provide all general actions with sc memory.
 * 
 * @author yaskoam
 */
public class ScPerformerImpl extends ScPerformer {

    private AbstractGraphDatabase dataBase;
    private Transaction transaction;

    /**
     * Construct object with the data base path.
     *
     * @param basePath path
     */
    public ScPerformerImpl(String basePath) {
        dataBase = DataBaseConnector.getDataBaseInstance(basePath);

        ScFactoryImpl factory = ScFactoryImpl.getInstance();
        factory.setDataBase(dataBase);
    }

    /**
     * Method that return sc factory object for further working.
     *
     * @return sc factory object
     */
    @Override
    public ScFactory getScFactory() {
        return ScFactoryImpl.getInstance();
    }

    /**
     * Method that start data base transaction and start create Index for types
     * for execute different operation.
     */
    @Override
    public void startExecution() {
        transaction = dataBase.beginTx();
        DataBaseConnector.createIndex(dataBase);
    }

    /**
     * Method that finish transaction.
     *
     */
    @Override
    public void finishExecution() {
        transaction.success();
        transaction.finish();
    }

    /**
     * Method that find sc node by name in memory.
     *
     * @param nodeName name of node
     * @return founded node
     */
    @Override
    public ScNode findScNodeByName(String nodeName) {
        AutoIndexer<Node> autoIndexer = dataBase.index().getNodeAutoIndexer();
        ReadableIndex<Node> index = autoIndexer.getAutoIndex();
        Node node = index.get(ScNode.SC_NODE_NAME_PROPERTY, nodeName).getSingle();
        if (node == null) {
            return null;
        }
        return new ScNodeImpl(node);
    }

    /**
     * Method that remove sc arc. 
     * When it was deleted, connected with it sc arcs was deleted too.
     *
     * @param arc deleted sc arc
     */
    @Override
    public void deleteScArc(ScArc arc) {
        ScArcImpl arcImpl = (ScArcImpl) arc;
        List<ScArc> inputArcsList = arcImpl.getAllInputScArcs();

        for (ScArc currentArc : inputArcsList) {
            this.deleteScArc(currentArc);
        }

        Node connector = arcImpl.getArcConnectorNode();
        Relationship beginRelationship = connector.getSingleRelationship(RelTypes.beginLink, Direction.INCOMING);
        Relationship endRelationship = connector.getSingleRelationship(RelTypes.endLink, Direction.OUTGOING);

        Node beginNode = beginRelationship.getStartNode();
        Node endNode = endRelationship.getEndNode();

        beginRelationship.delete();
        endRelationship.delete();
        connector.delete();

        /* For handling event. Notify event handler about detach arc. */
        ScEvent event = new ScEvent(ScEventTypes.DETACH_OUTPUT_FROM_NODE, new ScNodeImpl(beginNode));
        ScEventHandler.getInstance().notify(event);

        if (endNode.hasProperty(ScNodeImpl.CONNECTORNODE)) {
            event = new ScEvent(ScEventTypes.DETACH_INPUT_FROM_ARC, new ScArcImpl(endNode));
        } else {
            event = new ScEvent(ScEventTypes.DETACH_INPUT_FROM_NODE, new ScNodeImpl(endNode));
        }

        ScEventHandler.getInstance().notify(event);

        arc = null;
    }

    /**
     * Method that remove sc node. 
     * When it was deleted, connected with it sc arcs was deleted too.
     * 
     * @param scNode deleted sc node
     */
    @Override
    public void deleteScNode(ScNode scNode) {
        ScNodeImpl scNodeImpl = (ScNodeImpl) scNode;
        List<ScArc> scArcsList = scNodeImpl.getAllScArc();

        for (ScArc currentArc : scArcsList) {
            this.deleteScArc(currentArc);
        }

        /* For handling event. Notify event handler about removing sc node. */
        ScEvent event = new ScEvent(ScEventTypes.DELETE_SC_NODE, scNode);
        ScEventHandler.getInstance().notify(event);

        Node node = scNodeImpl.getNeo4jNode();
        node.delete();

        scNode = null;
    }

    /**
     * Creates f_a_f iterator.
     *
     * @param first first element in constraint
     * @param secondType type of second element in constraint
     * @param third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    @Override
    public Iterator createIterator_3_f_a_f(ScElement first, List<ScElementTypes> arcTypes, ScElement third) {
        return new ScIterator_3_f_a_f(dataBase, first, arcTypes, third);
    }

    /**
     * Creates f_a_a iterator.
     *
     * @param first first element in constraint
     * @param secondType type of second element in constraint
     * @param thirdType type of third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    @Override
    public Iterator createIterator_3_f_a_a(ScElement first, List<ScElementTypes> secondTypes,
            List<ScElementTypes> thirdTypes) {
        return new ScIterator_3_f_a_a(dataBase, first, secondTypes, thirdTypes);
    }

    /**
     * Creates a_a_f iterator.
     *
     * @param firstType type of first element in constraint
     * @param secondType type of second element in constraint
     * @param third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    @Override
    public Iterator createIterator_3_a_a_f(List<ScElementTypes> nodeTypes,
            List<ScElementTypes> arcTypes, ScElement third) {
        return new ScIterator_3_a_a_f(dataBase, nodeTypes, arcTypes, third);
    }

    @Override
    public Iterator createIterator_5_f_a_a_a_a(ScElement firstElement, List<ScElementTypes> secondTypes,
            List<ScElementTypes> thirdTypes, List<ScElementTypes> fourthTypes, List<ScElementTypes> fifthTypes) {
        return new ScIterator_5_f_a_a_a_a(dataBase, firstElement, secondTypes, thirdTypes, fourthTypes, fifthTypes);
    }

    @Override
    public Iterator createIterator_5_f_a_a_a_f(ScElement firstElement, List<ScElementTypes> secondTypes,
            List<ScElementTypes> thirdTypes, List<ScElementTypes> fourthTypes, ScElement fifthElement) {
        return new ScIterator_5_f_a_a_a_f(dataBase, firstElement, secondTypes, thirdTypes, fourthTypes, fifthElement);
    }

    @Override
    public Iterator createIterator_5_f_a_f_a_a(ScElement firstElement, List<ScElementTypes> secondTypes,
            ScElement thirdElement, List<ScElementTypes> fourthTypes, List<ScElementTypes> fifthTypes) {
        return new ScIterator_5_f_a_f_a_a(dataBase, firstElement, secondTypes, thirdElement, fourthTypes, fifthTypes);
    }

    @Override
    public Iterator createIterator_5_a_a_a_a_f(List<ScElementTypes> firstTypes, List<ScElementTypes> secondTypes,
            List<ScElementTypes> thirdTypes, List<ScElementTypes> fourthTypes, ScElement fifthElement) {
        return new ScIterator_5_a_a_a_a_f(dataBase, firstTypes, secondTypes, thirdTypes, fourthTypes, fifthElement);
    }

    @Override
    public Iterator createIterator_5_a_a_f_a_a(List<ScElementTypes> firstTypes, List<ScElementTypes> secondTypes,
            ScElement thirdElement, List<ScElementTypes> fourthTypes, List<ScElementTypes> fifthTypes) {
        return new ScIterator_5_a_a_f_a_a(dataBase, firstTypes, secondTypes, thirdElement, fourthTypes, fifthTypes);
    }

    @Override
    public Iterator createIterator_5_a_a_f_a_f(List<ScElementTypes> firstTypes, List<ScElementTypes> secondTypes,
            ScElement thirdElement, List<ScElementTypes> fourthTypes, ScElement fifthElement) {
        return new ScIterator_5_a_a_f_a_f(dataBase, firstTypes, secondTypes, thirdElement, fourthTypes, fifthElement);
    }

    @Override
    public Iterator createIterator_5_f_a_f_a_f(ScElement firstElement, List<ScElementTypes> secondTypes,
            ScElement thirdElement, List<ScElementTypes> fourthTypes, ScElement fifthElement) {
        return new ScIterator_5_f_a_f_a_f(dataBase, firstElement, secondTypes, thirdElement, fourthTypes, fifthElement);
    }
}
