package com.neerajweb.gcmgreetingapp.model;

/**
 * Created by Admin on 07/09/2015.
 */
public class Orientations {
    public enum Orientation {
        Ordered, Disordered;

        public static Orientation fromIndex(int index) {
            Orientation[] values = Orientation.values();
            if(index < 0 || index >= values.length) {
                throw new IndexOutOfBoundsException();
            }
            return values[index];
        }
    }
}
