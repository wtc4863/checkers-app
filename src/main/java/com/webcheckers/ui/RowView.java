package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class RowView implements Iterable<SpaceView>{
    private ArrayList<SpaceView> listSpaceViews;
    private int index;

    public RowView(int index, ArrayList<SpaceView> listSpaceViews) {
        this.index = index;
        this.listSpaceViews = listSpaceViews;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public Iterator<SpaceView> iterator() {
        return listSpaceViews.iterator();
    }
}
