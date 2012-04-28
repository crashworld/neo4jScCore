package net.ostis.sccore.iterators;

import java.util.Iterator;

/**
 * @author Q-ANS
 */
public interface ScIterator extends Iterator {

    boolean hasNext();

    Object next();

    void remove();
}
