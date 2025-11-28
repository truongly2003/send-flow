package com.example.sendflow.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EnvConfig {
    private static Dotenv dotenv;

    static {
        try {
            dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir"))  // tìm .env ở root project
                    .ignoreIfMissing()        //  không có file thì bỏ qua, dùng System env
                    .load();
        } catch (Exception e) {
            // Nếu lỗi gì thì vẫn dùng System env (Render/Railway)
            dotenv = null;
        }
    }

    public static String get(String key) {
        String value = System.getenv(key);           // Render inject
        if (value == null && dotenv != null) {
            value = dotenv.get(key);                 // local dùng .env
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }
}
