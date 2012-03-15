package net.ostis.sccore.scperformer;

import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
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
import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scfactory.ScFactoryImpl;

/**
 * Class that provide all general actions with sc memory.
 * @author yaskoam
 */
public class ScPerformer {
    private AbstractGraphDatabase dataBase;
    private Transaction transaction;

    /**
     * Construct object with the data base path.
     * @param basePath path
     */
    public ScPerformer(String basePath) {
        dataBase = DataBaseConnector.getDataBaseInstance(basePath);
        ScFactoryImpl factory = ScFactoryImpl.getInstance();
        factory.setDataBase(dataBase);
    }

    /**
     * Method that return sc factory object for further working.
     * @return sc factory object
     */
    public ScFactory getScFactory() {
        return ScFactoryImpl.getInstance();
    }

    /**
     * Method start data base transaction for execute different operation.
     *
     */
    public void beginExecution() {
        transaction = dataBase.beginTx();
    }

    /**
     * Method that finish transaction.
     * 
     */
    public void finishExecution() {
        transaction.success();
        transaction.finish();
    }

    /**
     * Method that find sc node by name in memory.
     * @param nodeName name of node
     * @return founded node
     */
    public ScNode findScNodeByName(String nodeName) {
        AutoIndexer<Node> autoIndexer = dataBase.index().getNodeAutoIndexer();
        ReadableIndex<Node> index = autoIndexer.getAutoIndex();
        Node node = index.get(ScNode.SC_NODE_NAME_PROPERTY, nodeName).getSingle();
        return new ScNodeImpl(node);
    }

    /**
     * Method that remove sc arc. When it was deleted, connected with it sc arcs was deleted too.
     * @param arc deleted sc arc
     */
    public void deleteScArc(ScArc arc) {
        ScArcImpl arcImpl = (ScArcImpl)arc;
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
            beginRelationship = endNode.getSingleRelationship(RelTypes.beginLink, Direction.INCOMING);
            endRelationship = endNode.getSingleRelationship(RelTypes.endLink, Direction.OUTGOING);
            ScArcImpl newScArc = new ScArcImpl(beginRelationship, endNode, endRelationship);
            event = new ScEvent(ScEventTypes.DETACH_INPUT_FROM_ARC, newScArc);
        } else {
            event = new ScEvent(ScEventTypes.DETACH_INPUT_FROM_NODE, new ScNodeImpl(endNode));
        }

        ScEventHandler.getInstance().notify(event);

        arc = null;
    }

    /**
     * Method that remove sc node. When it was deleted, connected with it sc arcs was deleted too.
     * @param scNode deleted sc node
     */
    public void deleteScNode(ScNode scNode) {
        ScNodeImpl scNodeImpl = (ScNodeImpl)scNode;
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
}