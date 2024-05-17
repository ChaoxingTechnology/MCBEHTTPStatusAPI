package cn.chaoxingscitech.MCBEHTTPStatusAPI.handle;

import cn.chaoxingscitech.MCBEHTTPStatusAPI.utils.BedrockQuery;
import cn.chaoxingscitech.MCBEHTTPStatusAPI.utils.Utils;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetData implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String ip = "";
        String port = "";

        // 解析参数
        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length > 1) {
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.equalsIgnoreCase("ip")) {
                    ip = value;
                } else if (key.equalsIgnoreCase("port")) {
                    port = value;
                }
            }
        }

        BedrockQuery queryResult = BedrockQuery.create(ip, Integer.parseInt(port));

        if (queryResult.isOnline()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = (JsonObject) gson.toJsonTree(queryResult);
            jsonObject.addProperty("html", Utils.convertColorCodes(queryResult.getMotd()));
            String jsonData = gson.toJson(jsonObject);
            byte[] data = jsonData.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, data.length);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(data);
            responseBody.close();
        } else {
            String errorMessage = "Unable to connect to server or server not online";
            byte[] errorData = errorMessage.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, errorData.length);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(errorData);
            responseBody.close();
        }
    }
}

