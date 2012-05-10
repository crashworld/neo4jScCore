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
public class ScIterator_5_a_a_f_a_a implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    /**
     * Construct sc iterator object
     * @param db data base object
     * @param firstTypes list of type nodes addresses for the first element
     * @param secondTypes list of type nodes addresses for the second element
     * @param thirdElement sc element to search
     * @param fourthTypes list of type nodes addresses for the forth element
     * @param fifthTypes sc element to search
     */
    public ScIterator_5_a_a_f_a_a(AbstractGraphDatabase db, List<Long> firstTypes, List<Long> secondTypes,
        ScElement thirdElement, List<Long> fourthTypes, List<Long> fifthTypes) {

        StringBuilder typesStartExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
        for (long nodeType : firstTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(", node1<-[:typelink]-type" + n);
            n++;
        }
        for (long nodeType : secondTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND arc2<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : fourthTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND arc4<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : fifthTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(", node5<-[:typelink]-type" + n);
            n++;
        }

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
            "START elem3=node(" + thirdElement.getAddress() + ") "
                + typesStartExpr + " "
                + "MATCH node1-[:beginLink]->arc2-[:endLink]->elem3, arc2<-[:endLink]-arc4<-[:beginLink]-node5 "
                + "WHERE not(node1._connectorNode) "
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
     * Returns 5_a_a_f_a_a constraint from next iterator result.
     *
     * @return 5_a_a_f_a_a constraint
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
