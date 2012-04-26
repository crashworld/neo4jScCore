package net.ostis.sccore.samples.graph;

import java.util.ArrayList;
import java.util.List;

import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;

/**
 * @author yaskoam
 */
public class CreateSCGraphSample {
    public static void main(String[] args) {

        ScPerformer performer = new ScPerformerImpl("data\\sc_core.db");
        String graphName = "firstGraph";

        //Vertexes
        List<String> vertexList = new ArrayList<String>();
        vertexList.add("A");
        vertexList.add("B");
        vertexList.add("C");
        vertexList.add("D");
        vertexList.add("G");
        vertexList.add("E");
        vertexList.add("F");

        //Edges
        List<String> edgeList = new ArrayList<String>();
        edgeList.add("A-B");
        edgeList.add("B-C");
        edgeList.add("B-D");
        edgeList.add("C-E");
        edgeList.add("D-G");
        edgeList.add("G-E");
        edgeList.add("E-A");
        edgeList.add("F-E");
        edgeList.add("F-B");

        try {
            performer.startExecution();

            ScNode graphNode = performer.findScNodeByName(graphName);
            if (graphNode == null) {
                graphNode = GraphManager.createUndirectedGraph(performer, graphName, vertexList, edgeList);
            }

            GraphManager.printUndirectedGraph(performer, graphNode);
        }
        finally {
            performer.finishExecution();
        }
    }
}
