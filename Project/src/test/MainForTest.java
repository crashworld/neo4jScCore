package test;

import java.util.ArrayList;
import java.util.List;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scevents.ScActionListener;
import net.ostis.sccore.scevents.ScEvent;
import net.ostis.sccore.scfactory.ScFactory;
import net.ostis.sccore.scperformer.ScPerformer;
import net.ostis.sccore.scperformer.ScPerformerImpl;
import net.ostis.sccore.types.ScElementTypes;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 16:19
 */
public class MainForTest {

    public static void main(String[] args) {
        String baseLocation = "data\\sc_core.db";
        ScPerformer performer = new ScPerformerImpl(baseLocation);

/*        try {
            //!!! necessarily required (open transaction)
            performer.startExecution();
            ScFactory factory = performer.getScFactory();
            ScNode node1 = factory.createScNode("first", ScElementTypes.NODE);
            ScNode node2 = factory.createScNode("second", ScElementTypes.NODE);
            ScNode node3 = factory.createScNode("third", ScElementTypes.NODE);

//            ScEventHandler eventHandler = ScEventHandler.getInstance();
//            ScEventListener eventListener = ScEventFactory.createScListner(ScEventTypes.ATTACH_INPUT_TO_NODE,
//                new WhenCreateArcToSecondNode(factory), node2);
//
//            ScEventListener eventListener1 = ScEventFactory.createScListner(ScEventTypes.ATTACH_OUTPUT_TO_NODE,
//                new WhenCreateArcFromSecondNode(factory), node3);
//
//            ScEventListener eventListener2 = ScEventFactory.createScListner(ScEventTypes.DETACH_OUTPUT_FROM_NODE,
//                new WhenDetachFromFirstNode(performer), node1);
//
//            ScEventListener eventListener3 = ScEventFactory.createScListner(ScEventTypes.ATTACH_INPUT_TO_ARC,
//                new WhenAttachToArc(factory), node1);
//
//            eventHandler.subscribeOnEvent(eventListener);
//            eventHandler.subscribeOnEvent(eventListener1);
//            eventHandler.subscribeOnEvent(eventListener2);
//            eventHandler.subscribeOnEvent(eventListener3);

            List<String> typeList = new ArrayList<String>();
            typeList.add(ScElementTypes.ARC);
            //factory.generate_5_f_a_f_a_f(node1, typeList, node2, typeList, node3);
            factory.generate_3_f_a_f(node1, typeList, node2);

            ScNode find1 = performer.findScNodeByName("first");
            ScNode find2 = performer.findScNodeByName("second");
            ScNode find3 = performer.findScNodeByName("third");

            List<String> typeList2 = new ArrayList<String>();
            typeList2.add(ScElementTypes.NODE);

            long time = System.currentTimeMillis();
            System.out.println("execute in: " + (System.currentTimeMillis() - time));


        }
        finally {
            //!!! necessarily required (close transaction)
            performer.finishExecution();
        }*/

        try {
            performer.startExecution();
            ScFactory factory = performer.getScFactory();
            ScNode node1 = factory.createScNode("first", ScElementTypes.NODE);
            ScNode node2 = factory.createScNode("second", ScElementTypes.NODE);
            ScNode node3 = factory.createScNode("third", ScElementTypes.NODE);
            node1.removeType(ScElementTypes.NODE);
        }
        finally {
            performer.finishExecution();
        }
    }

    private static class WhenCreateArcToSecondNode implements ScActionListener {

        private ScFactory factory;

        public WhenCreateArcToSecondNode(ScFactory factory) {
            this.factory = factory;
        }

        public void perform(ScEvent event) {
            ScElement element = event.getSource();
            ScArc arc = (ScArc) element;
            ScNode secondNode = arc.getEndScNode();
            ScNode forthNode = factory.createScNode("forth", ScElementTypes.NODE);
            List<String> typeList = new ArrayList<String>();
            typeList.add(ScElementTypes.ARC);
            factory.generate_3_f_a_f(secondNode, typeList, forthNode);
        }
    }

    private static class WhenCreateArcFromSecondNode implements ScActionListener {

        private ScFactory factory;

        public WhenCreateArcFromSecondNode(ScFactory factory) {
            this.factory = factory;
        }

        public void perform(ScEvent event) {
            ScElement element = event.getSource();
            ScArc arc = (ScArc) element;
            ScNode secondNode = arc.getStartScNode();
            ScNode node = factory.createScNode("five", ScElementTypes.NODE);
            List<String> typeList = new ArrayList<String>();
            typeList.add(ScElementTypes.ARC);
            factory.generate_3_f_a_f(node, typeList, secondNode.getAllOutputScArcs().get(0));
        }
    }

    private static class WhenDetachFromFirstNode implements ScActionListener {

        private ScPerformer performer;

        public WhenDetachFromFirstNode(ScPerformer performer) {
            this.performer = performer;
        }

        public void perform(ScEvent event) {
            ScNode sixNode = performer.getScFactory().createScNode("six", ScElementTypes.NODE);
            List<String> typeList = new ArrayList<String>();
            typeList.add(ScElementTypes.ARC);
            performer.getScFactory().generate_3_f_a_f(performer.findScNodeByName("second"), typeList, sixNode);
        }
    }

    private static class WhenAttachToArc implements ScActionListener {

        private ScFactory factory;

        public WhenAttachToArc(ScFactory factory) {
            this.factory = factory;
        }

        public void perform(ScEvent event) {
            ScArc arc = (ScArc) event.getSource();
            ScNode node = factory.createScNode("attr", ScElementTypes.NODE);
            List<String> typeList = new ArrayList<String>();
            typeList.add(ScElementTypes.ARC);
            factory.generate_3_f_a_f(node, typeList, arc);
        }
    }
}
