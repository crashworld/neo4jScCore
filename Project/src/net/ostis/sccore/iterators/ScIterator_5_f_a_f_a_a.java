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
 * @author SeeDuke
 */
public class ScIterator_5_f_a_f_a_a implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_5_f_a_f_a_a(AbstractGraphDatabase db, ScElement firstElement, List<Long> secondTypes,
        ScElement thirdElement, List<Long> fourthTypes, List<Long> fifthTypes) {

        StringBuilder typesStartExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
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
            "START node1=node(" + firstElement.getAddress() + "), elem3=node(" + thirdElement.getAddress() + ") "
            + typesStartExpr + " "
            + "MATCH node1-[:beginLink]->arc2-[:endLink]->elem3, arc2<-[:endLink]-arc4<-[:beginLink]-node5 "
            + "WHERE not(node1._connectorNode) "
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
