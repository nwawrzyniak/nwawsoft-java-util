package com.nwawsoft.util.datastructures;

/**
 * A data type for storing, passing and working with two objects of any type.
 */
public class TwoTuple {
    private Object object1;
    private Object object2;

    /**
     * Creates an empty TwoTuple. Both objects are initialized to null.
     */
    public TwoTuple() {
        this(null, null);
    }

    /**
     * Creates a TwoTuple from the specified objects. Objects may be null.
     *
     * @param object1 the first object.
     * @param object2 the second object.
     */
    public TwoTuple(final Object object1, final Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public Object getObject1() {
        return object1;
    }

    public void setObject1(final Object object1) {
        this.object1 = object1;
    }

    public Object getObject2() {
        return object2;
    }

    public void setObject2(final Object object2) {
        this.object2 = object2;
    }

    /**
     * Returns the class/type of the first object.
     *
     * @return a reflection of the class of 'object1'.
     */
    public Class objectOneClass() {
        return object1.getClass();
    }

    /**
     * Returns the class/type of the second object.
     *
     * @return a reflection of the class of 'object2'.
     */
    public Class objectTwoClass() {
        return object2.getClass();
    }
}
