/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.iterators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScArcImpl;


import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;
import org.neo4j.graphdb.Node;

/**
 * Provides 3-elements and 5-elements sc-constraints. 
 * @author Q-ANS
 */
public class ScConstraint {

    private final static String NODE1 = "node1";
    private final static String ARC2 = "arc2";
    private final static String ELEMENT3 = "elem3";
    private final static String ARC4 = "arc4";
    private final static String NODE5 = "node5";
    private List<ScElement> elements = new ArrayList<ScElement>();

    /**
     * Creates constraint from List of sc-elements.
     * @param elements
     */
    public ScConstraint(List<ScElement> elements) {
        this.elements = elements;
    }

    public ScElement getElement(int n) {
        return elements.get(n - 1);
    }

    /**
     * Creates constraint from Map gotten by iterator from databese.
     * @param resultRow
     * @return ScConstraint
     */
    public static ScConstraint createConstraintFromResultSet(Map resultRow) {
        List elements = new ArrayList<ScElement>();

        //creates first element - Node
        ScNode node1 = new ScNodeImpl((Node) resultRow.get(NODE1));
        elements.add(node1);

        //creates second element - Arc
        ScArc arc2 = new ScArcImpl((Node) resultRow.get(ARC2));
        elements.add(arc2);

        //creates third element - may be Node or Arc
        if (((Node) resultRow.get(ELEMENT3)).hasProperty("_connectorNode")) {
            ScArc arc3 = new ScArcImpl((Node) resultRow.get(ELEMENT3));
            elements.add(arc3);
        } else {
            ScNode node3 = new ScNodeImpl((Node) resultRow.get(ELEMENT3));
            elements.add(node3);
        }
        //test>>>>>>>>>>>>
        ScConstraint c = new ScConstraint(elements);
        System.out.println(c.getElement(1).getAddress() + "-" + c.getElement(2).getAddress() + "-" + c.getElement(3).getAddress());
        //test<<<<<<<<<<<<<
        return new ScConstraint(elements);
    }
}