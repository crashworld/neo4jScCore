package net.ostis.sccore.samples.graph;

import java.util.ArrayList;
import java.util.Iterator;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.types.ScElementTypes;

import java.util.List;
import net.ostis.sccore.iterators.ScConstraint;

/**
 *
 * @author yaskoam
 */
public class GraphManager {
    public static final List<ScElementTypes> constNodeTypes = new ArrayList<ScElementTypes>();
    public static final List<ScElementTypes> posConstArcTypes = new ArrayList<ScElementTypes>();

    public static final String VERTEX_ATTR_NAME = "vertex";
    public static final String EDGE_ATTR_NAME = "edge";

    static {
        constNodeTypes.add(ScElementTypes.NODE);
        constNodeTypes.add(ScElementTypes.CONST);

        posConstArcTypes.add(ScElementTypes.ARC);
        posConstArcTypes.add(ScElementTypes.POS);
        posConstArcTypes.add(ScElementTypes.CONST);
    }

    /**
     * Method that create undirected graph.
     *
     * @param performer object
     * @param graphName graph name
     * @param graphNodes list of graph nodes name
     * @param graphEdges list of string that
     * presented connection between nodes.
     * for example "A-B"
     * @return node of created graph
     */
    public static ScNode createUndirectedGraph(ScPerformer performer, String graphName,
        List<String> graphVertex, List<String> graphEdges) {

        ScFactory factory = performer.getScFactory();

        //Create key nodes
        ScNode graphNode = factory.createScNode(graphName, constNodeTypes);
        ScNode vertexAttr = factory.createScNode(VERTEX_ATTR_NAME, constNodeTypes);
        ScNode edgeAttr = factory.createScNode(EDGE_ATTR_NAME, constNodeTypes);

        //Create graph nodes
        for(String currentVertex : graphVertex) {
            ScNode scNode = factory.createScNode(currentVertex, constNodeTypes);
            factory.generate_5_f_a_f_a_f(graphNode, posConstArcTypes, scNode, posConstArcTypes, vertexAttr);
        }

        for (String currentEdge : graphEdges) {
            String[] twoVertex = currentEdge.split("-");
            ScNode firstVertex = performer.findScNodeByName(twoVertex[0]);
            ScNode secondVertex = performer.findScNodeByName(twoVertex[1]);
            ScNode edgeNode = factory.createScNode();
            factory.generate_5_f_a_f_a_f(graphNode, posConstArcTypes, edgeNode, posConstArcTypes, edgeAttr);
            factory.generate_3_f_a_f(edgeNode, posConstArcTypes, firstVertex);
            factory.generate_3_f_a_f(edgeNode, posConstArcTypes, secondVertex);
        }

        return graphNode;
    }

    public static void printUndirectedGraph(ScPerformer performer, ScNode graphNode) {
        //Find all vertexes
        ScNode vertexAttr = performer.findScNodeByName(VERTEX_ATTR_NAME);
        Iterator vertexIterator = performer.createIterator_5_f_a_a_a_f(graphNode, posConstArcTypes, constNodeTypes,
            posConstArcTypes, vertexAttr);

        System.out.println("All graph vertexes: ");
        while(vertexIterator.hasNext()) {
            ScConstraint constraint = (ScConstraint) vertexIterator.next();
            System.out.println(((ScNode)constraint.getElement(3)).getName() + " ");
        }

        //Find all edges
        System.out.println("All graph edges: ");
        ScNode edgeAttr = performer.findScNodeByName(EDGE_ATTR_NAME);
        Iterator edgeIterator = performer.createIterator_5_f_a_a_a_f(graphNode, posConstArcTypes,
            new ArrayList<ScElementTypes>(), posConstArcTypes, edgeAttr);

        while(edgeIterator.hasNext()) {
            ScConstraint constraint = (ScConstraint) edgeIterator.next();
            ScNode edgeNode = (ScNode) constraint.getElement(3);
            Iterator incidentIterator = performer.createIterator_3_f_a_a(edgeNode, posConstArcTypes, constNodeTypes);

            ScConstraint constraint1 = (ScConstraint)incidentIterator.next();
            ScConstraint constraint2 = (ScConstraint) incidentIterator.next();

             System.out.print(((ScNode)constraint1.getElement(3)).getName());
             System.out.print("--");
             System.out.print(((ScNode)constraint2.getElement(3)).getName());
             System.out.println();
        }
    }

    public static void deleteUndirectedGraph(ScPerformer performer, ScNode graphNode) {
        Iterator iterator = performer.createIterator_3_f_a_a(graphNode, new ArrayList<ScElementTypes>(),
                new ArrayList<ScElementTypes>());

        while(iterator.hasNext()) {
            ScNode currentGraphElement = (ScNode) ((ScConstraint) iterator.next()).getElement(3);
            performer.deleteScNode(currentGraphElement);
        }

        performer.deleteScNode(graphNode);
    }
}
