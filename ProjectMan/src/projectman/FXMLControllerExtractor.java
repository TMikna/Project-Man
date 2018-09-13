package projectman;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class FXMLControllerExtractor<controllerClass>
{
    private controllerClass controller;
    private Stage stage = new Stage();
    private Scene scene = null;
    private Window window = null;
    public FXMLControllerExtractor(String FXMLFileName, String newWindowTitle)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFileName));
            Parent root = loader.load();
            scene = new Scene(root);
            window = scene.getWindow();
            stage.setScene(scene);
            stage.setTitle(newWindowTitle);
            controller = ((controllerClass) loader.getController());
            
            if (controller instanceof SelfAwareController)
            {
                ((SelfAwareController) controller).whoAmI(stage, scene, window);
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public FXMLControllerExtractor(String FXMLFileName, String newWindowTitle, Window owner)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFileName));
            Parent root = loader.load();
            scene = new Scene(root);
            window = scene.getWindow();
            stage.setScene(scene);
            stage.setTitle(newWindowTitle);
            controller = ((controllerClass) loader.getController());
    
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            
            if (controller instanceof SelfAwareController)
            {
                ((SelfAwareController) controller).whoAmI(stage, scene, window);
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void execute()
    {
        stage.showAndWait();
    }
    
    public controllerClass getController()
    {
        return controller;
    }
    
    public Stage getStage()
    {
        return stage;
    }
    
    public Scene getScene()
    {
        return scene;
    }
    
    public Window getWindow()
    {
        return window;
    }
}
