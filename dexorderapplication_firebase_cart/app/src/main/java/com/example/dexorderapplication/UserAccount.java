package com.example.dexorderapplication;

/**
 *  사용자 계정 정보모델 클래스
 */
public class UserAccount
{
    private String IdToken;    // Firebase Uid  (고유 토큰정보)
    private String emailId;    // 이메일 아이디
    private String password;   // 비밀번호
    private String name;       // 이름
    private String age;        // 나이
    private String place;      // 주거하는 동

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAge() { return age; }

    public void setAge(String age) { this.age = age; }

    public String getIdToken() {
        return IdToken;
    }

    public void setIdToken(String idToken) {
        IdToken = idToken;
    }

    public UserAccount() { }

    public String getEmailId() { return emailId; }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
