package com.example.demo.api;

import com.example.demo.client.BaseClient;
import com.example.demo.client.service.ClientService;
import com.example.demo.exceptions.RegisteredClientNotFoundException;
import com.example.demo.payload.UserSignupRequest;
import com.example.demo.user.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserRegistrationEndpoint {
    private Validator validator;
    private ClientService clientService;
    private UserRegistrationService registrationService;

    public UserRegistrationEndpoint(@Qualifier("validator") Validator validator,
                                    ClientService clientService,
                                    UserRegistrationService registrationService) {
        this.validator = validator;
        this.clientService = clientService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register/user")
    String registerNewAccount(@RequestParam("client_id") String clientId,
                              @RequestBody UserSignupRequest signupRequest,
                              BindingResult result) {
        validator.validate(signupRequest, result);
        if (result.hasErrors()) {
            throw new BadCredentialsException("bad credentials");
        }
        Optional<? extends BaseClient> registeredClientOptional =
                clientService.loadRegisteredClient(clientId);
        if (!registeredClientOptional.isPresent()) {
            throw new RegisteredClientNotFoundException(clientId);
        }
        registrationService.registerNewAccount(signupRequest, false);
        return "user saved";
    }

    @PostMapping("/register/admin")
    String registerAdmin(@RequestBody UserSignupRequest signupRequest,
                         BindingResult result){
        //validator.validate(signupRequest, result);
        if(result.hasErrors()){
            throw new BadCredentialsException("bad credentials");
        }
        registrationService.registerNewAccount(signupRequest, true);
        return "admin saved";
    }

}
