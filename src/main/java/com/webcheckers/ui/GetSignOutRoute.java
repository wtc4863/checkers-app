package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

public class GetSignOutRoute implements Route {

  private TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  public GetSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
    Objects.requireNonNull(templateEngine, "templateEngine cannot be null");
    Objects.requireNonNull(playerLobby, "playerLobby cannot be null");

    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
  }

  public Object handle(Request request, Response response) {
    final Session httpSession = request.session();
    final String sessionID = httpSession.id();

    final String playerName = playerLobby.getPlayerNameBySessionID(sessionID);

    Map<String, Object> vm = new HashMap<>();

    if(playerName != null) {
      playerLobby.signOut(playerName);
      vm.put(GetHomeRoute.IS_SIGNED_IN, false);
      vm.put(GetHomeRoute.NUM_SIGNED_IN, playerLobby.getSignedInPlayers().size());
      vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
      response.redirect("/");
      return templateEngine.render(new ModelAndView(vm, GetHomeRoute.TEMPLATE_NAME));
    }
    return templateEngine.render(new ModelAndView(vm, GetHomeRoute.TEMPLATE_NAME));
  }

}
