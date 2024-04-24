import javax.swing.*;

public class App {

    

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        DatabaseManagerModel databaseManagerModel = new DatabaseManagerModel();
        LoginController loginController = new LoginController(loginView, databaseManagerModel);
        loginView.setVisible(true);                   
    }
}
