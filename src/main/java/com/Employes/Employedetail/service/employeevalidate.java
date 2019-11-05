package com.Employes.Employedetail.service;

import com.Employes.Employedetail.Entity.Designation;
import com.Employes.Employedetail.Entity.Employee;
import com.Employes.Employedetail.repository.DesignationRepo;
import com.Employes.Employedetail.repository.Employerepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
//VALIDATIONS FOR SIMPLE VALIDATIONS DONE IN SERVICE CLASS
public class employeevalidate {
    @Autowired
    Employerepo repo;
    @Autowired
    DesignationRepo depo;
//CHECKING DESIGNATIONS IS GREATER OR NOT THEN PRESENT DESIGNATIONS
    public boolean isGreaterThanEqualCurrentDesignation(Integer eid,String desg)
    {
        Employee employee=repo.findByEid(eid);
        float selfLevel=employee.getDesignation().getLevel();
        float parentLevel=depo.findByDesignation(desg).getLevel();
        if(selfLevel>=parentLevel)
            return true;
        else
            return false;
    }
//CHECKING CURRENT EMPLOYEE IS SMALLER THEN MANAGER DESIGNATION OR NOT
    public boolean isSmallerThanParent(Integer eid,String desg)
    {
        Employee employee=repo.findByEid(eid);
        if(employee.getManager()!=null)
        {
            float parentLevel=repo.findByEid(employee.getManager()).getDesignation().getLevel();
            float selfLevel=depo.findByDesignation(desg).getLevel();
            if(selfLevel>parentLevel)
                return true;
            else
                return false;
        }
        else
            return true;
    }
//CHECKING DESIGNATION IS GREATER THEN PRESENT DESIGNATION OR NOT
    public boolean isGreaterThanChilds(Integer eid,String desg)
    {
        float selfLevel=depo.findByDesignation(desg).getLevel();
        List<Employee> list=repo.findAllByManagerOrderByDesignation_levelAsc(eid);
        if(list.size()>0)
        {
            float childLevel=list.get(0).getDesignation().getLevel();
            if(selfLevel<childLevel)
                return true;
            else
                return false;
        }
        else
        {
            return true;
        }
    }
    //IF THE DESIGNATION IS GREATER THEN CURRENT DESIGNATION OR NOT
    public boolean isGreaterThanCurrentDesignation(Integer eid,String desg)
    {
        Employee employee=repo.findByEid(eid);
        float selfLevel=employee.getDesignation().getLevel();
        Designation designation=depo.findByDesignation(desg);
        float parentLevel=designation.getLevel();

        if(selfLevel>parentLevel)
            return true;
        else
            return false;
    }
//IF EMPLOYEE EXISTS OR NOT FOR GIVEN ID
    public boolean userExists(Integer eid)
    {
        Employee emp=repo.findByEid(eid);

        if(emp!=null)
            return true;
        else
            return false;
    }
//EMPLOYEE TABLE HAS DATA PRESENT OR NOT
    public boolean hasData(List<Employee> list)
    {
        if(list.size()>0)
            return true;
        else
            return false;
    }

    public boolean isDesignationValid(String desg)
    {
        Designation designation=depo.findByDesignation(desg);
        return (designation!=null);
    }
//NAME IS VALID OR NOT
    public boolean isValid(String name){
        if(name!=null)
        {
            if(name.trim().equals(""))
            {
                return false;
            }
            else if(name.matches(".*\\d.*"))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
//ID IS VALID OR NOT
    public boolean isValidId(Integer id){
      if(id.intValue()<0){
          return  false;
      }
      else{
          return true;
      }
    }

}




