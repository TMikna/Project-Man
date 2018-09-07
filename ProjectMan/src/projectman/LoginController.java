package projectman;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController
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
				Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));	//sukuria nauja langa is nurodyto FXML
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Welcome, admin!");
				primaryStage.setScene(new Scene(root));
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
