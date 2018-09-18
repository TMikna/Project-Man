package ui.controllers;

import backend.datatypes.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.UUID;

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
			
			String userName = auth_User.getText();
			Employee loggedInUser = new Employee(userName, userName, UUID.randomUUID(), userName, userName, 9001, 25, userName.isEmpty() ? Employee.AccessRights.ADMIN : "Employee.AccessRights".equals(userName) ? Employee.AccessRights.EMPLOYEE : "teamManager".equals(userName) ? Employee.AccessRights.TEAM_MANAGER : "projectManager".equals(userName) ? Employee.AccessRights.PROJECT_MANAGER : "companyManager".equals(userName) ? Employee.AccessRights.COMPANY_MANAGER : Employee.AccessRights.NO_ACCESS); //TODO: check username & password to get actual object
			FXMLControllerExtractor<MainWindowController> mainWindow = new FXMLControllerExtractor<>("/ui/fxml/MainWindow.fxml", "Sveiki, " + userName + "!", new MainWindowController(loggedInUser));
		}
	}
	
	@FXML
	public void auth_Cancel()
	{
		((Stage)(auth_User.getScene().getWindow())).close();
	}
}
