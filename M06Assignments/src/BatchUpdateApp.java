import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchUpdateApp extends Application {

    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Batch Update Demo");

        // Database connection dialog box
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Database Connection");
        dialog.setHeaderText("Connect to the database");
        dialog.getDialogPane().setContent(new DBConnectionPanel());

        Button connectButton = new Button("Connect to Database");
        connectButton.setOnAction(e -> dialog.showAndWait());

        // Buttons to perform batch updates
        Button withoutBatchButton = new Button("Without Batch");
        withoutBatchButton.setOnAction(e -> insertRecords(false));

        Button withBatchButton = new Button("With Batch");
        withBatchButton.setOnAction(e -> insertRecords(true));

        // Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(connectButton, 0, 0);
        GridPane.setConstraints(withoutBatchButton, 0, 1);
        GridPane.setConstraints(withBatchButton, 0, 2);

        gridPane.getChildren().addAll(connectButton, withoutBatchButton, withBatchButton);

        Scene scene = new Scene(gridPane, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void insertRecords(boolean useBatch) {
        if (connection == null) {
            System.out.println("Not connected to database.");
            return;
        }

        long startTime = System.currentTimeMillis();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Temp VALUES (?, ?, ?)");

            for (int i = 0; i < 1000; i++) {
                double num1 = Math.random();
                double num2 = Math.random();
                double num3 = Math.random();

                statement.setDouble(1, num1);
                statement.setDouble(2, num2);
                statement.setDouble(3, num3);

                if (useBatch) {
                    statement.addBatch();
                } else {
                    statement.executeUpdate();
                }
            }

            if (useBatch) {
                statement.executeBatch();
            }

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            System.out.println("Insertion complete. Time taken: " + totalTime + " ms");

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DBConnectionPanel class for database connection dialog box
    class DBConnectionPanel extends GridPane {
        private TextField urlField = new TextField();
        private TextField usernameField = new TextField();
        private PasswordField passwordField = new PasswordField();

        public DBConnectionPanel() {
            setPadding(new Insets(10));
            setVgap(8);
            setHgap(10);

            Label urlLabel = new Label("URL:");
            Label usernameLabel = new Label("Username:");
            Label passwordLabel = new Label("Password:");

            Button connectButton = new Button("Connect");
            connectButton.setOnAction(e -> connectToDatabase());

            GridPane.setConstraints(urlLabel, 0, 0);
            GridPane.setConstraints(urlField, 1, 0);
            GridPane.setConstraints(usernameLabel, 0, 1);
            GridPane.setConstraints(usernameField, 1, 1);
            GridPane.setConstraints(passwordLabel, 0, 2);
            GridPane.setConstraints(passwordField, 1, 2);
            GridPane.setConstraints(connectButton, 0, 3);

            getChildren().addAll(urlLabel, urlField, usernameLabel, usernameField,
                    passwordLabel, passwordField, connectButton);
        }

        private void connectToDatabase() {
            String url = urlField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
