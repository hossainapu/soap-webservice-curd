package com.hossain.business.bean;

import com.hossain.business.entity.Employee;
import com.hossain.service.common.ServiceResponse;
import com.hossain.service.request.DeleteRequest;
import com.hossain.service.request.GetEmployeeRequest;
import com.hossain.service.response.CreateResponse;
import com.hossain.service.response.GetEmployeeResponse;
import com.hossain.service.response.UpdateResponse;

import javax.ejb.Remote;

@Remote
public interface EmployeeBusiness {
    CreateResponse createEmployee(Employee employee);
    UpdateResponse updateEmployee(Employee employee);
    GetEmployeeResponse searchEmployee(GetEmployeeRequest request);
    ServiceResponse deleteEmployee(DeleteRequest request);
}
