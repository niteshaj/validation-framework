package org.nj.validator.controllers;


import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.nj.validator.bo.User;
import org.nj.validator.enums.error.ApplicationCode;
import org.nj.validator.exception.ApplicationException;
import org.nj.validator.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing")
class TestingController {

    @GetMapping
    public Response getValidResponse() {
        List<String> countries = new ArrayList<>(1);
        countries.add("India");
        return Response.createResponse(countries);
    }

    @GetMapping("/error")
    public void getErrorResponse() {
        throw new IllegalArgumentException("Error while getting details....");
    }

    @GetMapping("/application-error")
    public void getApplicationException() {
        throw new ApplicationException(ApplicationCode.INVALID_LOGIN_NAME)
            .add("Nitesh");
    }


    @PostMapping
    public Response createUser(@RequestBody @Valid User user) {
        User response = new User("Nitesh");
        return Response.createResponse(response);
    }
}
