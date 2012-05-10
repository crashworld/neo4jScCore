package net.ostis.sccore.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.scelements.ScElement;

/**
 * @author SeeDuke
 */
public class ScIterator_5_f_a_a_a_f implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    /**
     * Construct sc iterator object
     * @param db data base object
     * @param firstElement sc element to search
     * @param secondTypes list of type nodes addresses for the second element
     * @param thirdTypes list of type nodes addresses for the third element
     * @param fourthTypes list of type nodes addresses for the forth element
     * @param fifthElement sc element to search
     */
    public ScIterator_5_f_a_a_a_f(AbstractGraphDatabase db, ScElement firstElement, List<Long> secondTypes,
        List<Long> thirdTypes, List<Long> fourthTypes, ScElement fifthElement) {

        StringBuilder typesStartExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
        for (long nodeType : secondTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND arc2<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : thirdTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND elem3<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : fourthTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND arc4<-[:typeLink]-type" + n);
            n++;
        }

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
            "START node1=node(" + firstElement.getAddress() + "), node5=node(" + fifthElement.getAddress() + ")"
                + typesStartExpr + " "
                + "MATCH node1-[:beginLink]->arc2-[:endLink]->elem3, arc2<-[:endLink]-arc4<-[:beginLink]-node5 "
                + "WHERE not(node1._connectorNode) AND not(node5._connectorNode) "
                + typesWhereExpr + " "
                + "RETURN node1, arc2, elem3, arc4, node5");


        resultIterator = result.iterator();

    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    /**
     * Returns 5_f_a_a_a_f constraint from next iterator result.
     *
     * @return 5_f_a_a_a_f constraint
     */
    public ScConstraint next() {
        return ScConstraint.createFiveElementConstraint(resultIterator.next());
    }

    /**
     * Not supported.
     */
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
