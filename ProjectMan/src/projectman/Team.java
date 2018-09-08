/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectman;

import backend.Employee;
import java.util.List;

/**
 *
 * @author manfr
 */
class Team {
    List<Employee> teamPersonel;
    
    public Team(List<Employee> chosenPeople)
    {
        for(Employee e : chosenPeople)
            teamPersonel.add(e);
    }
    
}
