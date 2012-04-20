package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.scperformer.DataBaseManager;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Class that implement sc arc.
 *
 * @author yaskoam
 */
public class ScArcImpl extends ScArc {

    private Node connectorNode;

    /**
     * Constructor for object.
     *
     * @param connectorNode connector node of arc
     */
    public ScArcImpl(Node connectorNode) {
        this.connectorNode = connectorNode;
    }

    /**
     * Method that returns true if element is arc and false if not.
     *
     * @return true if is arc
     */
    @Override
    public boolean isScArc() {
        return true;
    }

    /**
     * Method that returns true if element is connector and false if not.
     *
     * @return true if is connector
     */
    @Override
    public boolean isScNode() {
        return false;
    }

    /**
     * Method that get address of sc element in memory.
     *
     * @return address of element
     */
    @Override
    public long getAddress() {
        return connectorNode.getId();
    }

    /**
     * Method that sets type of sc arc.
     *
     * @param type type of element
     */
    @Override
    public void addType(String type) {
        DataBaseManager dataBaseManager = DataBaseManager.getDataBaseManagerInstance();
        Node typeNode = dataBaseManager.getTypeNode(type);

        if (typeNode != null) {
            typeNode.createRelationshipTo(connectorNode, RelTypes.typeLink);
        }
    }

    /**
     * Method that set types of sc element.
     *
     * @param types list of types name
     */
    @Override
    public void addTypes(List<String> types) {
        for (String currentType : types) {
            this.addType(currentType);
        }
    }

    /**
     * Method that get all types of sc element.
     *
     * @return list of types
     */
    @Override
    public List<String> getTypes() {
        List<String> nodeTypes = new ArrayList<String>();

        ExecutionEngine engine = new ExecutionEngine(DataBaseManager.getDataBaseManagerInstance().getDataBase());
        ExecutionResult result = engine.execute(
            "START node=node(" + this.getAddress() + ") "
                + "MATCH node<-[:typeLink]-type "
                + "RETURN type ");

        Iterator<Map<String, Object>> resultIterator = result.iterator();

        while (resultIterator.hasNext()) {
            Node node = (Node) resultIterator.next().get("type");
            nodeTypes.add((String) node.getProperty(ScElementTypes.ELEMENT_TYPE_PROPERTY));
        }

        return nodeTypes;
    }

    /**
     * Method that remove type from sc element.
     *
     * @param type name of type
     */
    @Override
    public void removeType(String type) {
        ExecutionEngine engine = new ExecutionEngine(DataBaseManager.getDataBaseManagerInstance().getDataBase());
        ExecutionResult result = engine.execute(
            "START node=node(" + this.getAddress() + ") "
                + "MATCH node<-[relationship:typeLink]-type "
                + "WHERE type." + ScElementTypes.ELEMENT_TYPE_PROPERTY + "=" + type + " "
                + "RETURN relationship ");

        Iterator<Map<String, Object>> resultIterator = result.iterator();

        while (resultIterator.hasNext()) {
            Relationship relationship = (Relationship) resultIterator.next().get("relationship");
            relationship.delete();
        }
    }

    /**
     * Gets start sc connector of sc arc.
     * If start element of sc arc is another sc arc, then return null.
     *
     * @return founded sc connector
     */
    @Override
    public ScNode getStartScNode() {
        Node startNode = getBeginLink().getStartNode();
        return new ScNodeImpl(startNode);
    }

    /**
     * Method that get end sc connector of sc arc.
     * If end element of sc arc is another sc arc, then return null.
     *
     * @return founded sc node
     */
    @Override
    public ScNode getEndScNode() {
        Node endNode = getEndLink().getEndNode();
        if (endNode.hasProperty(ScNodeImpl.CONNECTOR_NODE)) {
            return null;
        }

        return new ScNodeImpl(endNode);
    }

    /**
     * Method that get end sc arc of sc arc.
     * If end element of sc arc isn't sc arc, then return null.
     *
     * @return founded sc arc
     */
    @Override
    public ScArc getEndScArc() {
        Node connector = getEndLink().getEndNode();
        if (!connector.hasProperty(ScNodeImpl.CONNECTOR_NODE)) {
            return null;
        }

        return new ScArcImpl(connector);
    }

    /**
     * Method that get all input sc arcs.
     *
     * @return all input sc arcs
     */
    @Override
    public List<ScArc> getAllInputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> endRelations = connectorNode.getRelationships(RelTypes.endLink, Direction.INCOMING);

        for (Relationship currentRelationship : endRelations) {
            Node connector = currentRelationship.getStartNode();
            scArcsList.add(new ScArcImpl(connector));
        }

        return scArcsList;
    }

    /**
     * Gets connector that used like arc.
     *
     * @return connector-connector
     */
    public Node getArcConnectorNode() {
        return this.connectorNode;
    }

    public Relationship getBeginLink() {
        return connectorNode.getSingleRelationship(RelTypes.beginLink, Direction.INCOMING);
    }

    public Relationship getEndLink() {
        return connectorNode.getSingleRelationship(RelTypes.endLink, Direction.OUTGOING);
    }
}
