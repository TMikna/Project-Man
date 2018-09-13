///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ui.controllers;
//
//import java.io.IOException;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.Window;
//
///**
// *
// * @author TM
// */
//public class Loader {
//    
//    
//    
//     public void Loader(ActionEvent e, Window mainWindow, String FxmlPath, String title) 
//    {
//        
//        FXMLLoader loader = null;
//        //Window mainWindow = addTeamMember.getScene().getWindow();
//        try
//        {
//            loader = new FXMLLoader(getClass().getResource(FxmlPath));
//            Parent root = loader.load();
//            Stage newEmployeeWindowStage = new Stage();
//            newEmployeeWindowStage.setTitle(title);
//            newEmployeeWindowStage.setScene(new Scene(root));
//            
//            newEmployeeWindowStage.initModality(Modality.WINDOW_MODAL);
//            newEmployeeWindowStage.initOwner(mainWindow);
//            
//                        
//            if (loader != null && loader.getController() != null)
//            {
//                AddNewEmployeeController newEmployeeController = loader.getController();
//                System.out.println("FXMLLoader in AddNewEmployeeInitializer method inside if statement, MainWindow Controller class: " + loader);
//                newEmployeeController.setMain(this);
//            } 
//            else System.out.println("FXMLLoader loader for newEmployeeController is failed to return");
//            
//            newEmployeeWindowStage.showAndWait();     //TODO: make the main window in the background inaccessible
//            
//        } catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }
////        FXMLLoader loader = newWindowInitializer (e, "/ui/fxml/AddNewEmployee.fxml", "Enter employee details");
////        System.out.println("FXMLLoader in AddNewEmployeeInitializer method, MainWindow Controller class: " + loader);
////        if (loader != null && loader.getController() != null)
////        {
////            AddNewEmployeeController newEmployeeController = loader.getController();
////            System.out.println("FXMLLoader in AddNewEmployeeInitializer method inside if statement, MainWindow Controller class: " + loader);
////            newEmployeeController.setMain(this);
////        } 
////        else System.out.println("FXMLLoader loader for newEmployeeController is failed to return");
////            LOGGER.warning("newEmployeeController window does not have reference to MainWindowController");
//            
////[Tomas] Changed logic, now addEmployee button press adds employee itself
////            Employee createdEmployee = newEmployeeController.returnEmployee();     //get your brand shining new generated employee object here! Limited time offer!
////            
////            if (createdEmployee != null)
////            {
////                DataStatic.add(createdEmployee);
////                System.out.println(createdEmployee);
////            }
//        
//    }
//}
