package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scevents.ScActionListener;
import net.ostis.sccore.scevents.ScEvent;
import net.ostis.sccore.scevents.ScEventFactory;
import net.ostis.sccore.scevents.ScEventHandler;
import net.ostis.sccore.scevents.ScEventListener;
import net.ostis.sccore.scevents.ScEventTypes;
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
        String baseLocation = "D:\\data\\sc_core.db";
        ScPerformer performer = new ScPerformerImpl(baseLocation);

        try {
            //!!! necessarily required (open transaction)
            performer.startExecution();
            ScFactory factory = performer.getScFactory();
            ScNode node1 = factory.createScNode("first", ScElementTypes.NODE_CONST);
            ScNode node2 = factory.createScNode("second", ScElementTypes.NODE_CONST);
            ScNode node3 = factory.createScNode("third", ScElementTypes.NODE_CONST);

            ScEventHandler eventHandler = ScEventHandler.getInstance();
            ScEventListener eventListener = ScEventFactory.createScListner(ScEventTypes.ATTACH_INPUT_TO_NODE,
                new WhenCreateArcToSecondNode(factory), node2);

            ScEventListener eventListener1 = ScEventFactory.createScListner(ScEventTypes.ATTACH_OUTPUT_TO_NODE,
                new WhenCreateArcFromSecondNode(factory), node3);

            ScEventListener eventListener2 = ScEventFactory.createScListner(ScEventTypes.DETACH_OUTPUT_FROM_NODE,
                new WhenDetachFromFirstNode(performer), node1);

            ScEventListener eventListener3 = ScEventFactory.createScListner(ScEventTypes.ATTACH_INPUT_TO_ARC,
                new WhenAttachToArc(factory), node1);

            eventHandler.subscribeOnEvent(eventListener);
            eventHandler.subscribeOnEvent(eventListener1);
            eventHandler.subscribeOnEvent(eventListener2);
            eventHandler.subscribeOnEvent(eventListener3);

            List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
            typeList.add(ScElementTypes.ARC_CONST);
            factory.generate_5_f_a_f_a_f(node1, typeList, node2, typeList, node3);

            ScNode find1 = performer.findScNodeByName("first");
            ScNode find2 = performer.findScNodeByName("second");
            ScNode find3 = performer.findScNodeByName("third");

            ScArc arc1 = node1.getAllOutputScArcs().get(0);
            ScArc arc2 = node3.getAllOutputScArcs().get(0);

//            System.out.println(find1.getName() + " -> " + arc1.getType() + " -> " + find2.getName());
//            System.out.println(find3.getName() + " -> " + arc2.getType() + " -> " + arc1.getType());


//            ScArc arc3 = node2.getAllInputScArcs().get(0);

//            System.out.println(arc3.getStartScNode().getName());
//            System.out.println(arc3.getEndScNode().getName());

//            List<ScArc> inputArcs = arc1.getAllInputScArcs();
//
//            System.out.println("input arcs from: ");
//            for (ScArc currentScArc : inputArcs) {
//                System.out.println(currentScArc.getStartScNode().getName());
//            }

//            List<ScArc> inputArcs = node1.getAllOutputScArcs();
//            for (ScArc currentScArc : inputArcs) {
//                System.out.println(currentScArc.getStartScNode().getName()
//                    + " --> " + currentScArc.getEndScNode().getName() + "\n");
//            }
            System.out.println("\n------------f_a_a-----------------\n");
            Iterator i = performer.createIterator_3_f_a_a(node1, "CONST", "sd");
            while (i.hasNext()) {
                i.next();
            }
            System.out.println("\n-------------a_a_f----------------\n");
            i = performer.createIterator_3_a_a_f("s", "CONST", arc1);
            while (i.hasNext()) {
                i.next();
            }
            System.out.println("\n-------------f_a_f----------------\n");
            i = performer.createIterator_3_f_a_f(arc1, "CONST", find3);
            while (i.hasNext()) {
                i.next();
            }

            long time = System.currentTimeMillis();
            System.out.println("execute in: " + (System.currentTimeMillis() - time));

            //performer.deleteScNode(performer.findScNodeByName("first"));
            //performer.deleteScArc(arc1);
            //        for (int i = 0; i < 50500; i++) {
            //            factory.createScNode(Integer.toString(i), ScNodeTypes.CONST);
            //        }

        }
        finally {
            //!!! necessarily required (close transaction)
            performer.finishExecution();
        }

        try {
            performer.startExecution();
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

        @Override
        public void perform(ScEvent event) {
            ScElement element = event.getSource();
            ScArc arc = (ScArc) element;
            ScNode secondNode = arc.getEndScNode();
            ScNode forthNode = factory.createScNode("forth", ScElementTypes.NODE_CONST);
            List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
            typeList.add(ScElementTypes.ARC_CONST);
            factory.generate_3_f_a_f(secondNode, typeList, forthNode);
        }
    }

    private static class WhenCreateArcFromSecondNode implements ScActionListener {

        private ScFactory factory;

        public WhenCreateArcFromSecondNode(ScFactory factory) {
            this.factory = factory;
        }

        @Override
        public void perform(ScEvent event) {
            ScElement element = event.getSource();
            ScArc arc = (ScArc) element;
            ScNode secondNode = arc.getStartScNode();
            ScNode node = factory.createScNode("five", ScElementTypes.NODE_CONST);
            List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
            typeList.add(ScElementTypes.ARC_CONST);
            factory.generate_3_f_a_f(node, typeList, secondNode.getAllOutputScArcs().get(0));
        }
    }

    private static class WhenDetachFromFirstNode implements ScActionListener {

        private ScPerformer performer;

        public WhenDetachFromFirstNode(ScPerformer performer) {
            this.performer = performer;
        }

        public void perform(ScEvent event) {
            ScNode sixNode = performer.getScFactory().createScNode("six", ScElementTypes.NODE_CONST);
            List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
            typeList.add(ScElementTypes.ARC_CONST);
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
            ScNode node = factory.createScNode("attr", ScElementTypes.NODE_CONST);
            List<ScElementTypes> typeList = new ArrayList<ScElementTypes>();
            typeList.add(ScElementTypes.ARC_CONST);
            factory.generate_3_f_a_f(node, typeList, arc);
        }
    }
}
