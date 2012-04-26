package net.ostis.sccore.unittests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import net.ostis.sccore.contents.Content;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 * @author Q-YAA
 */
public class ContentTests {
    private static ScPerformer performer;
    private static ScFactory factory;

    @BeforeClass
    public static void beforeClass() {
        performer = new ScPerformerImpl("data\\sc_core.db");
        factory = performer.getScFactory();
        performer.startExecution();
    }

    @AfterClass
    public static void afterClass() {
        performer.finishExecution();
    }

    @Test
    public void testIntContent() {
        try {
            //performer.startExecution();
            ScNode node = factory.createScNode("node", ScElementTypes.NODE);
            Content content = new Content(123);
            node.setContent(content);

            content = node.getContent();
            System.out.println(content.getIntContent());

            performer.deleteScNode(node);
        }
        finally {
            //performer.finishExecution();
        }

    }

    @Test
    public void testStringContent() {
        try {
            //performer.startExecution();
            ScNode node = factory.createScNode("node", ScElementTypes.NODE);
            Content content = new Content("Content string");
            node.setContent(content);

            content = node.getContent();
            System.out.println(content.getStringContent());

            performer.deleteScNode(node);
        }
        finally {
            //performer.finishExecution();
        }

    }

    @Test
    public void testFloatContent() {
        try {
            //performer.startExecution();
            ScNode node = factory.createScNode("node", ScElementTypes.NODE);
            Content content = new Content((float) 123.123);
            node.setContent(content);

            content = node.getContent();
            System.out.println(content.getFloatContent());

            performer.deleteScNode(node);
        }
        finally {
            //performer.finishExecution();
        }

    }

    @Test
    public void testDoubleContent() {
        try {
            //performer.startExecution();
            ScNode node = factory.createScNode("node", ScElementTypes.NODE);
            Content content = new Content((double) 123.123);
            node.setContent(content);

            content = node.getContent();
            System.out.println(content.getDoubleContent());

            performer.deleteScNode(node);
        }
        finally {
            //performer.finishExecution();
        }

    }

    @Test
    public void testByteContent() {
        try {
            //performer.startExecution();
            ScNode node = factory.createScNode("node", ScElementTypes.NODE);
            byte[] b = new byte[]{
                0x0, 0x0
            };
            Content content = new Content(b);
            node.setContent(content);

            content = node.getContent();
            for (byte currentByte : content.getByteContent()) {
                System.out.print(currentByte);
            }

            performer.deleteScNode(node);
        }
        finally {
            //performer.finishExecution();
        }

    }
}
