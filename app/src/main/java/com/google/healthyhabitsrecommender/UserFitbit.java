package com.google.healthyhabitsrecommender;
import com.google.gson.annotations.SerializedName;

public class UserFitbit {
    @SerializedName("access_token")
    private String accessToken;
    private String name;
    private String age;
    private String weight;
    private String heartRate;

    //*********** Setters *************
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    //*********** Getters *************
    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeartRate() {
        return heartRate;
    }
}
