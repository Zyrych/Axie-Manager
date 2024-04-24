import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;


public class AxieController {
    private AxieView view;
    private List<AxieModel> axieList;
    private LoginView loginView; // Add reference to LoginView


    public AxieController(AxieView view, List<AxieModel> axieList, LoginView loginView) {
        this.view = view;
        this.axieList = axieList != null ? axieList : new ArrayList<>();
        this.loginView = loginView; // Initialize LoginView reference

        

        

        // Attach listeners to view buttons
        view.addAddButtonListener(new AddButtonListener());
        view.addEditButtonListener(new EditButtonListener());
        view.addDeleteButtonListener(new DeleteButtonListener());

        JButton logoutButton = view.getLogoutButton();
        if (logoutButton != null) {
            logoutButton.addActionListener(new LogoutButtonListener());
        }

        
        refreshUIDataFromDatabase();
    }


    public class AddButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            int userId = AuthenticationManager.getInstance().getCurrentUserId();
            String axieName = view.getInput("Enter axie name:");
            if (axieName != null && !axieName.isEmpty()) {
                String axieClass = view.getInput("Enter axie class:");
    
                // Create a new Model object
                AxieModel newAxie = new AxieModel();
                
                // Set the values using setter methods
                newAxie.setAxieName(axieName);
                newAxie.setAxieClass(axieClass);
                
                // Add the new Model object to the axieList
                axieList.add(newAxie);

                insertDataIntoDatabase(newAxie, userId);
                
                refreshUIDataFromDatabase();
            }
        }
    }



            class EditButtonListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AxieModel selectedAxie = view.getSelectedAxie();
                    if (selectedAxie != null) {
                        String newAxieName = view.getInput("Enter new axie name:", selectedAxie.getAxieName());
                        if (newAxieName != null && !newAxieName.isEmpty()) {
                            String newAxieClass = view.getInput("Enter new description:", selectedAxie.getAxieClass());
                            selectedAxie.setAxieName(newAxieName);
                            selectedAxie.setAxieClass(newAxieClass);

                            
                            updateDataInDatabase(selectedAxie);
                            refreshUIDataFromDatabase();
                        }
                    } else {
                        view.showMessage("Please select an axie to edit.");
                    }
                }
            }
        
        
             // Action listener for Delete button+
             class DeleteButtonListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AxieModel selectedAxie = view.getSelectedAxie();
                    if (selectedAxie != null) {
                        axieList.remove(selectedAxie);
                        deleteDataFromDatabase(selectedAxie); // Delete from database
                        view.updateAxieList(axieList);
                    } else {
                        view.showMessage("Please select an axie to delete.");
                    }
                }
                
                
                private void deleteDataFromDatabase(AxieModel axieToDelete) {
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    try {
                        connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Blue/Documents/Projects/AxieWithDatabase - Copy/Axie/database/appDatabase.db");
                        String sql = "DELETE FROM Axies WHERE Axie_Name = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, axieToDelete.getAxieName());
                        preparedStatement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (preparedStatement != null) preparedStatement.close();
                            if (connection != null) connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            
            class LogoutButtonListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle logout button click event
                    // Perform logout actions here
                    // For example: close the AxieView and show the login view
                    view.dispose(); // Close the AxieView
                    // Show the login view (assuming you have a reference to it)
                    loginView.setVisible(true);
                }
            }


            private void insertDataIntoDatabase(AxieModel newAxie, int userId) {
                // Database related code to insert data into the database table
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                
                try {
                    // Establish a connection to the SQLite database
                    connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Blue/Documents/Projects/AxieWithDatabase - Copy/Axie/database/appDatabase.db");
                    
                    // Prepare the INSERT statement
                    String sql = "INSERT INTO Axies (Axie_Name, Axie_Class, User_ID) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, newAxie.getAxieName());
                    preparedStatement.setString(2, newAxie.getAxieClass());
                    preparedStatement.setInt(3, userId);
                    
                    // Execute the INSERT statement
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    // Close the resources
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }


            private void refreshUIDataFromDatabase() {
                // Clear the existing axieList
                axieList.clear();
                
                // Database related code to retrieve updated data from the database
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
            
                try {
                    // Establish a connection to the SQLite database
                    connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Blue/Documents/Projects/AxieWithDatabase - Copy/Axie/database/appDatabase.db");
            
                    // Prepare the SELECT query with a WHERE clause to filter by User ID
                    String sql = "SELECT * FROM Axies WHERE User_ID = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, AuthenticationManager.getInstance().getCurrentUserId()); // Set the User ID
                    
                    // Execute the SELECT query
                    resultSet = preparedStatement.executeQuery();
                    
                    // Process the results
                    while (resultSet.next()) {
                        // Retrieve data from the result set
                        String axieName = resultSet.getString("Axie_Name");
                        String axieClass = resultSet.getString("Axie_Class");
            
                        // Create a Model object with retrieved data and add it to the axieList
                        AxieModel axie = new AxieModel();
                        axie.setAxieName(axieName);
                        axie.setAxieClass(axieClass);
                        axieList.add(axie);
                    }
            
                    // Update the view with the new data
                    view.updateAxieList(axieList);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Close the resources
                    try {
                        if (resultSet != null) resultSet.close();
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }


            private void updateDataInDatabase(AxieModel updatedAxie) {
                // Database related code to update data in the database table
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                
                try {
                    // Establish a connection to the SQLite database
                    connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/Blue/Documents/Projects/AxieWithDatabase - Copy/Axie/database/appDatabase.db");
                
                    // Prepare the UPDATE statement
                    String sql = "UPDATE Axies SET Axie_Name = ?, Axie_Class = ? WHERE Axie_Name = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, updatedAxie.getAxieName()); // New Axie_Name
                    preparedStatement.setString(2, updatedAxie.getAxieClass());
                    preparedStatement.setString(3, updatedAxie.getOriginalAxieName()); // Original Axie_Name
                        
                    // Execute the UPDATE statement
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    // Close the resources
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }


            public AxieView getView() {
                return view;
            }
}