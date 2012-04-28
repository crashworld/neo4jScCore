package net.ostis.sccore.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.scelements.ScElement;

/**
 * Iterates over 3_f_a_f constructions in database.
 *
 * @author Q-ANS
 */
public class ScIterator_3_f_a_f implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_3_f_a_f(AbstractGraphDatabase db, ScElement firstElement, List<Long> secondTypes,
        ScElement thirdElement) {

        StringBuilder typesStartExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
        for (long nodeType : secondTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND arc2<-[:typeLink]-type" + n);
            n++;
        }

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
            "START node1=node(" + firstElement.getAddress() + "), elem3=node(" + thirdElement.getAddress() + ") "
                + typesStartExpr + " "
                + "MATCH node1--arc2--elem3 "
                + "WHERE ((not(elem3._connectorNode)) or (arc2-->elem3)) "
                + typesWhereExpr + " "
                + "RETURN node1, arc2, elem3");

        resultIterator = result.iterator();


    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    /**
     * Returns 3_f_a_f constraint from next iterator result.
     *
     * @return 3_f_a_f constraint
     */
    public ScConstraint next() {
        return ScConstraint.createThreeElementConstraint(resultIterator.next());
    }

    /**
     * Not supported.
     */
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
