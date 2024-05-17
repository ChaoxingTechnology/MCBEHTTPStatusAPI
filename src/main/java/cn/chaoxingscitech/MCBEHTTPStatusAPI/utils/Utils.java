package cn.chaoxingscitech.MCBEHTTPStatusAPI.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String convertColorCodes(String text) {
        // 定义颜色映射关系
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("&0", "#000000");
        colorMap.put("&1", "#0000AA");
        colorMap.put("&2", "#00AA00");
        colorMap.put("&3", "#00AAAA");
        colorMap.put("&4", "#AA0000");
        colorMap.put("&5", "#AA00AA");
        colorMap.put("&6", "#FFAA00");
        colorMap.put("&7", "#AAAAAA");
        colorMap.put("&8", "#555555");
        colorMap.put("&9", "#5555FF");
        colorMap.put("&a", "#55FF55");
        colorMap.put("&b", "#55FFFF");
        colorMap.put("&c", "#FF5555");
        colorMap.put("&d", "#FF55FF");
        colorMap.put("&e", "#FFFF55");
        colorMap.put("&f", "#FFFFFF");

        // 替换Minecraft颜色字符为HTML颜色代码
        StringBuilder sb = new StringBuilder();
        boolean inColor = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '&') {
                inColor = true;
            } else if (inColor && colorMap.containsKey("&" + c)) {
                sb.append("<span style=\"color:").append(colorMap.get("&" + c)).append(";\">");
                inColor = false;
            } else {
                sb.append(c);
                if (c == 'r') {
                    sb.append("</span>");
                }
            }
        }

        return sb.toString();
    }

}
