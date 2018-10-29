package com.webcheckers.appl;

import com.webcheckers.appl.TurnController;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.SimpleMove;
import com.webcheckers.ui.BoardView;
import com.webcheckers.ui.Message;
import com.webcheckers.ui.Message.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Application-Tier")
public class TurnControllerTest {

    private static final String MESSAGE_BODY_STR = "testMessage";
    private static final String MESSAGE_TYPE_STR = "info";
    private static final String TEST_JSON = "{\"start\":{\"row\":4,\"cell\":1},\"end\":{\"row\":5,\"cell\":0}";
    private static final String TEST_ID = "1";
    private static final String TEST_NAME = "meh";
    private static final int ROW_START = 4;
    private static final int ROW_END = 5;
    private static final int COL_START = 4;
    private static final int COL_END = 0;

    // Component Under Test
    private TurnController CuT;

    // Friendly Objects
    PlayerLobby playerLobby;

    // Mocked Objects

    @BeforeEach
    public void setup() {
        playerLobby = new PlayerLobby();
        CuT = new TurnController(playerLobby);
    }

    @AfterEach
    public void tearDown() {
        playerLobby = null;
        CuT = null;
    }

    @Test
    public void testConstructor() {
        Assertions.assertNotNull(CuT.builder);
        Assertions.assertNotNull(CuT.playerLobby);
    }

    @Test
    public void testMesageFromModelToUI() {
        Message testMessage = new Message(MESSAGE_BODY_STR, MessageType.info);
        String json = CuT.MessageFromModeltoUI(testMessage);
        String expectedJson = String.format("{\"text\":\"%s\",\"type\":\"%s\"}", MESSAGE_BODY_STR, MESSAGE_TYPE_STR);
        Assertions.assertEquals(expectedJson, json);
    }


}
