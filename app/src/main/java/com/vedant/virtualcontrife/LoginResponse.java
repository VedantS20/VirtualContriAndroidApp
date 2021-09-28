package com.vedant.virtualcontrife;

public class LoginResponse {
    private Client client;
    private User user;
    private String accesstoken , refreshtoken , accesstokenexp , refreshtokenexp;

    public LoginResponse(Client client, User user, String accesstoken, String refreshtoken, String accesstokenexp, String refreshtokenexp) {
        this.client = client;
        this.user = user;
        this.accesstoken = accesstoken;
        this.refreshtoken = refreshtoken;
        this.accesstokenexp = accesstokenexp;
        this.refreshtokenexp = refreshtokenexp;
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public String getAccesstokenexp() {
        return accesstokenexp;
    }

    public String getRefreshtokenexp() {
        return refreshtokenexp;
    }
}
