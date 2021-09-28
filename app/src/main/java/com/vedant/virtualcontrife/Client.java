package com.vedant.virtualcontrife;

public class Client {
    private String id , Clientid ,ClientSecret;

    public Client(String id, String clientid, String clientSecret, int _V) {
        this.id = id;
        Clientid = clientid;
        ClientSecret = clientSecret;
    }

    public String getId() {
        return id;
    }

    public String getClientid() {
        return Clientid;
    }

    public String getClientSecret() {
        return ClientSecret;
    }
}
