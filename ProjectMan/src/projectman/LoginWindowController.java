package projectman;

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
				Stage primaryStage = new Stage();
				primaryStage.setScene(new Scene(root));
				
				((MainWindowController)loader.getController()).setLoggedInUserRights("admin");	//TODO: check username & password to get access rights
				primaryStage.setTitle("Welcome, admin!");
				
				primaryStage.show();
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
