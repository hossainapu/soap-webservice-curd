package com.hossain.service.bean;

import com.hossain.service.common.ServiceResponse;
import com.hossain.service.request.CreateEmployeeRequest;
import com.hossain.service.request.DeleteRequest;
import com.hossain.service.request.GetEmployeeRequest;
import com.hossain.service.request.UpdateRequest;
import com.hossain.service.response.CreateResponse;
import com.hossain.service.response.GetEmployeeResponse;
import com.hossain.service.response.UpdateResponse;

import javax.ejb.Remote;

@Remote
public interface EmployeeService {
    CreateResponse createEmployee(CreateEmployeeRequest request);
    UpdateResponse updateEmployee(UpdateRequest request);
    GetEmployeeResponse listEmployee(GetEmployeeRequest request);
    ServiceResponse deleteEmployee(DeleteRequest request);
}
