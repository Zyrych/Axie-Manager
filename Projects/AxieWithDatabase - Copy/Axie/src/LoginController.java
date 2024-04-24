import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LoginController implements ActionListener {
    private LoginView loginView;
    private DatabaseManagerModel databaseManager;
    private List<AxieModel> axieList;

    public LoginController(LoginView loginView, DatabaseManagerModel databaseManager) {
        this.loginView = loginView;
        this.databaseManager = databaseManager;
        this.axieList = new ArrayList<>();

        // Add action listener to the login button
        loginView.addLoginListener(this);

        // Add action listener to the register button
        loginView.addRegisterListener(e -> openRegistrationView());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginView.getLoginButton()) {
            // Handle login button click event
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // Validate username and password
            if (validateUsernameAndPassword(username, password)) {
                // Authentication successful
                // Proceed to open the main application window or perform other actions
                int userId = databaseManager.getUserId(username);
                if (userId != -1) {
                    AuthenticationManager.getInstance().setCurrentUserId(userId);
                    openAxieView();
                    loginView.setVisible(false); // Close the login window
                } else {
                    // Handle case where user ID is not found
                    System.err.println("User ID not found for username: " + username);
                }
            } else {
                // Authentication failed
                // Display error message to the user
                showLoginFailedWarning();
            }
        }
    }

    private boolean validateUsernameAndPassword(String username, String password) {
        // Check if username and password are not empty
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        // Query the database to check if the provided username and password match any existing user
        return databaseManager.authenticateUser(username, password);
    }

    private void openRegistrationView() {
        RegistrationView registrationView = new RegistrationView();
        RegistrationController registrationController = new RegistrationController(registrationView, databaseManager);
        registrationView.setVisible(true);
        loginView.setVisible(false); // Close the login window
    }

    private void openAxieView() {
        AxieView axieView = new AxieView();
        AxieController axieController = new AxieController(axieView, axieList, loginView);
        axieView.setVisible(true); // Make the AxieView visible
    }

    private void showLoginFailedWarning() {
        JOptionPane.showMessageDialog(loginView, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.WARNING_MESSAGE);
    }
}
