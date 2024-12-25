package testData;

import java.util.List;
import java.util.Objects;

public class LoginData {
    private List<User> validUsers;
    private List<User> invalidUsers;
    private List<User> lockedUser;

    // return the users by type "valid" or "invalid"
    public List<User> getUsers(String Usertype) {
        if (Objects.equals(Usertype, "valid"))
        {
            return validUsers;
        }
        else if (Objects.equals(Usertype, "invalid"))
        {
            return invalidUsers;
        }
        else {
            return null;
        }
    }
    public void setValidUsers(List<User> validUsers) {
        this.validUsers = validUsers;
    }

    public List<User> getInvalidUsers() {
        return invalidUsers;
    }

    public void setInvalidUsers(List<User> invalidUsers) {
        this.invalidUsers = invalidUsers;
    }
    public static class User {
        private String username;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
