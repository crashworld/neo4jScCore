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

import net.ostis.sccore.contents.Content;
import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.scperformer.DataBaseManager;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Class that implement SC node.
 *
 * @author yaskoam
 */
public class ScNodeImpl extends ScNode {

    /**
     * String constant for connector node of sc arc.
     */
    public static final String CONNECTOR_NODE = "_connectorNode";
    /**
     * String constant for name of node attribute.
     */
    public static final String SC_NODE_NAME_PROPERTY = "_scNodeName";

    private static final String CONTENT = "_content";
    private static final String CONTENT_TYPE = "_contentType";

    private Node neo4jNode;

    /**
     * Construct object.
     *
     * @param node neo4jNode
     */
    public ScNodeImpl(Node node) {
        this.neo4jNode = node;
    }

    /**
     * Method that returns true if element is arc and false if not.
     *
     * @return true if is arc
     */
    @Override
    public boolean isScArc() {
        return false;
    }

    /**
     * Method that returns true if element is node and false if not.
     *
     * @return true if is node
     */
    @Override
    public boolean isScNode() {
        return true;
    }

    /**
     * Method that get address of sc element in memory.
     *
     * @return address of element
     */
    @Override
    public long getAddress() {
        return neo4jNode.getId();
    }

    /**
     * Method that get name of sc node.
     *
     * @return name of sc node
     */
    @Override
    public String getName() {
        return (String) neo4jNode.getProperty(SC_NODE_NAME_PROPERTY);
    }

    /**
     * Method that set name for sc node.
     *
     * @param name node name
     */
    @Override
    public void setName(String name) {
        neo4jNode.setProperty(ScNodeImpl.SC_NODE_NAME_PROPERTY, name);
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
            typeNode.createRelationshipTo(neo4jNode, RelTypes.typeLink);
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
                + "WHERE type." + ScElementTypes.ELEMENT_TYPE_PROPERTY + "=\"" + type + "\" "
                + "RETURN relationship ");

        Iterator<Map<String, Object>> resultIterator = result.iterator();

        while (resultIterator.hasNext()) {
            Relationship relationship = (Relationship) resultIterator.next().get("relationship");
            relationship.delete();
        }
    }

    /**
     * Method that remove all types from sc node.
     */
    @Override
    public void removeAllTypes() {
        for (String currentType : this.getTypes()) {
            this.removeType(currentType);
        }
    }

    /**
     * Method that get all input sc arcs to sc node.
     *
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllInputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> endRelations = neo4jNode.getRelationships(RelTypes.endLink, Direction.INCOMING);

        for (Relationship endRelationship : endRelations) {
            Node connector = endRelationship.getStartNode();
            scArcsList.add(new ScArcImpl(connector));
        }

        return scArcsList;
    }

    /**
     * Method that get all output sc arcs from sc node.
     *
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllOutputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> beginRelations = neo4jNode.getRelationships(RelTypes.beginLink, Direction.OUTGOING);

        for (Relationship startRelationship : beginRelations) {
            Node connector = startRelationship.getEndNode();
            scArcsList.add(new ScArcImpl(connector));
        }

        return scArcsList;
    }

    /**
     * Method that get all sc arcs connected with node.
     *
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllScArc() {
        List<ScArc> scArcsList = this.getAllInputScArcs();
        scArcsList.addAll(this.getAllOutputScArcs());
        return scArcsList;
    }

    /**
     * Method that get content from node.
     *
     * @return content object
     */
    @Override
    public Content getContent() {
        if (!neo4jNode.hasProperty(ScNodeImpl.CONTENT_TYPE)) {
            return null;
        }

        Content content = new Content();
        content.setContent(neo4jNode.getProperty(ScNodeImpl.CONTENT));
        Content.ContentTypes contentTypes = Content.ContentTypes.getTypeByName((String) neo4jNode.getProperty(CONTENT_TYPE));
        content.setContentType(contentTypes);

        return content;
    }

    /**
     * Method that set content in node.
     *
     * @param content object
     */
    @Override
    public void setContent(Content content) {
        neo4jNode.setProperty(ScNodeImpl.CONTENT, content.getContent());
        neo4jNode.setProperty(ScNodeImpl.CONTENT_TYPE, content.getContentType().toString());
    }

    public Node getNeo4jNode() {
        return this.neo4jNode;
    }
}
