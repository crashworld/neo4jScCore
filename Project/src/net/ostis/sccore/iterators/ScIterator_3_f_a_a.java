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

    public ScIterator_3_f_a_a(AbstractGraphDatabase db, ScElement firstElement, List<ScElementTypes> arcTypes,
            List<ScElementTypes> thirdElementTypes) {

        StringBuilder thirdElementTypesMatchExpr = new StringBuilder("");
        StringBuilder thirdElementWhereExpr = new StringBuilder("");
        int n = 0;
        for (ScElementTypes thirdElementType : thirdElementTypes) {
            thirdElementTypesMatchExpr.append(", elem3<-[:endLink]-()<-[:beginLink]-type" + n);
            thirdElementWhereExpr.append(" AND type" + n + "._scNodeName=\"" + thirdElementType.name() + "\"");
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
                "START node1=node(" + firstElement.getAddress() + ") "
                + "MATCH node1-[:beginLink]->arc2-[:endLink]->elem3"
                + thirdElementTypesMatchExpr + " "
                + arcTypesMatchExpr + " "
                + "WHERE not(node1._connectorNode)"
                + thirdElementWhereExpr + " "
                + arcTypesWhereExpr + " "
                + "RETURN node1, arc2, elem3 ");

        //test>>>>>>>>>>>>
        System.out.println(result);
        //test<<<<<<<<<<<<

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
        return ScConstraint.createConstraintFromResultSet(resultIterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
