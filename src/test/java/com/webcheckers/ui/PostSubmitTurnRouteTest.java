package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Game.Turn;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.model.SimpleMove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.BeforeEach;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSubmitTurnRouteTest {
  private PostSubmitTurnRoute CuT;

  private Player thisPlayer;
  private Player opponentPlayer;
  private Game game;
  private PlayerLobby playerLobby;
  private Gson gson;
  private Board board;
  private TemplateEngine templateEngine;

  private Request request;
  private Response response;
  private Session session;

  @BeforeEach
  void setup() {
    response = mock(Response.class);
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    when(session.id()).thenReturn("id");

    templateEngine = mock(TemplateEngine.class);

    playerLobby = new PlayerLobby();
    gson = new Gson();

    thisPlayer = new Player("current", "id");
    playerLobby.signIn(thisPlayer);
    opponentPlayer = new Player("opponent");
    playerLobby.signIn(opponentPlayer);

    playerLobby.startGame(thisPlayer, opponentPlayer);
    game = playerLobby.getGame(thisPlayer);
    board = game.getBoard();

    CuT = new PostSubmitTurnRoute(playerLobby, templateEngine);
  }

  @Test
  public void testGoodMove() {
    game.addMove(new SimpleMove(new Position(5, 0), new Position(4, 1)));

    String compare = "{\"text\":\"Turn submitted\",\"type\":\"info\"}";

    assertEquals(compare, CuT.handle(request, response));
    assertTrue(game.getTurn() == Turn.WHITE);
    assertFalse(game.getBoard().getSpace(new Position(5, 0)).doesHasPiece());
    assertTrue(game.getBoard().getSpace(new Position(4, 1)).doesHasPiece());
  }
}
