package net.ostis.sccore.iterators;

import java.util.Iterator;

/**
 * Interface for all sc iterators
 *
 * @author Q-ANS
 */
public interface ScIterator extends Iterator {

    /**
     * Method that check if next element exist.
     *
     * @return true if next element exist, in other case return false
     */
    boolean hasNext();

    /**
     * Method that get next element.
     *
     * @return next element of iteration
     */
    Object next();

    /**
     * Method that remove next element.
     */
    void remove();
}
