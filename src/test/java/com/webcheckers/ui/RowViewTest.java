package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Space;
import com.webcheckers.model.Space.SpColor;

import java.lang.reflect.Array;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("UI-Tier")
public class RowViewTest {
    private RowView rowView;

    Space space1 = new Space(SpColor.black);
    Space space2 = new Space(SpColor.white);
    Space[] spaces = new Space[2];
    SpaceView spaceView1 = new SpaceView(new Space(SpColor.black), 0);
    SpaceView spaceView2 = new SpaceView(new Space(SpColor.white), 1);


    @Test
    public void testConstructorStandard() {
        spaces[0] = space1;
        spaces[1] = space2;

        rowView = new RowView(spaces, 0, false);
        assertEquals(spaceView1, rowView.listSpaceViews.get(0), "RowView did not construct correctly");
    }

    @Test
    public void testConstructorReverse() {
        spaces[0] = space1;
        spaces[1] = space2;

        RowView rowView2 = new RowView(spaces, 0, true);
        assertEquals(spaceView2, rowView2.listSpaceViews.get(0), "RowView did not reverse correctly");
    }

    @Test
    public void testGetIndex() {
        assertNotNull(rowView.getIndex(), "Index was null");
    }

}
