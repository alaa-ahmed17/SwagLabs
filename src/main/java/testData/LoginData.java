package testData;

import java.util.List;
import java.util.Objects;

public class LoginData {
    private List<User> validUsers;
    private List<User> invalidUsers;
    private List<User> lockedUsers;

    // return the users by type "valid" or "invalid"
    public List<User> getUsers(String userType) {
        return switch (userType.toLowerCase()) {
            case "valid" -> validUsers;
            case "invalid" -> invalidUsers;
            case "locked" -> lockedUsers;
            default -> null;
        };
    }
    public void setValidUsers(List<User> validUsers) {
        this.validUsers = validUsers;
    }

    public List<User> getvalidUsers() {
        return validUsers;
    }

    public void setInvalidUsers(List<User> invalidUsers) {
        this.invalidUsers = invalidUsers;
    }

    public List<User> getInvalidUsers() {
        return invalidUsers;
    }

    public void setLockedUsers(List<User> lockedUsers) {
        this.lockedUsers = lockedUsers;
    }

    public List<User> getLockedUsers() {
        return lockedUsers;
    }


    // Inner Class for User Data
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
