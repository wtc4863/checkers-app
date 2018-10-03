package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.*;

public class GetSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    //
    // Constants
    //
    static final String TITLE_ATTR = "title";
    static final String MESSAGE_ATTR = "message";
    static final String TEMPLATE_NAME = "signin.ftl";

    //
    // Attributes
    //
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
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");

        // Render the template
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, "Sign-in");
        vm.put(MESSAGE_ATTR, "Please enter your username to sign in.");
        return templateEngine.render(new ModelAndView(vm, TEMPLATE_NAME));
    }
}
