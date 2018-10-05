package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {

    private ArrayList<Row> listRows;

    public BoardView(ArrayList<Row> listRows) {
        this.listRows = listRows;
    }

    public Iterator<Row> iterator() {
        return listRows.iterator();
    }
}
