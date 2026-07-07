package com.atait.exam.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

// Annotation 2 บรรทัดนี้ช่วยให้การจัดการ JSON ยืดหยุ่นขึ้น
@JsonIgnoreProperties(ignoreUnknown = true) // ถ้ามีฟิลด์แปลกๆ ส่งมาให้ข้ามไปเลย ไม่ต้อง Error
@JsonInclude(JsonInclude.Include.NON_NULL)  // ถ้าฟิลด์ไหนไม่มีค่า (เช่น id ตอนสร้าง user ใหม่) ไม่ต้องส่งไปใน JSON
public class User {

    private Integer id; // ใช้ Integer แทน int เพื่อให้เก็บค่า null ได้เวลาเราทำ POST (สร้างใหม่ยังไม่มี id)
    private String name;
    private String email;
    private String gender;
    private String status;

    // 1. Default Constructor (จำเป็นมาก! ระบบใช้ตัวนี้เพื่อแปลง JSON กลับมาเป็น Object)
    public User() {}

    // 2. Constructor สำหรับใช้ตอนเราอยากสร้าง User ใหม่ด้วยตัวเอง
    public User(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }

    // --- ด้านล่างนี้คือ Getters และ Setters เพื่อให้คลาสอื่นดึงค่าหรือแก้ไขค่าได้ ---

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}