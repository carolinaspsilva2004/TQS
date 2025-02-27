package tqsdemo.employeemngr.service;

import org.springframework.stereotype.Service;
import tqsdemo.employeemngr.data.Employee;
import tqsdemo.employeemngr.data.EmployeeRepository;


import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // injected by SB in the constructor
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public boolean exists(String employeeName) {
        return employeeRepository.findByName(employeeName) != null;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
