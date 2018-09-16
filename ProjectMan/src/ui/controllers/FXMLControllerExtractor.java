package ui.controllers;

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
    public FXMLControllerExtractor(String FXMLFileName, String newWindowTitle, controllerClass controller)
    {
        this.controller = controller;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFileName));
            loader.setController(controller);
            Parent root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(newWindowTitle);
            window = scene.getWindow();
            
            if (controller instanceof SelfAwareController)
            {
                ((SelfAwareController) controller).whoAmI(stage, scene, window);
            }
    
            stage.showAndWait();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public FXMLControllerExtractor(String FXMLFileName, String newWindowTitle, Window owner, controllerClass controller)
    {
        this.controller = controller;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFileName));
            loader.setController(controller);
            Parent root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(newWindowTitle);
            window = scene.getWindow();
    
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            
            if (controller instanceof SelfAwareController)
            {
                ((SelfAwareController) controller).whoAmI(stage, scene, window);
            }
    
            stage.showAndWait();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
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
