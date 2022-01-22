package com.bricolage.bricolageback.dto;

public class StringResponse {

    private String response;

    public StringResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "StringResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}
