package test;

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
import net.ostis.sccore.types.ScArcTypes;
import net.ostis.sccore.types.ScNodeTypes;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 16:19
 */
public class MainForTest {

    public static void main(String[] args) {
        String baseLocation = "D:\\data\\sc_core.db";
        ScPerformer performer = new ScPerformer(baseLocation);

        try {
            //!!! necessarily required (open transaction)
            performer.beginExecution();
            ScFactory factory = performer.getScFactory();
            ScEventHandler eventHandler = ScEventHandler.getInstance();
            ScEventListener eventListner = ScEventFactory.createScListner(ScEventTypes.ATTACH_INPUT_TO_NODE,
                new WhenCreateArcToSecondNode(factory), "second");

            eventHandler.subscribeOnEvent(eventListner);

            ScNode node1 = factory.createScNode("first", ScNodeTypes.CONST);
            ScNode node2 = factory.createScNode("second", ScNodeTypes.CONST);
            ScNode node3 = factory.createScNode("third", ScNodeTypes.CONST);

            factory.generate_5_f_a_f_a_f_1(node1, ScArcTypes.CONST, node2, ScArcTypes.CONST, node3);

            ScNode find1 = performer.findScNodeByName("first");
            ScNode find2 = performer.findScNodeByName("second");
            ScNode find3 = performer.findScNodeByName("third");

            ScArc arc1 = node1.getAllOutputScArcs().get(0);
            ScArc arc2 = node3.getAllOutputScArcs().get(0);

            System.out.println(find1.getName() + " -> " + arc1.getType() + " -> " + find2.getName());
            System.out.println(find3.getName() + " -> " + arc2.getType() + " -> " + arc1.getType());


            ScArc arc3 = node2.getAllInputScArcs().get(0);

            System.out.println(arc3.getStartScNode().getName());
            System.out.println(arc3.getEndScNode().getName());
            System.out.println(arc3.getArcConnectorNode().getId());

            long time = System.currentTimeMillis();
            System.out.println("execute in: " + (System.currentTimeMillis() - time));
            //        for (int i = 0; i < 50500; i++) {
            //            factory.createScNode(Integer.toString(i), ScNodeTypes.CONST);
            //        }
        } finally {
            //!!! necessarily required (close transaction)
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
            ScNode forthNode = factory.createScNode("forth", ScNodeTypes.CONST);
            factory.generate_3_f_a_f(secondNode, ScArcTypes.CONST, forthNode);
        }
    }
}
