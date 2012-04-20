/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.types.ScElementTypes;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.kernel.AbstractGraphDatabase;

/**
 *
 * @author SeeDuke
 */
public class ScIterator_5_f_a_a_a_f implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_5_f_a_a_a_f(AbstractGraphDatabase db, ScElement firstElement, List<Long> secondTypes,
        List<Long> thirdTypes, List<Long> fourthTypes, ScElement fifthElement) {

        StringBuilder typesStartExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
        for (long nodeType : secondTypes) {
            typesStartExpr.append(", type" + n + "=node(" + (int) nodeType + ") ");
            typesWhereExpr.append(" AND arc2<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : thirdTypes) {
            typesStartExpr.append(", type" + n + "=node(" + (int) nodeType + ") ");
            typesWhereExpr.append(" AND elem3<-[:typeLink]-type" + n);
            n++;
        }
        for (long nodeType : fourthTypes) {
            typesStartExpr.append(", type" + n + "=node(" + (int) nodeType + ") ");
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

    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    public ScConstraint next() {
        return ScConstraint.createFiveElementConstraint(resultIterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
