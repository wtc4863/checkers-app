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
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

@Tag("Application-Tier")
public class TurnControllerTest {

    private static final String MESSAGE_BODY_STR = "testMessage";
    private static final String MESSAGE_TYPE_STR = "info";
    private static final String SIMPLE_MOVE_JSON = "{\"start\":{\"row\":5,\"cell\":0},\"end\":{\"row\":4,\"cell\":1}}";
    private static final String JUMP_MOVE_JSON = "{\"start\":{\"row\":5,\"cell\":0},\"end\":{\"row\":3,\"cell\":2}}";

    private static final String TEST_RED_NAME = "red";
    private static final String TEST_WHITE_NAME = "white";
    private static final String TEST_RED_ID = "1";
    private static final String TEST_WHITE_ID= "2";

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

    @Test public void testConstructor() {
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

    @Test
    public void testMoveUItoModelSimpleMove() {
        SimpleMove expected = new SimpleMove(new Position(5,0), new Position(4, 1));
        Move actual = CuT.MovefromUItoModel(SIMPLE_MOVE_JSON);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testMoveUItoModelJumpMove() {
        SimpleMove expected = new SimpleMove(new Position(5,0), new Position(3, 2));
        Move actual = CuT.MovefromUItoModel(JUMP_MOVE_JSON);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void handleValidationShouldFail() {

    }

    private void setUpHandlevalidation() {

    }


    /*
    @Test
    public void handleValidationShouldPassSimpleMove() {
        Player redPlayer = new Player(TEST_RED_NAME, TEST_RED_ID);
        Player whitePlayer = new Player(TEST_WHITE_NAME, TEST_WHITE_ID);
        playerLobby = new PlayerLobby();
        playerLobby.signIn(redPlayer);
        playerLobby.signIn(whitePlayer);
        playerLobby.startGame(redPlayer, whitePlayer);

        CuT = new TurnController(playerLobby);

        Message message = CuT.handleValidation(JSON_MOVE_PASS, TEST_RED_ID);
        Assertions.assertEquals(message.getText(), TurnController.VALID_MOVE);
    }

    @Test
    public void handleValidationShouldFailSimpleMove() {
        Player redPlayer = new Player(TEST_RED_NAME, TEST_RED_ID);
        Player whitePlayer = new Player(TEST_WHITE_NAME, TEST_WHITE_ID);
        playerLobby = new PlayerLobby();
        playerLobby.signIn(redPlayer);
        playerLobby.signIn(whitePlayer);
        playerLobby.startGame(redPlayer, whitePlayer);

        CuT = new TurnController(playerLobby);

        Message message = CuT.handleValidation(JSON_MOVE_ERR, TEST_RED_ID);
        Assertions.assertEquals(message.getText(), TurnController.SIMPLE_MOVE_ERROR_MSG);
    }
    */
}
