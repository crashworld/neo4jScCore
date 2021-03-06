package net.ostis.sccore.scperformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.iterators.ScIteratorTypes;
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
        DataBaseManager dataBaseManager = DataBaseManager.getDataBaseManagerInstance(basePath);
        dataBase = dataBaseManager.getDataBase();

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
     * Method that start data base transaction.
     */
    @Override
    public void startExecution() {
        transaction = dataBase.beginTx();
    }

    /**
     * Method that finish transaction.
     */
    @Override
    public void finishExecution() {
        transaction.success();
        transaction.finish();
    }

    /**
     * Method that sets transaction succesfull.
     */
    @Override
    public void successExecution() {
        transaction.success();
    }

    /**
     * Method that sets transaction failure.
     */
    @Override
    public void failureExecution() {
        transaction.failure();
    }

    /**
     * Method that find sc node by name in memory.
     * <p> If exist several nodes with the same name, it return some of them. </p>
     * @param nodeName name of node
     * @return founded node
     */
    @Override
    public ScNode findScNodeByName(String nodeName) {
        AutoIndexer<Node> autoIndexer = dataBase.index().getNodeAutoIndexer();
        ReadableIndex<Node> index = autoIndexer.getAutoIndex();

        IndexHits<Node> indexHits = index.get(ScNodeImpl.SC_NODE_NAME_PROPERTY, nodeName);
        try {
            if (indexHits.hasNext()) {
                Node node = indexHits.next();
                return new ScNodeImpl(node);
            }
            else {
                return null;
            }
        }
        finally {
            indexHits.close();
        }
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
        arcImpl.removeAllTypes();

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

        if (endNode.hasProperty(ScNodeImpl.CONNECTOR_NODE)) {
            event = new ScEvent(ScEventTypes.DETACH_INPUT_FROM_ARC, new ScArcImpl(endNode));
        }
        else {
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
        scNodeImpl.removeAllTypes();

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
     * Method that create sc iterator.
     * @param type type of sc iterator
     * @param args search args for sc iterator
     * @return sc iterator object
     */
    @Override
    public Iterator createIterator(String type, Object... args) {
        if (type.equals(ScIteratorTypes.A_A_F)) {
            return createIterator_3_a_a_f((List<String>) args[0], (List<String>) args[1], (ScElement) args[2]);
        }
        if (type.equals(ScIteratorTypes.F_A_A)) {
            return createIterator_3_f_a_a((ScElement) args[0], (List<String>) args[1], (List<String>) args[2]);
        }
        if (type.equals(ScIteratorTypes.F_A_F)) {
            return createIterator_3_f_a_f((ScElement) args[0], (List<String>) args[1], (ScElement) args[2]);
        }
        if (type.equals(ScIteratorTypes.A_A_A_A_F)) {
            return createIterator_5_a_a_a_a_f((List<String>) args[0], (List<String>) args[1], (List<String>) args[2],
                (List<String>) args[3], (ScElement) args[4]);
        }
        if (type.equals(ScIteratorTypes.A_A_F_A_A)) {
            return createIterator_5_a_a_f_a_a((List<String>) args[0], (List<String>) args[1], (ScElement) args[2],
                (List<String>) args[3], (List<String>) args[4]);
        }
        if (type.equals(ScIteratorTypes.A_A_F_A_F)) {
            return createIterator_5_a_a_f_a_f((List<String>) args[0], (List<String>) args[1], (ScElement) args[2],
                (List<String>) args[3], (ScElement) args[4]);
        }
        if (type.equals(ScIteratorTypes.F_A_A_A_A)) {
            return createIterator_5_f_a_a_a_a((ScElement) args[0], (List<String>) args[1], (List<String>) args[2],
                (List<String>) args[3], (List<String>) args[4]);
        }
        if (type.equals(ScIteratorTypes.F_A_A_A_F)) {
            return createIterator_5_f_a_a_a_f((ScElement) args[0], (List<String>) args[1], (List<String>) args[2],
                (List<String>) args[3], (ScElement) args[4]);
        }
        if (type.equals(ScIteratorTypes.F_A_F_A_A)) {
            return createIterator_5_f_a_f_a_a((ScElement) args[0], (List<String>) args[1], (ScElement) args[2],
                (List<String>) args[3], (List<String>) args[4]);
        }
        if (type.equals(ScIteratorTypes.F_A_F_A_F)) {
            return createIterator_5_f_a_f_a_f((ScElement) args[0], (List<String>) args[1], (ScElement) args[2],
                (List<String>) args[3], (ScElement) args[4]);
        }
        throw new NoSuchMethodError("Method for creating iterator \"" + type + "\" not found");
    }

    /**
     * Creates f_a_f iterator.
     *
     * @param first first element in constraint
     * @param secondTypes types of second element in constraint
     * @param third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    private Iterator createIterator_3_f_a_f(ScElement first, List<String> secondTypes, ScElement third) {

        List<Long> secondAddresses = getAddresses(secondTypes);

        return new ScIterator_3_f_a_f(dataBase, first, secondAddresses, third);
    }

    /**
     * Creates f_a_a iterator.
     *
     * @param first first element in constraint
     * @param secondTypes types of second element in constraint
     * @param thirdTypes types of third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    private Iterator createIterator_3_f_a_a(ScElement first, List<String> secondTypes,
        List<String> thirdTypes) {

        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> thirdAddresses = getAddresses(thirdTypes);

        return new ScIterator_3_f_a_a(dataBase, first, secondAddresses, thirdAddresses);
    }

    /**
     * Creates 3_a_a_f iterator.
     *
     * @param firstTypes types of first element in constraint
     * @param secondTypes types of second element in constraint
     * @param third element in constraint
     * @return java.util.Iterator for iterate over ScConstraints
     */
    private Iterator createIterator_3_a_a_f(List<String> firstTypes, List<String> secondTypes,
        ScElement third) {

        List<Long> firstAddresses = getAddresses(firstTypes);
        List<Long> secondAddresses = getAddresses(secondTypes);

        return new ScIterator_3_a_a_f(dataBase, firstAddresses, secondAddresses, third);
    }

    private Iterator createIterator_5_f_a_a_a_a(ScElement firstElement, List<String> secondTypes,
        List<String> thirdTypes, List<String> fourthTypes, List<String> fifthTypes) {

        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> thirdAddresses = getAddresses(thirdTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);
        List<Long> fifthAddresses = getAddresses(fifthTypes);

        return new ScIterator_5_f_a_a_a_a(dataBase, firstElement, secondAddresses, thirdAddresses, fourthAddresses,
            fifthAddresses);
    }

    private Iterator createIterator_5_f_a_a_a_f(ScElement firstElement, List<String> secondTypes,
        List<String> thirdTypes, List<String> fourthTypes, ScElement fifthElement) {

        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> thirdAddresses = getAddresses(thirdTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);

        return new ScIterator_5_f_a_a_a_f(dataBase, firstElement, secondAddresses, thirdAddresses, fourthAddresses,
            fifthElement);
    }

    private Iterator createIterator_5_f_a_f_a_a(ScElement firstElement, List<String> secondTypes,
        ScElement thirdElement, List<String> fourthTypes, List<String> fifthTypes) {

        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);
        List<Long> fifthAddresses = getAddresses(fifthTypes);

        return new ScIterator_5_f_a_f_a_a(dataBase, firstElement, secondAddresses, thirdElement, fourthAddresses,
            fifthAddresses);
    }

    private Iterator createIterator_5_a_a_a_a_f(List<String> firstTypes, List<String> secondTypes,
        List<String> thirdTypes, List<String> fourthTypes, ScElement fifthElement) {

        List<Long> firstAddresses = getAddresses(firstTypes);
        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> thirdAddresses = getAddresses(thirdTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);

        return new ScIterator_5_a_a_a_a_f(dataBase, firstAddresses, secondAddresses, thirdAddresses, fourthAddresses,
            fifthElement);
    }

    private Iterator createIterator_5_a_a_f_a_a(List<String> firstTypes, List<String> secondTypes,
        ScElement thirdElement, List<String> fourthTypes, List<String> fifthTypes) {

        List<Long> firstAddresses = getAddresses(firstTypes);
        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);
        List<Long> fifthAddresses = getAddresses(fifthTypes);

        return new ScIterator_5_a_a_f_a_a(dataBase, firstAddresses, secondAddresses, thirdElement, fourthAddresses,
            fifthAddresses);
    }

    private Iterator createIterator_5_a_a_f_a_f(List<String> firstTypes, List<String> secondTypes,
        ScElement thirdElement, List<String> fourthTypes, ScElement fifthElement) {

        List<Long> firstAddresses = getAddresses(firstTypes);
        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);

        return new ScIterator_5_a_a_f_a_f(dataBase, firstAddresses, secondAddresses, thirdElement, fourthAddresses,
            fifthElement);
    }

    private Iterator createIterator_5_f_a_f_a_f(ScElement firstElement, List<String> secondTypes,
        ScElement thirdElement, List<String> fourthTypes, ScElement fifthElement) {

        List<Long> secondAddresses = getAddresses(secondTypes);
        List<Long> fourthAddresses = getAddresses(fourthTypes);

        return new ScIterator_5_f_a_f_a_f(dataBase, firstElement, secondAddresses, thirdElement, fourthAddresses,
            fifthElement);
    }

    /**
     * Gets addresses of type-nodes
     *
     * @param elements list of types
     * @return ArrayList of addresses
     */
    private List<Long> getAddresses(List<String> elements) {
        IndexManager indexManager = dataBase.index();
        Index<Node> index = indexManager.forNodes(ScElementTypes.ELEMENT_TYPE_PROPERTY);

        List<Long> typeAddresses = new ArrayList<Long>();

        for (String nodeType : elements) {
            Node node = index.get(ScElementTypes.ELEMENT_TYPE_PROPERTY, nodeType).next();
            typeAddresses.add(node.getId());
        }

        return typeAddresses;

    }
}
