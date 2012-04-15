/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.types;

/**
 * Enum that contain types of sc elements.
 *
 * @author yaskoam
 */
public enum ScElementTypes {

    NODE("NODE"),
    
    ARC("ARC"),
    
    CONST("CONST"),
    
    VAR("VAR"),
    
    META("META"),
    
    POS("POS"),
    
    NEG("NEG"),
    
    FUZ("FUZ");

    private String typeName = "";

    private ScElementTypes(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
    
    
}
