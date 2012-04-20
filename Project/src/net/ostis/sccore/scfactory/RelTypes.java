package net.ostis.sccore.scfactory;

import org.neo4j.graphdb.RelationshipType;

/**
 * Enum for relationship types.
 * 
 * @author yaskoam
 */
public enum RelTypes implements RelationshipType {
    beginLink,
    endLink,
    typeLink
}