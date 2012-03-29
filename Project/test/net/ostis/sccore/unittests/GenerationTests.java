package net.ostis.sccore.unittests;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.types.ScArcTypes;
import net.ostis.sccore.types.ScNodeTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author yaskoam
 */
public class GenerationTests {
    private static ScPerformer performer;

    @BeforeClass
    public static void beforeClass() {
        ScPerformer newPerformer = new ScPerformer("D:\\data\\sc_core.db");
        performer = newPerformer;
        performer.beginExecution();
    }

    @AfterClass
    public static void afterClass() {
        performer.finishExecution();
    }

    @Test
    public void test_3_f_a_f_node() {
            ScFactory factory = performer.getScFactory();
            //Generate 0 -> 0
            ScNode firstNode = factory.createScNode("first3", ScNodeTypes.CONST);
            ScNode secondNode = factory.createScNode("second3", ScNodeTypes.CONST);
            ScArc firstArc = factory.generate_3_f_a_f(firstNode, ScArcTypes.CONST, secondNode);
    }

    @Test
    public void test_3_f_a_f_arc() {
        //Generate 0 -> |
        ScFactory factory = performer.getScFactory();
        ScNode firstNode = performer.findScNodeByName("first3");

        ScNode attrNode = factory.createScNode("attr3", ScNodeTypes.CONST);
        ScArc secondArc = factory.generate_3_f_a_f(attrNode, ScArcTypes.CONST, firstNode.getAllOutputScArcs().get(0));
    }

    @Test
    public void test_5_f_a_f_a_f() {
        //Generate
        //        0
        //        |
        //      0 -> 0
        ScFactory factory = performer.getScFactory();
        ScNode firstNode = factory.createScNode("first5", ScNodeTypes.CONST);
        ScNode secondNode = factory.createScNode("second5", ScNodeTypes.CONST);
        ScNode attr = factory.createScNode("attr5", ScNodeTypes.CONST);
        factory.generate_5_f_a_f_a_f(firstNode, ScArcTypes.CONST, secondNode, ScArcTypes.CONST, firstNode);
    }

    @Test
    public void test_5_f_a_f_a_f_1() {
        //Generate 0 -> 0 -> 0
        ScFactory factory = performer.getScFactory();
        ScNode firstNode = factory.createScNode("first5_1", ScNodeTypes.CONST);
        ScNode secondNode = factory.createScNode("second5_1", ScNodeTypes.CONST);
        ScNode attr = factory.createScNode("attr5_1", ScNodeTypes.CONST);
        factory.generate_5_f_a_f_a_f(firstNode, ScArcTypes.CONST, secondNode, ScArcTypes.CONST, firstNode);
    }
}
