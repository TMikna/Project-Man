package ui.controllers;

import backend.datatypes.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class EventViewerController implements Initializable, SelfAwareController
{
    @FXML
    private TextField eventName;
    @FXML
    private TextArea eventComment;
    @FXML
    private VBox targetList;
    
    private Event event;
    
    private Stage stage;
    private Scene scene;
    private Window window;
    @Override
    public void whoAmI(Stage stage, Scene scene, Window window)
    {
        this.stage = stage;
        this.scene = scene;
        this.window = window;
        
        stage.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal)
            {
                stage.close();
            }
        });
    }
    
    public EventViewerController(Event event)
    {
        this.event = event;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        eventName.setText(event.getEventName());
        eventComment.setText(event.getEventDescription());
    
        event.getTargets()
             .forEach(employee -> targetList.getChildren()
                                            .add(new TextField(employee.getName() + " " + employee.getLastName())
                                            {{
                                                setPrefWidth(200);  //for some reason USE_COMPUTED_SIZE would cut out some text even if there was space
                                                setEditable(false);
                                                setFocusTraversable(false);
                                            }}));
    }
}
