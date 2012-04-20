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
public class ScIterator_5_a_a_f_a_a implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_5_a_a_f_a_a(AbstractGraphDatabase db, List<ScElementTypes> firstTypes,
        List<ScElementTypes> secondTypes, ScElement thirdElement, List<ScElementTypes> fourthTypes,
        List<ScElementTypes> fifthTypes) {

        StringBuilder typesMatchExpr = new StringBuilder("");
        StringBuilder typesWhereExpr = new StringBuilder("");
        int n = 0;
        for (ScElementTypes nodeType : firstTypes) {
            typesMatchExpr.append(", node1<-[:endLink]-()<-[:beginLink]-type" + n);
            typesWhereExpr.append(" AND type" + n + "._scNodeName=\"" + nodeType.name() + "\"");
            n++;
        }
        for (ScElementTypes nodeType : secondTypes) {
            typesMatchExpr.append(", arc2<-[:endLink]-()<-[:beginLink]-type" + n);
            typesWhereExpr.append(" AND type" + n + "._scNodeName=\"" + nodeType.name() + "\"");
            n++;
        }
        for (ScElementTypes nodeType : fourthTypes) {
            typesMatchExpr.append(", arc4<-[:endLink]-()<-[:beginLink]-type" + n);
            typesWhereExpr.append(" AND type" + n + "._scNodeName=\"" + nodeType.name() + "\"");
            n++;
        }
        for (ScElementTypes nodeType : fifthTypes) {
            typesMatchExpr.append(", node5<-[:endLink]-()<-[:beginLink]-type" + n);
            typesWhereExpr.append(" AND type" + n + "._scNodeName=\"" + nodeType.name() + "\"");
            n++;
        }

        ExecutionEngine engine = new ExecutionEngine(db);
        ExecutionResult result = engine.execute(
            "START elem3=node(" + thirdElement.getAddress() + ") "
                + "MATCH node1-[:beginLink]->arc2-[:endLink]->elem3, arc2<-[:endLink]-arc4<-[:beginLink]-node5 "
                + typesMatchExpr + " "
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
