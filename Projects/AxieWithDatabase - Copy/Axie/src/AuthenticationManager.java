public class AuthenticationManager {
    private static AuthenticationManager instance;
    private int currentUserId;

    // Private constructor to prevent instantiation from outside
    private AuthenticationManager() {
        // Initialize currentUserId to indicate no user logged in
        currentUserId = -1;
    }

    // Singleton pattern to ensure only one instance of AuthenticationManager exists
    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    // Method to set the current user ID when a user logs in
    public void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    // Method to get the current user ID
    public int getCurrentUserId() {
        return currentUserId;
    }

    // Method to check if a user is currently logged in
    public boolean isLoggedIn() {
        return currentUserId != -1;
    }

    // Method to log out the current user
    public void logout() {
        currentUserId = -1;
    }
}
