package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class RowView implements Iterable<SpaceView>{
    //
    // Attributes
    //
    private ArrayList<SpaceView> listSpaceViews;
    private int index;

    //
    // Constructor
    //
    public RowView(int index, ArrayList<SpaceView> listSpaceViews) {
        this.index = index;
        this.listSpaceViews = listSpaceViews;
    }

    //
    // Methods
    //
    /**
     * Get the index of the row
     * @return row index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Gets an iterator of SpaceViews
     * @return SpaceView iterator
     */
    @Override
    public Iterator<SpaceView> iterator() {
        return listSpaceViews.iterator();
    }
}
