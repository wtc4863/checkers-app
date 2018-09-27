package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.*;

public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;

    public GetSignInRoute(final TemplateEngine templateEngine) {
        // Validate the template engine
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;

        LOG.config("Get SignInRoute is initialized.");
    }

    /**
     * Render the WebCheckers sign-in page.
     *
     * @param request
     *      the HTTP request
     * @param response
     *      the HTTP response
     *
     * @return
     *      the rendered HTML for the sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");

        // Render the template
        Map<String, Object> model = new HashMap<>();
        model.put("message", "Please enter your username to sign in.");
        return templateEngine.render(new ModelAndView(model, "signin.ftl"));
    }
}
