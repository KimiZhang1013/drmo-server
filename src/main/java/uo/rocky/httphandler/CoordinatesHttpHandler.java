package uo.rocky.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import uo.rocky.entity.Coordinate;

import java.io.IOException;

public final class CoordinatesHttpHandler extends HttpHandlerPrinciple implements HttpHandler {
    public static final String CONTEXT = "/coordinates";
    private static final String ALLOW = "HEAD, POST";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    @Override
    public void handlePOSTRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getRequestHeaders();

        Coordinate coordinate = new Coordinate(new JSONObject(inputRequestBody(httpExchange.getRequestBody())));
        System.out.println(coordinate);
        try {
            coordinate.insertSQLite();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        httpExchange.getResponseHeaders().add("Allow", ALLOW);
        httpExchange.getResponseHeaders().add("Content-Type", CONTENT_TYPE);

        httpExchange.sendResponseHeaders(200, 0);

        outputResponseBody(httpExchange.getResponseBody(), "");
    }
}