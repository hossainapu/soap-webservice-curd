package com.hossain.service.response;

import com.hossain.business.entity.Employee;
import com.hossain.service.common.ServiceResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class CreateResponse extends ServiceResponse implements Serializable {

    Employee employee;

    public CreateResponse(boolean status, String message) {
        super(status, message);
    }
}
