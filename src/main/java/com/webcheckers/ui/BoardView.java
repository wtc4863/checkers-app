package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<RowView> {
    //
    // Attributes
    //
    private ArrayList<RowView> listRowViews;

    //
    // Constructor
    //
    public BoardView(ArrayList<RowView> listRowViews) {
        this.listRowViews = listRowViews;
    }

    //
    // Methods
    //
    /**
     * Gets iterator of RowViews
     * @return RowView iterator
     */
    public Iterator<RowView> iterator() {
        return listRowViews.iterator();
    }


}
