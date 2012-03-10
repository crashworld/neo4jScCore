package net.ostis.sccore.scfactory;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScArcImpl;
import net.ostis.sccore.scelements.ScNode;

import net.ostis.sccore.scelements.ScNodeImpl;

import net.ostis.sccore.scevents.ScEvent;
import net.ostis.sccore.scevents.ScEventHandler;
import net.ostis.sccore.scevents.ScEventTypes;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.AutoIndexer;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.kernel.AbstractGraphDatabase;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 15:08
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
     * @return factory object
     */
    public static ScFactoryImpl getInstance() {
        if (factory == null) {
            factory = new ScFactoryImpl();
        }

        return factory;
    }

    /**
     * Method that create sc node.
     * @param nodeName sc node name
     * @param nodeType sc node type
     * @return created sc node
     */
    @Override
    public ScNode createScNode(String nodeName, String nodeType) {
        if (nodeName == null || nodeType == null) {
            throw new NullPointerException("Creation sc node: null name or type.");
        }

        AutoIndexer<Node> autoIndexer = dataBase.index().getNodeAutoIndexer();
        ReadableIndex<Node> index = autoIndexer.getAutoIndex();
        Node node = index.get(ScNode.SC_NODE_NAME_PROPERTY, nodeName).getSingle();
        if (node != null) {
            return new ScNodeImpl(node);
        }

        node = dataBase.createNode();
        node.setProperty(ScNode.SC_NODE_NAME_PROPERTY, nodeName);
        node.setProperty(ScNode.SC_NODE_TYPE_PROPERTY, nodeType);
        ScNodeImpl scNode = new ScNodeImpl(node);

        /* create event */
        ScEventHandler eventHandler = ScEventHandler.getInstance();
        ScEvent event = new ScEvent(ScEventTypes.CREATE_SC_NODE, scNode);
        eventHandler.notify(event);

        return scNode;
    }

    /**
     * Method that create sc arc.
     * @param startScNode start sc node of sc arc
     * @param endScNode end sc node of sc arc
     * @param type sc arc type
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScNode endScNode, String type) {
        if (startScNode == null || endScNode == null || type == null) {
            throw new NullPointerException("Creation sc arc: null node or type.");
        }

        Node connectedNode = dataBase.createNode();
        /* set arc type on connected node of sc arc */
        connectedNode.setProperty(ScArc.SC_ARC_TYPE_PROPERTY, type);

        Node startNode = ((ScNodeImpl) startScNode).getNeo4jNode();
        Node endNode = ((ScNodeImpl) endScNode).getNeo4jNode();
        Relationship beginLink = startNode.createRelationshipTo(connectedNode, RelType.Link);
        Relationship endLink = connectedNode.createRelationshipTo(endNode, RelType.Link);
        ScArcImpl scArc = new ScArcImpl(beginLink, connectedNode, endLink);

        /* create events */
        ScEventHandler eventHandler = ScEventHandler.getInstance();
        ScEvent event = new ScEvent(ScEventTypes.ATTACH_INPUT_TO_NODE, scArc);
        eventHandler.notify(event);
        event = new ScEvent(ScEventTypes.ATTACH_OUTPUT_TO_NODE, scArc);
        eventHandler.notify(event);

        return scArc;
    }


    /**
     * Method that create sc arc.
     * @param startScNode start sc node of sc arc
     * @param endScArc end sc arc
     * @param type sc arc type
     * @return created sc arc
     */
    @Override
    public ScArc createScArc(ScNode startScNode, ScArc endScArc, String type) {
        if (startScNode == null || endScArc == null || type == null) {
            throw new NullPointerException("Creation sc arc: null node or type.");
        }

        Node connectedNode = dataBase.createNode();
        /* set arc type on connected node of sc arc */
        connectedNode.setProperty(ScArc.SC_ARC_TYPE_PROPERTY, type);

        Node startNode = ((ScNodeImpl) startScNode).getNeo4jNode();
        Node endNode = ((ScArcImpl) endScArc).getConnectedNode();
        Relationship beginLink = startNode.createRelationshipTo(connectedNode, RelType.Link);
        Relationship endLink = connectedNode.createRelationshipTo(endNode, RelType.Link);
        ScArcImpl scArc = new ScArcImpl(beginLink, connectedNode, endLink);

        /* create events */
        ScEventHandler eventHandler = ScEventHandler.getInstance();
        ScEvent event = new ScEvent(ScEventTypes.ATTACH_INPUT_TO_NODE, scArc);
        eventHandler.notify(event);
        event = new ScEvent(ScEventTypes.ATTACH_OUTPUT_TO_NODE, scArc);
        eventHandler.notify(event);

        return new ScArcImpl(beginLink, connectedNode, endLink);

    }

    /**
     * Method that generate sc constrain ( 0->0 )
     * @param startNode first node of constrain
     * @param type type of sc arc 
     * @param endNode end node of constrain
     * @return generated sc arc
     */
    @Override
    public ScArc generate_3_f_a_f(ScNode startNode, String type, ScNode endNode) {
        ScArc generatedScArc = this.createScArc(startNode, endNode, type);
        return generatedScArc;
    }

    /**
     * Method that generate sc constrain ( 0 -> | )
     * @param startNode first node of constrain
     * @param type type of sc arc
     * @param endArc end sc arc of constrain
     * @return generated sc arc
     */
    @Override
    public ScArc generate_3_f_a_f(ScNode startNode, String type, ScArc endScArc) {
        ScArc generatedScArc = this.createScArc(startNode, endScArc, type);
        return generatedScArc;
    }


    /**
     * Method that generate sc constrain
     *    0
     * 0->|
     *    0
     * @param firstNode first node of constrain
     * @param firstType type of first generated sc arc of constrain
     * @param secondNode second node of constrain
     * @param secondType type of second generated sc arc of constrain
     * @param thirdNode third node of constrain
     */
    @Override
    public void generate_5_f_a_f_a_f_1(ScNode firstNode, String firstType, ScNode secondNode,
        String secondType, ScNode thirdNode) {

        ScArc scArc = this.createScArc(firstNode, secondNode, firstType);
        this.createScArc(thirdNode, scArc, secondType);
    }

    /**
     * Method that generate sc constrain
     * 0->0->0
     * @param firstNode first node of constrain
     * @param firstType type of first generated sc arc of constrain
     * @param secondNode second node of constrain
     * @param secondType type of second generated sc arc of constrain
     * @param thirdNode third node of constrain
     */
    @Override
    public void generate_5_f_a_f_a_f_2(ScNode firstNode, String firstType, ScNode secondNode,
        String secondType, ScNode thirdNode) {

       this.createScArc(firstNode, secondNode, firstType);
       this.createScArc(secondNode, thirdNode, secondType);
    }

    public void setDataBase(AbstractGraphDatabase dataBase) {
        this.dataBase = dataBase;
    }

    private static enum RelType implements RelationshipType {
        Link
    }
}
