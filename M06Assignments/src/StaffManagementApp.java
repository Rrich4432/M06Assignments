import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class StaffManagementApp extends Application {

    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Staff Management");

        // Database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/StaffDB", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // GUI components
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label miLabel = new Label("MI:");
        TextField miField = new TextField();
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        Label cityLabel = new Label("City:");
        TextField cityField = new TextField();
        Label stateLabel = new Label("State:");
        TextField stateField = new TextField();
        Label telephoneLabel = new Label("Telephone:");
        TextField telephoneField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> {
            String id = idField.getText();
            viewStaff(id);
        });

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> {
            String id = idField.getText();
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String mi = miField.getText();
            String address = addressField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String telephone = telephoneField.getText();
            String email = emailField.getText();
            insertStaff(id, lastName, firstName, mi, address, city, state, telephone, email);
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            String id = idField.getText();
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String mi = miField.getText();
            String address = addressField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String telephone = telephoneField.getText();
            String email = emailField.getText();
            updateStaff(id, lastName, firstName, mi, address, city, state, telephone, email);
        });

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(idLabel, 0, 0);
        GridPane.setConstraints(idField, 1, 0);
        GridPane.setConstraints(lastNameLabel, 0, 1);
        GridPane.setConstraints(lastNameField, 1, 1);
        GridPane.setConstraints(firstNameLabel, 0, 2);
        GridPane.setConstraints(firstNameField, 1, 2);
        GridPane.setConstraints(miLabel, 0, 3);
        GridPane.setConstraints(miField, 1, 3);
        GridPane.setConstraints(addressLabel, 0, 4);
        GridPane.setConstraints(addressField, 1, 4);
        GridPane.setConstraints(cityLabel, 0, 5);
        GridPane.setConstraints(cityField, 1, 5);
        GridPane.setConstraints(stateLabel, 0, 6);
        GridPane.setConstraints(stateField, 1, 6);
        GridPane.setConstraints(telephoneLabel, 0, 7);
        GridPane.setConstraints(telephoneField, 1, 7);
        GridPane.setConstraints(emailLabel, 0, 8);
        GridPane.setConstraints(emailField, 1, 8);
        GridPane.setConstraints(viewButton, 0, 9);
        GridPane.setConstraints(insertButton, 1, 9);
        GridPane.setConstraints(updateButton, 2, 9);

        gridPane.getChildren().addAll(idLabel, idField, lastNameLabel, lastNameField, firstNameLabel, firstNameField,
                miLabel, miField, addressLabel, addressField, cityLabel, cityField, stateLabel, stateField,
                telephoneLabel, telephoneField, emailLabel, emailField, viewButton, insertButton, updateButton);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewStaff(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Staff WHERE id = ?");
            statement.setString(1, id);
            // ResultSet resultSet = statement.executeQuery();

            // if (resultSet.next()) {
                
            // } else {
                
            // }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @param lastName
     * @param firstName
     * @param mi
     * @param address
     * @param city
     * @param state
     * @param telephone
     * @param email
     */
    private void insertStaff(String id, String lastName, String firstName, String mi, String address, String city, String state, String telephone, String email) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, mi);
            statement.setString(5, address);
            statement.setString(6, city);
            statement.setString(7, state);
            statement.setString(8, telephone);
            statement.setString(9, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new staff record was inserted successfully!");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStaff(String id, String lastName, String firstName, String mi, String address, String city, String state, String telephone, String email) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?, city=?, state=?, telephone=?, email=? WHERE id=?");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, mi);
            statement.setString(4, address);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, telephone);
            statement.setString(8, email);
            statement.setString(9, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Staff record with ID " + id + " was updated successfully!");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
