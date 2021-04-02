package com.hossain.business.bean;
import com.hossain.business.common.Utils;
import com.hossain.business.entity.Employee;
import com.hossain.database.bean.DatabaseService;
import com.hossain.service.common.ServiceResponse;
import com.hossain.service.request.DeleteRequest;
import com.hossain.service.request.GetEmployeeRequest;
import com.hossain.service.response.CreateResponse;
import com.hossain.service.response.GetEmployeeResponse;
import com.hossain.service.response.UpdateResponse;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Remote(EmployeeBusiness.class)
@Stateless(name = "EmployeeBusinessEJB", mappedName = "simple-curd-EmployeeBusinessBean")
public class EmployeeBusinessBean implements EmployeeBusiness {

    private final Logger LOGGER = Logger.getAnonymousLogger();
    @EJB
    private DatabaseService manager;

    public EmployeeBusinessBean() {
    }

    @Override
    public CreateResponse createEmployee(Employee employee) {
        if(employee == null) {
            return new CreateResponse(false, "Employee record is required");
        }
        if(!Utils.isOk(employee.getFirstName())) {
            return new CreateResponse(false, "First Name is required");
        }
        if(!Utils.isOk(employee.getLastName())) {
            return new CreateResponse(false, "Last Name is required");
        }
        if(!Utils.isOk(employee.getEmail())) {
            return new CreateResponse(false, "Email address is required");
        }
        if(!Utils.isOk(employee.getPassword())) {
            return new CreateResponse(false, "Password is required");
        }
        if(!Utils.isOk(employee.getPhoneNumber())) {
            return new CreateResponse(false, "Phone number is required");
        }
        if(employee.getStatus() == null) {
            return new CreateResponse(false, "Status is required");
        }

        try {
            if(manager.phoneTaken(employee.getPhoneNumber())) {
                return new CreateResponse(false, "Phone number already exists");
            }
            if(manager.emailTaken(employee.getEmail())) {
                return new CreateResponse(false, "Email already exists");
            }
            com.hossain.database.model.Employee employeeEO = new com.hossain.database.model.Employee();
            employeeEO.setFirstName(employee.getFirstName());
            employeeEO.setLastName(employee.getLastName());
            employeeEO.setDepartmentName(employee.getDepartmentName());
            employeeEO.setEmail(employee.getEmail());
            employeeEO.setPhoneNumber(employee.getPhoneNumber());
            employeeEO.setStatus(employee.getStatus().getValue());
            employeeEO.setPassword(Utils.encodePassword(employee.getPassword()));
            employeeEO = manager.save(employeeEO);
            return new CreateResponse(true, "Employee created successfully").setEmployee(new Employee(employeeEO));
        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, "Error:", t);
            return new CreateResponse(false, "Internal service error. Please contact with admin");
        }
    }

    @Override
    public UpdateResponse updateEmployee(Employee employee) {
        if(employee == null) {
            return new UpdateResponse(false, "Employee record is required");
        }
        if(employee.getId() == null) {
            return new UpdateResponse(false, "Record id is required");
        }
        if(!Utils.isOk(employee.getFirstName())) {
            return new UpdateResponse(false, "First Name is required");
        }
        if(!Utils.isOk(employee.getLastName())) {
            return new UpdateResponse(false, "Last Name is required");
        }
        if(!Utils.isOk(employee.getEmail())) {
            return new UpdateResponse(false, "Email address is required");
        }
        if(!Utils.isOk(employee.getPassword())) {
            return new UpdateResponse(false, "Password is required");
        }
        if(!Utils.isOk(employee.getPhoneNumber())) {
            return new UpdateResponse(false, "Phone number is required");
        }
        if(employee.getStatus() == null) {
            return new UpdateResponse(false, "Status is required");
        }

        com.hossain.database.model.Employee employeeDup = manager.findByFieldName(com.hossain.database.model.Employee.class, "email", employee.getEmail());
        if(employeeDup != null && employeeDup.equals(employee.getEmail()) && !employeeDup.getId().equals(employee.getId())) {
            return new UpdateResponse(false, "Email already exists");
        }

        com.hossain.database.model.Employee employeePhoneDup = manager.findByFieldName(com.hossain.database.model.Employee.class, "email", employee.getEmail());
        if(employeePhoneDup != null && employeePhoneDup.equals(employee.getPhoneNumber()) && !employeeDup.getId().equals(employee.getId())) {
            return new UpdateResponse(false, "Phone already exists");
        }

        com.hossain.database.model.Employee employeeEO = manager.findByFieldName(com.hossain.database.model.Employee.class, "id", employee.getId());
        if(employeeEO == null) {
            return new UpdateResponse(false, "Invalid employee id");
        }

        employeeEO.setFirstName(employee.getFirstName());
        employeeEO.setLastName(employee.getLastName());
        employeeEO.setDepartmentName(employee.getDepartmentName());
        employeeEO.setEmail(employee.getEmail());
        employeeEO.setPhoneNumber(employee.getPhoneNumber());
        employeeEO.setStatus(employee.getStatus().getValue());
        employeeEO.setPassword(Utils.encodePassword(employee.getPassword()));
        employeeEO = manager.update(employeeEO);
        return new UpdateResponse(true, "Employee updated successfully").setEmployee(new Employee(employeeEO));
    }

    @Override
    public GetEmployeeResponse searchEmployee(GetEmployeeRequest request) {
        String sql = "select o from Employee o ";
        String count = "select count(o.id) from Employee o ";
        String where = "where 1=1 ";
        Map<String, Object> params = new HashMap<>();
        if(Utils.isOk(request.getEmail())) {
            where += " and o.email=:email ";
            params.put("email", request.getEmail().trim());
        }
        if(Utils.isOk(request.getPhone())) {
            where += " and o.phoneNumber =:phoneNumber ";
            params.put("phoneNumber", request.getPhone());
        }
        if(request.getStatus() != null) {
            where += " and o.status =:status ";
            params.put("status", request.getStatus().getValue());
        }
        if(Utils.isOk(request.getName())) {
            where += " and (o.first_name like :name or o.last_name like :name) ";
            params.put("name", "%"+request.getName().trim()+"%");
        }
        if(request.getLimit() ==0) {
            request.setLimit(10);
        }

        try {
            List<com.hossain.database.model.Employee> employeeList = manager.findByQuery(sql + where + " order by o.id desc", com.hossain.database.model.Employee.class, params, request.getStart(), request.getLimit());
            if(employeeList != null && employeeList.size() >0) {
                int cnt = manager.findCount(count+where, params);
                return new GetEmployeeResponse(true, null).setEmployeeList(employeeList.stream().map(employee -> new Employee(employee)).collect(Collectors.toList())).setTotalCount(cnt);
            }
        } catch (Throwable t) {
            return new GetEmployeeResponse(false, "Error:"+t.getMessage());
        }
        return new GetEmployeeResponse(true, "No record found");
    }

    @Override
    public ServiceResponse deleteEmployee(DeleteRequest request) {
        if(request.getId() == null) {
            return new ServiceResponse(false, "Employee id is required");
        }

        manager.delete(request.getId(), com.hossain.database.model.Employee.class);
        return new ServiceResponse(true, "Record deleted successfully");
    }
}
