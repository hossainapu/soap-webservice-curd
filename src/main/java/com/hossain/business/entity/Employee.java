package com.hossain.business.entity;

import com.hossain.business.common.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee implements Serializable {

    Integer id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String departmentName;
    String password;
    Status status;

    public Employee(com.hossain.database.model.Employee employeeEO) {
        if(employeeEO != null) {
            this.id = employeeEO.getId();
            this.firstName = employeeEO.getFirstName();
            this.lastName = employeeEO.getLastName();
            this.email = employeeEO.getEmail();
            this.phoneNumber = employeeEO.getPhoneNumber();
            this.departmentName = employeeEO.getDepartmentName();
            this.password = employeeEO.getPassword();
            this.status = Status.getStatus(employeeEO.getStatus());
        }
    }
}
