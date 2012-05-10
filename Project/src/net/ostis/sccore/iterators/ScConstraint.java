package net.ostis.sccore.iterators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScArcImpl;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;

/**
 * Provides 3-elements and 5-elements sc-constraints.
 *
 * @author Q-ANS
 */
public class ScConstraint {

    private final static String NODE1 = "node1";
    private final static String ARC2 = "arc2";
    private final static String ELEMENT3 = "elem3";
    private final static String ARC4 = "arc4";
    private final static String NODE5 = "node5";
    private static List<ScElement> elements = new ArrayList<ScElement>();

    /**
     * Creates constraint from List of sc-elements.
     *
     * @param elements sc elements of constraint
     */
    public ScConstraint(List<ScElement> elements) {
        ScConstraint.elements = elements;
    }

    /**
     * Method that return element of constraint.
     *
     * @param number number of element
     * @return sc element
     */
    public ScElement getElement(int number) {
        return elements.get(number - 1);
    }

    /**
     * Creates constraint from Map gotten by iterator from database.
     *
     * @param resultRow search result from data base
     * @return ScConstraint object that present search result
     */
    public static ScConstraint createThreeElementConstraint(Map resultRow) {
        List<ScElement> elements = new ArrayList<ScElement>();

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
        }
        else {
            ScNode node3 = new ScNodeImpl((Node) resultRow.get(ELEMENT3));
            elements.add(node3);
        }

        return new ScConstraint(elements);
    }

    /**
     * Method that create five element constraint.
     * @param resultRow search result from data base
     * @return ScConstraint object that present search result
     */
    public static ScConstraint createFiveElementConstraint(Map resultRow) {

        elements = createThreeElementConstraint(resultRow).getElements();

        //creates first element - Node
        ScNode arc4 = new ScNodeImpl((Node) resultRow.get(ARC4));
        elements.add(arc4);

        //creates second element - Arc
        ScArc node5 = new ScArcImpl((Node) resultRow.get(NODE5));
        elements.add(node5);

        return new ScConstraint(elements);

    }

    private List<ScElement> getElements() {
        return elements;
    }
}
