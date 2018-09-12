package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

<<<<<<< HEAD:ProjectMan/src/backend/datatypes/Team.java

import backend.datatypes.Employee;
=======
import backend.Employee;
import java.util.ArrayList;
>>>>>>> master:ProjectMan/src/projectman/Team.java
import java.util.List;

/**
 *
 * @author manfr
 */
<<<<<<< HEAD:ProjectMan/src/backend/datatypes/Team.java
public class Team {
    List<Employee> teamPersonel;
=======
class Team {
    String Name;
    int capacity;
    
    List<Employee> teamPersonel = new ArrayList();
>>>>>>> master:ProjectMan/src/projectman/Team.java
    
    public Team(List<Employee> chosenPeople)
    {
        for(Employee e : chosenPeople)
            teamPersonel.add(e);
    }
    
}
