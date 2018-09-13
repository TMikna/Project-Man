package projectman;

import backend.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
			
			String user = auth_User.getText();
			FXMLControllerExtractor<MainWindowController> mainWindow = new FXMLControllerExtractor<>("MainWindow.fxml", "Sveiki, " + user + "!");
			mainWindow.getController().setLoggedInUser(new Employee(user, user, user, user, user, 9001, 25, user.isEmpty()? Employee.ADMIN : "employee".equals(user) ? Employee.EMPLOYEE : "teamManager".equals(user) ? Employee.TEAM_MANAGER : "projectManager".equals(user) ? Employee.PROJECT_MANAGER : "companyManager".equals(user) ? Employee.COMPANY_MANAGER : Employee.NO_ACCESS)); //TODO: check username & password to get actual object
			mainWindow.execute();
		}
	}
	
	@FXML
	public void auth_Cancel()
	{
		((Stage)(auth_User.getScene().getWindow())).close();
	}
}
