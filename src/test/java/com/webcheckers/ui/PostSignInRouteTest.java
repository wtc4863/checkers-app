package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;
import spark.utils.Assert;

public class PostSignInRouteTest {

    // Component Under Test
    private PostSignInRoute CuT;

    //Friendly Objects
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    // Mocked Objects
    private Request request;
    private Response response;

}
