/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.unittests;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.ostis.sccore.samples.graph.GraphManager;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;

/**
 * @author yaskoam
 */
public class GraphTest {
    private static ScPerformer performer;

    @BeforeClass
    public static void beforeClass() {
        ScPerformer newPerformer = new ScPerformerImpl("data\\sc_core.db");
        performer = newPerformer;
        performer.startExecution();
    }

    @AfterClass
    public static void afterClass() {
        performer.finishExecution();
    }

    @Test
    public void testUndirectedGraph() {
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
