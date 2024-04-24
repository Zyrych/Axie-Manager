import java.sql.*;

public class DatabaseManagerModel {
    private static final String JDBC_URL = "jdbc:sqlite:C:/Users/Blue/Documents/Projects/AxieWithDatabase - Copy/Axie/database/appDatabase.db";



    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if any rows are returned
                return rs.next(); // Returns true if at least one row is found, indicating authentication success
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return false; // Authentication failed due to database error
        }
    }



    public boolean doesUsernameExists(String username) {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL);
            
            // Prepare SQL statement to check if username exists
            String sql = "SELECT COUNT(*) FROM User WHERE Username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            
            // Execute query
            resultSet = preparedStatement.executeQuery();
            
            // Check if username exists
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception
            }
        }

        return exists;
    }




    public boolean registerUser(String username, String password, String email) {
        boolean success = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL);
            
            // Prepare SQL statement to insert new user
            String sql = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            
            // Execute update (insert) query
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Check if insertion was successful
            if (rowsAffected > 0) {
                // Retrieve the generated user ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        // Now you have the user ID, you can use it as needed
                        System.out.println("User registered successfully with ID: " + userId);
                        success = true;
                    } else {
                        System.err.println("Failed to retrieve user ID after registration");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception
            }
        }

        return success;
    }


    public int getUserId(String username) {
        String query = "SELECT ID FROM User WHERE Username = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }
        return -1; // User ID not found or error occurred
    }


    
}

