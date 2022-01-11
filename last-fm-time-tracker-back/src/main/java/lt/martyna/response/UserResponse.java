package lt.martyna.response;

public class UserResponse {
    private final long id;
    private final String username;

    public UserResponse(String username, long id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }
}
