package uo.rocky.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import uo.rocky.entity.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class RegistrationHttpHandler extends HttpHandlerBase implements HttpHandler {
    public static final String GET_CONTEXT = "/registration";
    private static final String GET_ALLOW = "GET, HEAD, POST";
    private static final String GET_CONTENT_TYPE = "application/json; charset=utf-8";

    @Override
    public void handleGETRequest(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        String[] params = -1 == uri.indexOf('?') ? new String[]{} : uri.substring(uri.indexOf('?') + 1).split("&");
        System.out.println("params: " + Arrays.toString(params));
        Map<String, String> paramsMap = new HashMap<>();

        for (String param : params) {
            String[] tempStrings = param.split("=");
            if (2 == tempStrings.length) {
                paramsMap.put(tempStrings[0].toUpperCase(), tempStrings[1]);
            } else {
                // TODO
                System.out.println("煞笔吧，一个参数的位置写两个等号。。。");
            }
        }
        try {
            StringJoiner results = new StringJoiner(",", "[", "]");
            for (User user : User.selectSQLite(paramsMap)) {
                results.add(user.toJSONString());
            }
            System.out.println(results);

            httpExchange.getResponseHeaders().add(ResponseHeader.CONTENT_TYPE.call(), GET_CONTENT_TYPE);
            httpExchange.sendResponseHeaders(200, results.toString().getBytes(UTF_8).length);

            outputResponseBody(httpExchange.getResponseBody(), results.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleHEADRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add(ResponseHeader.ALLOW.call(), GET_ALLOW);
        httpExchange.sendResponseHeaders(200, -1);
    }

    @Override
    public void handlePOSTRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getRequestHeaders();

        User user = User.valueOf(new JSONObject(inputRequestBody(httpExchange.getRequestBody())));
        System.out.println(user);
        try {
            System.out.println(user.insertSQLite() ? "Insert succeed!" : "Insert failed!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        httpExchange.sendResponseHeaders(200, -1);
    }
}