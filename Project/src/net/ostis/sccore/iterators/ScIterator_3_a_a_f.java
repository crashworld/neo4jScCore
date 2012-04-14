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
 * @author Q-ANS
 */
public class ScIterator_3_a_a_f implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_3_a_a_f(AbstractGraphDatabase db, List<ScElementTypes> nodeTypes, List<ScElementTypes> arcTypes,
            ScElement thirdElement) {

        StringBuilder nodeTypesMatchExpr = new StringBuilder("");
        StringBuilder nodeWhereExpr = new StringBuilder("");
        int n = 0;
        for (ScElementTypes nodeType : nodeTypes) {
            nodeTypesMatchExpr.append(", node1<-[:endLink]-()<-[:beginLink]-type" + n);
            nodeWhereExpr.append(" AND type" + n + "._scNodeName=\"" + nodeType.name() + "\"");
            n++;
        }
        StringBuilder arcTypesMatchExpr = new StringBuilder("");
        StringBuilder arcTypesWhereExpr = new StringBuilder("");
        for (ScElementTypes arcType : arcTypes) {
            arcTypesMatchExpr.append(", arc2<-[:endLink]-()<-[:beginLink]-type" + n);
            arcTypesWhereExpr.append(" AND type" + n + "._scNodeName=\"" + arcType.name() + "\"");
            n++;
        }

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
                "START elem3=node(" + thirdElement.getAddress() + ") "
                + "MATCH elem3<-[:endLink]-arc2<-[:beginLink]-node1 "
                + nodeTypesMatchExpr + " "
                + arcTypesMatchExpr + " "
                + "WHERE not(node1._connectorNode) "
                + nodeWhereExpr + " "
                + arcTypesWhereExpr + " "
                + "RETURN node1, arc2, elem3 ");

        //test>>>>>>>>>>>>
        System.out.println(result);
        //test<<<<<<<<<<<<<

        resultIterator = result.iterator();

    }

    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    /**
     * Returns 3_a_a_f constraint from next iterator result.
     * @return 3_a_a_f constraint
     */
    public ScConstraint next() {
        return ScConstraint.createConstraintFromResultSet(resultIterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
