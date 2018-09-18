package ui.controllers;

import backend.datatypes.Employee;
import backend.datatypes.Project;
import backend.datatypes.Team;
import backend.server.DataStatic;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
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
			
			Employee loggedInUser = new Employee(userName, userName, UUID.randomUUID(), userName, userName, 9001, 25, userName.isEmpty() ? Employee.AccessRights.ADMIN : "employee".equals(userName) ? Employee.AccessRights.EMPLOYEE : "teamManager".equals(userName) ? Employee.AccessRights.TEAM_MANAGER : "projectManager".equals(userName) ? Employee.AccessRights.PROJECT_MANAGER : "companyManager".equals(userName) ? Employee.AccessRights.COMPANY_MANAGER : Employee.AccessRights.NO_ACCESS); //TODO: check username & password to get actual object
			Employee user1 = new Employee("test1", "testtest1", UUID.randomUUID(), "testPW1", "sysadmin", 9001, 25, Employee.AccessRights.ADMIN);
			Employee user2 = new Employee("test2", "testtest2", UUID.randomUUID(), "testPW2", "sysadmin", 9001, 25, Employee.AccessRights.ADMIN);
			Team team1 = new Team("testTeam1", Arrays.asList(loggedInUser, user1));
			Team team2 = new Team("testTeam2", Arrays.asList(loggedInUser, user2));
			Project project = new Project("testProject", Arrays.asList(team1, team2));
			team1.setProject(project);
			team1.setProject(project);
			
			DataStatic.getEmployees().addAll(Arrays.asList(loggedInUser, user1, user2));
			DataStatic.getTeams().addAll(Arrays.asList(team1, team2));
			DataStatic.getProjects().add(project);
			
			new FXMLControllerExtractor<>(
					"/ui/fxml/MainWindow.fxml",
					"Sveiki, " + userName + "!",
					new MainWindowController(loggedInUser));
		}
	}
	
	@FXML
	public void auth_Cancel()
	{
		((Stage)(auth_User.getScene().getWindow())).close();
	}
}
