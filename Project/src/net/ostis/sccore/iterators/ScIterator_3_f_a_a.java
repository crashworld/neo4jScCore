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
 * Iterates over 3_f_a_a constructions in database.
 * @author Q-ANS
 */
public class ScIterator_3_f_a_a implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_3_f_a_a(AbstractGraphDatabase db, ScElement firstElement, List<Long> secondTypes,
        List<Long> thirdTypes) {

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

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
            "START node1=node(" + firstElement.getAddress() + ") "
            + typesStartExpr + " "
            + "MATCH node1-[:beginLink]->arc2-[:endLink]->elem3 "
            + "WHERE not(node1._connectorNode)"
            + typesWhereExpr + " "
            + "RETURN node1, arc2, elem3 ");

        resultIterator = result.iterator();

    }

    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    /**
     * Returns 3_f_a_a constraint from next iterator result.
     * @return 3_f_a_a constraint
     */
    public ScConstraint next() {
        return ScConstraint.createThreeElementConstraint(resultIterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
