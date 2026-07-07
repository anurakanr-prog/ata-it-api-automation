package com.atait.exam.utils;

import com.atait.exam.models.User;
import java.util.UUID;

public class TestDataUtil {
    // Method นี้จะคอยสร้าง Object User ที่มี Email และ Name ไม่ซ้ำกันเลยในแต่ละครั้งที่รัน
    public static User generateRandomUser() {
        // ใช้ UUID ของ Java มาตัดเอาแค่ 8 ตัวอักษรแรก เพื่อใช้สุ่มค่า
        String randomString = UUID.randomUUID().toString().substring(0, 8);

        return new User(
                "TestQA " + randomString,
                "qa.test." + randomString + "@example.com",
                "male",
                "active"
        );
    }
}
