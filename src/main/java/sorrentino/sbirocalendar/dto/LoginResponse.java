package sorrentino.sbirocalendar.dto;

public class LoginResponse {

    private String token;
    private String username;
    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public LoginResponse(String token, String username, String group) {
        this.token    = token;
        this.username = username;
        this.group    = group;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
