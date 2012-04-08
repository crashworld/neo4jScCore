/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ostis.sccore.iterators;

import java.util.Iterator;

/**
 *
 * @author Q-ANS
 */
public interface ScIterator extends Iterator {

    boolean hasNext();

    Object next();

    void remove();
}
