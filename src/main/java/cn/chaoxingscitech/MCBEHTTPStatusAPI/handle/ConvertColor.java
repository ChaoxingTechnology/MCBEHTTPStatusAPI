package cn.chaoxingscitech.MCBEHTTPStatusAPI.handle;

import cn.chaoxingscitech.MCBEHTTPStatusAPI.utils.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ConvertColor implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        String query = exchange.getRequestURI().getQuery();
        String data = extractDataFromQuery(query);

        if (data == null) {
            sendResponse(exchange, 400, "Bad Request");
            return;
        }

        String convertedData = Utils.convertColorCodes(data);
        if (convertedData != null) {
            sendResponse(exchange, 200, convertedData);
        } else {
            sendResponse(exchange, 400, "Bad Request");
        }
    }

    private String extractDataFromQuery(String query) {
        String data = null;
        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length > 1 && keyValue[0].equalsIgnoreCase("data")) {
                data = keyValue[1];
                break;
            }
        }
        return data;
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(responseBytes);
        responseBody.close();
        exchange.close();
    }
}
