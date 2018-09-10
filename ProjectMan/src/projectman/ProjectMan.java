package projectman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProjectMan extends Application {

    @Override
    public void start(Stage loginWindowStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
        loginWindowStage.setTitle("Login");
        loginWindowStage.setScene(new Scene(root));
        loginWindowStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
