package com.hossain.service.bean;

import com.hossain.business.bean.EmployeeBusiness;
import com.hossain.service.common.ServiceResponse;
import com.hossain.service.request.CreateEmployeeRequest;
import com.hossain.service.request.DeleteRequest;
import com.hossain.service.request.GetEmployeeRequest;
import com.hossain.service.request.UpdateRequest;
import com.hossain.service.response.CreateResponse;
import com.hossain.service.response.GetEmployeeResponse;
import com.hossain.service.response.UpdateResponse;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@Remote(EmployeeService.class)
@Stateless(name = "EmployeeService", mappedName = "simple-curd-EmployeeService")
@WebService(name = "EmployeeService", portName = "EmployeePort", serviceName = "EmployeeService")
public class EmployeeServiceBean implements EmployeeService {

    @EJB
    private EmployeeBusiness employeeManager;

    public EmployeeServiceBean() {
    }

    @Override
    @WebMethod
    public CreateResponse createEmployee(@WebParam(name = "request") CreateEmployeeRequest request) {
        if(request == null) {
            return new CreateResponse(false, "Employee request is invalid or null");
        }
        return employeeManager.createEmployee(request.getEmployee());
    }

    @Override
    @WebMethod
    public UpdateResponse updateEmployee(@WebParam(name = "request") UpdateRequest request) {
        if(request == null) {
            return new UpdateResponse(false, "Employee request is invalid or null");
        }
        return employeeManager.updateEmployee(request.getEmployee());
    }

    @Override
    @WebMethod
    public GetEmployeeResponse listEmployee(@WebParam(name = "request") GetEmployeeRequest request) {
        if(request == null) {
            return new GetEmployeeResponse(false, "Search request is invalid or null");
        }

        return employeeManager.searchEmployee(request);
    }

    @Override
    @WebMethod
    public ServiceResponse deleteEmployee(@WebParam(name = "request") DeleteRequest request) {
        if(request == null) {
            return new ServiceResponse(false, "Delete request is invalid or null");
        }
        return employeeManager.deleteEmployee(request);
    }
}
