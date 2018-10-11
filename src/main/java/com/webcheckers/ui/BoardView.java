package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<RowView> {

    private ArrayList<RowView> listRowViews;

    public BoardView(ArrayList<RowView> listRowViews) {
        this.listRowViews = listRowViews;
    }

    public Iterator<RowView> iterator() {
        return listRowViews.iterator();
    }


}
