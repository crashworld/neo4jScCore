package test;

import javax.management.timer.Timer;
import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;
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
        String baseLocation = "D:\\Programs\\neo4j-enterprise-1.6-windows\\neo4j-enterprise-1.6\\data\\graph.db";
        ScPerformer performer = new ScPerformer(baseLocation);

        //!!! necessarily required (open transaction)
        performer.beginExecution();
        ScFactory factory = performer.getScFactory();
        ScNode node1 = factory.createScNode("first", ScNodeTypes.CONST);
        ScNode node2 = factory.createScNode("second", ScNodeTypes.CONST);
        ScNode node3 = factory.createScNode("third", ScNodeTypes.CONST);

        ScArc arc1 = factory.createScArc(node1, node2, ScArcTypes.CONST);
        ScArc arc2 = factory.createScArc(node3, arc1, ScArcTypes.CONST);

        ScNode find1 = performer.findScNodeByName("first");
        ScNode find2 = performer.findScNodeByName("second");
        ScNode find3 = performer.findScNodeByName("third");


        System.out.println(find1.getName() + " -> " + arc1.getType() + " -> " + find2.getName());
        System.out.println(find3.getName() + " -> " + arc2.getType() + " -> " + arc1.getType());

        long time = System.currentTimeMillis();
        for (int i = 0; i < 50500; i++) {
            factory.createScNode(Integer.toString(i), ScNodeTypes.CONST);
        }
        
        //!!! necessarily required (close transaction)
        performer.finishExecution();
        System.out.println("execute in: " + ( System.currentTimeMillis() -  time));
    }
}
