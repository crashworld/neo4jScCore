/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.unittests.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ostis.sccore.iterators.ScConstraint;
import net.ostis.sccore.iterators.ScIteratorTypes;
import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;
import net.ostis.sccore.types.ScElementTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Q-ANS
 */
public class a_a_f {

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
    public void test_a_a_f() {
        List<String> nodeConstType = new ArrayList<String>();
        nodeConstType.add(ScElementTypes.CONST);
        nodeConstType.add(ScElementTypes.NODE);

        List<String> arcPosConstType = new ArrayList<String>();
        arcPosConstType.add(ScElementTypes.ARC);
        arcPosConstType.add(ScElementTypes.CONST);
        arcPosConstType.add(ScElementTypes.POS);

        ScFactory factory = performer.getScFactory();
        ScNode node = factory.createScNode("first", nodeConstType);
        ScNode node2 = factory.createScNode("second", nodeConstType);
        ScArc arc = factory.createScArc(node, node2, arcPosConstType);
        ScNode attr = factory.createScNode("attr", nodeConstType);

        for (int i = 0; i < 2; i++) {
            ScNode tmp = factory.createScNode("", nodeConstType);
            factory.generate_3_f_a_f(tmp, arcPosConstType, node);
        }

        Iterator iterator = performer.createIterator(ScIteratorTypes.A_A_F, nodeConstType, arcPosConstType, node);

        while (iterator.hasNext()) {
            ScConstraint constr = (ScConstraint) iterator.next();
            System.out.println(constr.getElement(1).getAddress() + "->" + constr.getElement(2).getAddress() + "->" + constr.getElement(3).getAddress());

            System.out.println(constr.getElement(1).getTypes().toString());
        }

    }
}
