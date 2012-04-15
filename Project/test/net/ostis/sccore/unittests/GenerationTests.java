package net.ostis.sccore.unittests;

import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 *
 * @author yaskoam
 */
public class GenerationTests {
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
    public void test_3_f_a_f_node() {
        ScFactory factory = performer.getScFactory();
        //Generate 0 -> 0
        ScNode firstNode = factory.createScNode("first3", ScElementTypes.NODE);
        ScNode secondNode = factory.createScNode("second3", ScElementTypes.NODE);
        List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
        typeList.add(ScElementTypes.ARC);
        ScArc firstArc = factory.generate_3_f_a_f(firstNode, typeList, secondNode);
    }

    @Test
    public void test_3_f_a_f_arc() {
        //Generate 0 -> |
        ScFactory factory = performer.getScFactory();
        ScNode firstNode = performer.findScNodeByName("first3");

        ScNode attrNode = factory.createScNode("attr3", ScElementTypes.NODE);

        List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
        typeList.add(ScElementTypes.ARC);

        ScArc secondArc = factory.generate_3_f_a_f(attrNode, typeList, firstNode.getAllOutputScArcs().get(0));
    }

    @Test
    public void test_5_f_a_f_a_f() {
        //Generate
        //        0
        //        |
        //      0 -> 0
        ScFactory factory = performer.getScFactory();
        ScNode firstNode = factory.createScNode("first5", ScElementTypes.NODE);
        ScNode secondNode = factory.createScNode("second5", ScElementTypes.NODE);
        ScNode attr = factory.createScNode("attr5", ScElementTypes.NODE);
        List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
        typeList.add(ScElementTypes.ARC);
        factory.generate_5_f_a_f_a_f(firstNode,  typeList, secondNode,  typeList, firstNode);
    }

    @Test
    public void test_5_f_a_f_a_f_1() {
        //Generate 0 -> 0 -> 0
        ScFactory factory = performer.getScFactory();
        ScNode firstNode = factory.createScNode("first5_1", ScElementTypes.NODE);
        ScNode secondNode = factory.createScNode("second5_1", ScElementTypes.NODE);
        ScNode attr = factory.createScNode("attr5_1", ScElementTypes.NODE);
        List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
        typeList.add(ScElementTypes.ARC);
        factory.generate_5_f_a_f_a_f(firstNode, typeList, secondNode, typeList, firstNode);
    }
}
