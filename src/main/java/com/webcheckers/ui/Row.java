package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space>{
    private ArrayList<Space> listSpaces;
    private int index;

    public Row(int index, ArrayList<Space>listSpaces) {
        this.index = index;
        this.listSpaces = listSpaces;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public Iterator<Space> iterator() {
        return listSpaces.iterator();
    }
}
