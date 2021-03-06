/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.unittests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ostis.sccore.iterators.ScIteratorTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 * @author Q-ANS
 */
public class TypeTest {

    private static ScPerformer performer;

    @BeforeClass
    public static void beforeClass() {
        performer = new ScPerformerImpl("data\\sc_core.db");
        performer.startExecution();
    }

    @AfterClass
    public static void afterClass() {
        performer.finishExecution();
    }

    @Test
    public void testType() {
        List<String> nodeConstType = new ArrayList<String>();
        nodeConstType.add(ScElementTypes.CONST);
        nodeConstType.add(ScElementTypes.NODE);

        List<String> arcPosConstType = new ArrayList<String>();
        arcPosConstType.add(ScElementTypes.ARC);
        arcPosConstType.add(ScElementTypes.CONST);
        arcPosConstType.add(ScElementTypes.POS);

        ScFactory factory = performer.getScFactory();
        ScNode node = factory.createScNode("first", nodeConstType);

        ScNode attr = factory.createScNode("attr", nodeConstType);

        for (int i = 0; i < 100; i++) {
            ScNode tmp = factory.createScNode(Integer.toString(i),nodeConstType);

            factory.generate_5_f_a_f_a_f(node, arcPosConstType, tmp, arcPosConstType, attr);
        }
        performer.successExecution();

        Iterator iterator = performer.createIterator(ScIteratorTypes.F_A_A_A_F, node, arcPosConstType, nodeConstType, arcPosConstType, attr);

    }
}
