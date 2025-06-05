package pages;

public class Validator {
    public static String validateSignup(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }

        if (!username.matches("^[a-zA-Z0-9_]{4,}$")) {
            return "Username must be at least 4 characters and contain only letters, numbers, or underscores.";
        }

        if (password == null || password.isEmpty()) {
            return "Password cannot be empty.";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter.";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter.";
        }

        if (!password.matches(".*\\d.*")) {
            return "Password must contain at least one digit.";
        }

        if (!password.matches(".*[!@#$%^&*()].*")) {
            return "Password must contain at least one special character (!@#$%^&*()).";
        }

        return null; // All checks passed
    }
}
