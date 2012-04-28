package net.ostis.sccore.scfactory;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScArcImpl;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;
import net.ostis.sccore.scevents.ScEvent;
import net.ostis.sccore.scevents.ScEventHandler;
import net.ostis.sccore.scevents.ScEventTypes;

/**
 * Class that implement sc factory.
 *
 * @author yaskoam
 */
public class ScFactoryImpl extends ScFactory {

    private AbstractGraphDatabase dataBase = null;
    private static ScFactoryImpl factory = null;

    /**
     * Private constructor
     */
    private ScFactoryImpl() {
    }

    /**
     * Method that get factory object if then exist and create them if not.
     *
     * @return factory object
     */
    public synchronized static ScFactoryImpl getInstance() {
        if (factory == null) {
            factory = new ScFactoryImpl();
        }

        return factory;
    }

    /**
     * Method that create sc node.
     *
     * @return created sc node
     */
    @Override
    public ScNode createScNode() {
        Node node = dataBase.createNode();
        ScNodeImpl scNode = new ScNodeImpl(node);

        /* create event */
        ScEventHandler eventHandler = ScEventHandler.getInstance();
        ScEvent event = new ScEvent(ScEventTypes.CREATE_SC_NODE, scNode);
        eventHandler.notify(event);
        scNode.setName("");

        return scNode;
    }

    /**
     * Method that create sc node with name.
     *
     * @param nodeName sc node name
     * @return created sc node
     */
    @Override
    public ScNode createScNode(String nodeName) {
        if (nodeName == null) {
            throw new IllegalArgumentException("Creation sc node: null name.");
        }

//        AutoIndexer<Node> autoIndexer = dataBase.index().getNodeAutoIndexer();
//        ReadableIndex<Node> index = autoIndexer.getAutoIndex();
//        Node node = index.get(ScNodeImpl.SC_NODE_NAME_PROPERTY, nodeName).getSingle();
//        if (node != null) {
//            return new ScNodeImpl(node);
//        }

        ScNode scNode = createScNode();
        scNode.setName(nodeName);

        return scNode;
    }

    /**
     * Method that create sc node with list of types and name.
     *
     * @param nodeName sc node name
     * @param type sc node types
     * @return created sc node
     */
    @Override
    public ScNode createScNode(String nodeName, String type) {
        if (type == null) {
            throw new IllegalArgumentException("Creation sc node: null type.");
        }
        ScNode scNode = createScNode(nodeName);
        scNode.addType(type);
        return scNode;
    }

    /**
     * Method that create sc node with type and name.
     *
     * @param nodeName sc node name
     * @param types sc node types
     * @return created sc node
     */
    @Override
    public ScNode createScNode(String nodeName, List<String> types) {
        if (types == null) {
            throw new IllegalArgumentException("Creation sc node: null types.");
        }
        ScNode scNode = createScNode(nodeName);
        scNode.addTypes(types);
        return scNode;
    }

    /**
     * Method that create sc arc to sc node.
     *
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScNode endScNode) {
        if (startScNode == null || endScNode == null) {
            throw new IllegalArgumentException("Creation sc arc: null node or type.");
        }

        Node startNode = ((ScNodeImpl) startScNode).getNeo4jNode();
        Node endNode = ((ScNodeImpl) endScNode).getNeo4jNode();

        Node connectorNode = dataBase.createNode();
        connectorNode.setProperty(ScNodeImpl.CONNECTOR_NODE, "true");
        startNode.createRelationshipTo(connectorNode, RelTypes.beginLink);
        connectorNode.createRelationshipTo(endNode, RelTypes.endLink);

        ScArcImpl scArc = new ScArcImpl(connectorNode);

        /* create events */
        ScEventHandler eventHandler = ScEventHandler.getInstance();
        ScEvent event = new ScEvent(ScEventTypes.ATTACH_OUTPUT_TO_NODE, scArc);
        eventHandler.notify(event);
        event = new ScEvent(ScEventTypes.ATTACH_INPUT_TO_NODE, scArc);
        eventHandler.notify(event);

