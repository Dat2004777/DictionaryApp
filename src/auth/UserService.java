package auth;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("admin", "admin", User.Role.ADMIN));
        users.add(new User("datj", "123", User.Role.USER));
    }

    public User login(String username, String pw) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(pw)) {
                return u;
            }
        }
        
        return null;
    }
}
