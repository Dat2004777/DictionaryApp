
package auth;


public class User {
    private String username;
    private String password;
    private Role role;
    
    public enum Role {
        ADMIN, USER
    }

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
    
    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }
}
