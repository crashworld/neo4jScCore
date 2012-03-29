package net.ostis.sccore.unittests;

import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.types.ScNodeTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author yaskoam
 */
public class HighSpeedPerformance {
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
    public void addScNodes50000() {
        ScFactory factory = performer.getScFactory();
        for (int i = 0; i < 50000; i++) {
            factory.createScNode(Integer.toString(i), ScNodeTypes.CONST);
        }
    }
}
