package projectman;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController
{
	@FXML
	TextField auth_User;
	@FXML
	PasswordField auth_Password;
	
	@FXML
	public void auth_Verify()
	{
		if(true)//"admin".equals(auth_User.getText()) && "admin".equals(auth_Password.getText()))
		{
			((Stage)(auth_User.getScene().getWindow())).close();	//uzdaro esanti langa
			
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
				Parent root = loader.load();	//sukuria nauja langa is nurodyto FXML
				Stage mainWindowStage = new Stage();
				mainWindowStage.setScene(new Scene(root));
				
				MainWindowController controller = loader.getController();	//instance of the main window controller (currently primary controller)
				controller.setLoggedInUserRights("admin");	//TODO: check username & password to get access rights
				
				mainWindowStage.setTitle("Welcome, admin!");
				
				mainWindowStage.show();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void auth_Cancel()
	{
		((Stage)(auth_User.getScene().getWindow())).close();
	}
}
