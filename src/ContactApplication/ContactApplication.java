package ContactApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =
                FXMLLoader.load(getClass().getResource("ContactApplication.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Contact Viewer Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}