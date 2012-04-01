package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Class that implement sc arc.
 * 
 * @author yaskoam
 */
public class ScArcImpl extends ScArc {

    private Node connectorNode;

    private List<ScElementTypes> arcTypes;
    /**
     * Constructor for object.
     *
     * @param connectorNode connector node of arc
     */
    public ScArcImpl(Node connectorNode) {
        this.connectorNode = connectorNode;
        this.arcTypes = new ArrayList<ScElementTypes>();
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
    public void addType(ScElementTypes type) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if(arcTypes.isEmpty() && !arcTypes.contains(type))
            arcTypes.add(type);
    }

    /**
     * Method that set types of sc element.
     *
     * @param types list of types name
     */
    @Override
    public void addTypes(List<ScElementTypes> types) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if(arcTypes.isEmpty())
            arcTypes = types;
        else
            for(ScElementTypes type : types )
            {
             if(!arcTypes.contains(type))
                arcTypes.add(type);                  
            }
    }

    /**
     * Method that get all types of sc elemetn.
     *
     * @return list of types
     */
    @Override
    public List<ScElementTypes> getTypes() {
        return arcTypes;
        //throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * Method that remove type from sc element.
     *
     * @param type name of type
     */
    @Override
    public void removeType(ScElementTypes type) {
        if(arcTypes.contains(type))
                arcTypes.remove(type);
        //throw new UnsupportedOperationException("Not supported yet.");
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
        if (endNode.hasProperty(ScNodeImpl.CONNECTORNODE)) {
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
        if (!connector.hasProperty(ScNodeImpl.CONNECTORNODE)) {
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
