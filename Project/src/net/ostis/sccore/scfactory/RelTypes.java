/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scfactory;

import org.neo4j.graphdb.RelationshipType;

/**
 * Enum for relationship types.
 * @author yaskoam
 */
public enum RelTypes implements RelationshipType {
    beginLink,
    endLink
}