        return scArc;
    }

    /**
     * Method that create sc arc to sc node with type.
     *
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @param type type of sc arc
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScNode endScNode, String type) {
        ScArc newScArc = createScArc(startScNode, endScNode);
        newScArc.addType(type);
        return newScArc;
    }

    /**
     * Method that create sc arc.
     *
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScNode endScNode, List<String> types) {
        if (types == null) {
            throw new IllegalArgumentException("Creation sc arc: null types.");
        }
        ScArc newArc = createScArc(startScNode, endScNode);
        newArc.addTypes(types);
        return newArc;
    }

    /**
     * Method that create sc arc.
     *
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScArc endScArc) {
        if (startScNode == null || endScArc == null) {
            throw new IllegalArgumentException("Creation sc arc: null node or type.");
        }

        Node startNode = ((ScNodeImpl) startScNode).getNeo4jNode();
        Node endNode = ((ScArcImpl) endScArc).getArcConnectorNode();

        Node connectorNode = dataBase.createNode();
        connectorNode.setProperty(ScNodeImpl.CONNECTOR_NODE, "true");
        startNode.createRelationshipTo(connectorNode, RelTypes.beginLink);
        connectorNode.createRelationshipTo(endNode, RelTypes.endLink);


        ScArcImpl scArc = new ScArcImpl(connectorNode);

        /* create events */
        ScEventHandler eventHandler = ScEventHandler.getInstance();
        ScEvent event = new ScEvent(ScEventTypes.ATTACH_OUTPUT_TO_NODE, scArc);
        eventHandler.notify(event);
        event = new ScEvent(ScEventTypes.ATTACH_INPUT_TO_ARC, scArc);
        eventHandler.notify(event);

        return scArc;
    }

    /**
     * Method that create sc arc to sc arc with types.
     *
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @param type type of sc arc
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScArc endScArc, String type) {
        ScArc newScArc = createScArc(startScNode, endScArc);
        newScArc.addType(type);
        return newScArc;
    }

    /**
     * Method that create sc arc to sc arc with types.
     *
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @param types list of types
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScArc endScArc, List<String> types) {
        if (types == null) {
            throw new IllegalArgumentException("Creation sc arc: null types.");
        }
        ScArc newScArc = createScArc(startScNode, endScArc);
        newScArc.addTypes(types);
        return newScArc;
    }

    /**
     * Method that generate sc constrain ( 0->0 ) .
     *
     * @param startNode first node of constrain
     * @param types types of sc arc
     * @param endNode end node of constrain
     * @return generated sc arc
     */
    @Override
    public ScArc generate_3_f_a_f(ScNode startNode, List<String> types, ScNode endNode) {
        if (types == null) {
            throw new IllegalArgumentException("Creation 3_f_a_f : null types.");
        }
        return this.createScArc(startNode, endNode, types);
    }

    /**
     * Method that generate sc constrain ( 0 -> | ) .
     *
     * @param startNode first node of constrain
     * @param types types of sc arc
     * @param endScArc end sc arc of constrain
     * @return generated sc arc
     */
    @Override
    public ScArc generate_3_f_a_f(ScNode startNode, List<String> types, ScArc endScArc) {
        if (types == null) {
            throw new IllegalArgumentException("Creation 3_f_a_f : null types.");
        }
        return this.createScArc(startNode, endScArc, types);
    }

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
    @Override
    public void generate_5_f_a_f_a_f(ScNode firstNode, List<String> types1, ScNode secondNode,
        List<String> types2, ScNode thirdNode) {

        if (types1 == null || types2 == null) {
            throw new IllegalArgumentException("Creation 5_f_a_f_a_f : null types.");
        }

        ScArc scArc = this.createScArc(firstNode, secondNode, types1);
        this.createScArc(thirdNode, scArc, types2);
    }

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
    @Override
    public void generate_5_f_a_f_a_f_1(ScNode firstNode, List<String> types1, ScNode secondNode,
        List<String> types2, ScNode thirdNode) {

        if (types1 == null || types2 == null) {
            throw new IllegalArgumentException("Creation 5_f_a_f_a_f_1 : null types.");
        }

        this.createScArc(firstNode, secondNode, types1);
        this.createScArc(secondNode, thirdNode, types2);
    }

    public void setDataBase(AbstractGraphDatabase dataBase) {
        this.dataBase = dataBase;
    }
}
