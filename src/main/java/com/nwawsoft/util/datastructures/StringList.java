package com.nwawsoft.util.datastructures;

import com.nwawsoft.util.tools.DebugPrinter;

/**
 * Works like List but ignores objects except Strings and List objects that are not purely made out of Strings.
 */
public class StringList extends List {

    /**
     * Creates a new StringList object.
     */
    public StringList() {
        super();
    }

    /**
     * Sets the Object where the 'current' reference is, but only if it is a String.
     *
     * @param o an Object to set to the 'current' reference of the StringList.
     */
    @Override
    public void setObject(final Object o) {
        if (o instanceof String) {
            super.setObject(o);
        }
    }

    /**
     * Appends the Object to the end of the StringList, but only if it is a String.
     *
     * @param o an Object to append to the StringList.
     */
    @Override
    public void append(final Object o) {
        if (o instanceof String) {
            super.append(o);
        }
    }

    /**
     * Inserts the Object in front of where the 'current' reference is, but only if it is a String.
     *
     * @param o an Object to insert into the StringList.
     */
    @Override
    public void insert(final Object o) {
        if (o instanceof String) {
            super.insert(o);
        }
    }

    /**
     * Concatenates the specified List to the end of the List making this call. Ignores all non-String values.
     *
     * @param l a List whose contents shall be appended to the StringList.
     */
    @Override
    public void concat(final List l) {
        if (l instanceof StringList) {
            super.concat(l);
        } else {
            l.toFirst();
            while (l.hasAccess()) {
                if (l.getObject() instanceof String) {
                    this.append(l.getObject());
                } else {
                    DebugPrinter.dp(this, "This is not a String. It is " + l.getObject() + ". Ignored.");
                }
                next();
            }
        }
    }

    /**
     * Prints the whole StringList one String per line and moves the 'current' reference to the first entry.
     */
    @Override
    public void print() {
        super.toFirst();
        while (super.hasAccess()) {
            System.out.println((String)super.getObject());
            super.next();
        }
        super.toFirst();
    }

    /**
     * Prints the whole StringList with a maximum of outputAmount Strings one String per line and moves the 'current'
     * reference to the first entry.
     *
     * @param outputAmount the maximum number of entries to print, starting at the beginning.
     */
    public void print(final int outputAmount) {
        super.toFirst();
        int count = 0;
        while (super.hasAccess() && count <= outputAmount) {
            count++;
            System.out.println((String)super.getObject());
            super.next();
        }
        super.toFirst();
    }

    /**
     * Prints the rest of the StringList, starting from where the 'current' reference was, one String per line and
     * moves the 'current' reference to the first entry.
     */
    public void printHere() {
        while (super.hasAccess()) {
            System.out.println((String)super.getObject());
            super.next();
        }
        super.toFirst();
    }

    /**
     * Prints the rest of the StringList with a maximum of outputAmount Strings, starting from where the 'current'
     * reference was, one String per line and moves the 'current' reference to the first entry.
     *
     * @param outputAmount the maximum number of entries to print, starting where the 'current' reference is.
     */
    public void printHere(final int outputAmount) {
        int count = 0;
        while (super.hasAccess() && count <= outputAmount) {
            System.out.println((String)super.getObject());
            super.next();
            count++;
        }
        super.toFirst();
    }
}
