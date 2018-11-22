package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

public class PostConfirmAsyncRoute implements Route {

    //
    // Constants
    //

    //
    // Attributes
    //

    //
    // Constructor
    //

    public PostConfirmAsyncRoute() {
    }

    //
    // Methods
    //

    /**
     * Invoked when a request is made on this route's corresponding path e.g. '/hello'
     *
     * @param request  The request object providing information about the HTTP request
     * @param response The response object providing functionality for modifying the response
     * @return The content to be set in the response
     */
    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}
