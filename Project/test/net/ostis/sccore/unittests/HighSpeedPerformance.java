package net.ostis.sccore.unittests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 * @author yaskoam
 */
public class HighSpeedPerformance {

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
    public void addScNodes50000() {
        ScFactory factory = performer.getScFactory();
        for (int i = 0; i < 50000; i++) {
            factory.createScNode(Integer.toString(i), ScElementTypes.NODE);
            performer.successExecution();
        }
    }
}
