package com.atait.exam.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    // โหลดไฟล์ .env ขึ้นมาเพียงครั้งเดียวเมื่อคลาสถูกเรียกใช้
    private static final Dotenv dotenv = Dotenv.load();

    // Method สำหรับดึง Token
    public static String getApiToken() {
        return dotenv.get("GOREST_API_TOKEN");
    }

    public static String getUnauthorizeToken() {
        return dotenv.get("UNAUTHORIZE_API_TOKEN");
    }

    // Method สำหรับดึง Base URL ของ API (ใส่ไว้ที่นี่เพื่อความสะดวกในการจัดการ)
    public static String getBaseUrl() {
        return "https://gorest.co.in/public/v2";
    }
}