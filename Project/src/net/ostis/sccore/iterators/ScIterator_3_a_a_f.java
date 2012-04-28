/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Iterates over 3_a_a_f constructions in database.
 *
 * @author Q-ANS
 */
public class ScIterator_3_a_a_f implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_3_a_a_f(AbstractGraphDatabase db, List<Long> firstTypes, List<Long> secondTypes,
        ScElement thirdElement) {

        StringBuilder typesStartExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
        for (long nodeType : firstTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND node1<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : secondTypes) {
            typesStartExpr.append(", type" + n + "=node(" + nodeType + ") ");
            typesWhereExpr.append(" AND arc2<-[:typeLink]-type" + n);
            n++;
        }

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
            "START elem3=node(" + thirdElement.getAddress() + ") "
            + typesStartExpr + " "
            + "MATCH elem3<-[:endLink]-arc2<-[:beginLink]-node1 "
            + "WHERE not(node1._connectorNode) "
            + typesWhereExpr + " "
            + "RETURN node1, arc2, elem3 ");
			
        resultIterator = result.iterator();

    }

    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    /**
     * Returns 3_a_a_f constraint from next iterator result.
     *
     * @return 3_a_a_f constraint
     */
    public ScConstraint next() {
        return ScConstraint.createThreeElementConstraint(resultIterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
