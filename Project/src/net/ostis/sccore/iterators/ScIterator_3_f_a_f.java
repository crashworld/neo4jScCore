/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.AbstractGraphDatabase;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScArcImpl;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;
import net.ostis.sccore.scelements.ScNodeImpl;

/**
 * Iterates over 3_f_a_f constructions in database.
 * @author Q-ANS
 */
public class ScIterator_3_f_a_f implements ScIterator {

    private Iterator<Map<String, Object>> resultIterator;

    public ScIterator_3_f_a_f(AbstractGraphDatabase db, ScElement firstElement, String secondType, ScElement thirdElement) {

        ExecutionEngine engine = new ExecutionEngine(db);

        //Just test query. Types needed.
        ExecutionResult result = engine.execute(
            "START node1=node(" + firstElement.getAddress() + "), elem3=node(" + thirdElement.getAddress() + ") "
            + "MATCH node1--arc2--elem3 "
            + "WHERE (not(elem3._connectorNode)) or (arc2-->elem3) "
            + "RETURN node1, arc2, elem3");

        //test>>>>>>>>>>>>
        System.out.println(result);
        //test<<<<<<<<<<<<

        resultIterator = result.iterator();
    }

    public boolean hasNext() {
        return resultIterator.hasNext();
    }

    /**
     * Returns 3_f_a_f constraint from next iterator result.
     * @return 3_f_a_f constraint
     */
    public ScConstraint next() {
        return ScConstraint.createConstraintFromResultSet(resultIterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
