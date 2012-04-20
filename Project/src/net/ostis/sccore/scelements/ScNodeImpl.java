package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.contents.Content;
import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scfactory.ScFactoryImpl;
import net.ostis.sccore.types.ScElementTypes;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.AbstractGraphDatabase;

/**
 * Class that implement SC node.
 * 
 * @author yaskoam
 */
public class ScNodeImpl extends ScNode {

    /** String constant for connector node of sc arc. */
    public static final String CONNECTORNODE = "_connectorNode";
    private Node neo4jNode;
    private Content nodeContent;

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
        neo4jNode.setProperty(ScNode.SC_NODE_NAME_PROPERTY, name);
    }

    /**
     * Method that sets type of sc arc.
     *
     * @param type type of element
     */
    @Override
    public void addType(ScElementTypes type) {
        ScFactoryImpl factory = ScFactoryImpl.getInstance();
        AbstractGraphDatabase dataBase = factory.getDataBase();
        IndexManager index = dataBase.index();
        Node node = index.forNodes(ScNode.Sc_ELEMENT_TYPE).get(ScNode.Sc_ELEMENT_TYPE, type.toString()).getSingle();
        if (node != null) {
            node.createRelationshipTo(neo4jNode, RelTypes.typeLink);
        }
        //factory.createScArc(new ScNodeImpl(node), this);


        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that set types of sc element.
     *
     * @param types list of types name
     */
    @Override
    public void addTypes(List<ScElementTypes> types) {
        for (ScElementTypes type : types) {
            this.addType(type);
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that get all types of sc elemetn.
     *
     * @return list of types
     */
    @Override
    public List<ScElementTypes> getTypes() {
        ScElementTypes[] scTypes = ScElementTypes.values();
        List<ScElementTypes> scCurrentTypes = new ArrayList<ScElementTypes>();
        List<ScArc> scArcsList = this.getAllInputScArcs();
        ScFactoryImpl factory = ScFactoryImpl.getInstance();
        AbstractGraphDatabase dataBase = factory.getDataBase();
        IndexManager index = dataBase.index();

        for (ScArc arc : scArcsList) {
            ScNode node = arc.getStartScNode();

            if (index.forNodes(ScNode.Sc_ELEMENT_TYPE).get(ScNode.Sc_ELEMENT_TYPE, node.getName()).getSingle() != null) {
                scCurrentTypes.add(ScElementTypes.valueOf(node.getName()));
            }
        }

        return scCurrentTypes;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that remove type from sc element.
     *
     * @param type name of type
     */
    @Override
    public void removeType(ScElementTypes type) {
        ScFactory factory = ScFactoryImpl.getInstance();

        ////////////////////////////////////////////////////////////////////////////
        ScNode node = new ScNodeImpl(neo4jNode);
        factory.createScArc(this, node, type);
        //throw new UnsupportedOperationException("Not supported yet.");
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

    public Node getNeo4jNode() {
        return this.neo4jNode;
    }
}
