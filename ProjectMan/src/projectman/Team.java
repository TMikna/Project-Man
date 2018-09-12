/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectman;

import backend.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manfr
 */
class Team {
    String Name;
    int capacity;
    
    List<Employee> teamPersonel = new ArrayList();
    
    public Team(List<Employee> chosenPeople, String Name)
    {
        for(Employee e : chosenPeople)
            teamPersonel.add(e);
    }
    
}
