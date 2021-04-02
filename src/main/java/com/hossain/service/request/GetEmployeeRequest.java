package com.hossain.service.request;

import com.hossain.business.common.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetEmployeeRequest implements Serializable {

    String email;
    String phone;
    String name;
    Status status;

    int start;
    int limit;
}
