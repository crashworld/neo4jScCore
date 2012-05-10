/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contain standard types of sc elements.
 *
 * @author yaskoam
 */
public class ScElementTypes {

    public static final String NODE = "NODE";
    public static final String ARC = "ARC";
    public static final String CONST = "CONST";
    public static final String VAR = "VAR";
    public static final String META = "META";
    public static final String POS = "POS";
    public static final String NEG = "NEG";
    public static final String FUZ = "FUZ";

    public static final String ELEMENT_TYPE_PROPERTY = "_scElementType";

    /**
     * Method that return all existing types in list.
     *
     * @return list of types 
     */
    public static List<String> getTypesList() {

        List<String> typesList = new ArrayList<String>();
        typesList.add(ARC);
        typesList.add(NODE);
        typesList.add(CONST);
        typesList.add(VAR);
        typesList.add(META);
        typesList.add(POS);
        typesList.add(NEG);
        typesList.add(FUZ);

        return typesList;
    }
}
