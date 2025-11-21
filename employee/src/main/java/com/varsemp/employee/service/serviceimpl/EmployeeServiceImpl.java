package com.varsemp.employee.service.serviceimpl;

import com.varsemp.employee.entity.Employee;
import com.varsemp.employee.repository.EmployeeRepo;
import com.varsemp.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepo employeeRepo;

    @Override
    public Employee createEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    @Override
    public  Employee getEmployeeById(Long id){
        return employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee Not Found"));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee){
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setSalary(employee.getSalary());
        return employeeRepo.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id){
        employeeRepo.deleteById(id);
    }
}
