package pl.ttwug.security.ttwug.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.ttwug.security.ttwug.form.UserRegistrationForm;
import pl.ttwug.security.ttwug.helper.JWTHelper;
import pl.ttwug.security.ttwug.model.ApplicationUser;
import pl.ttwug.security.ttwug.repository.ApplicationUserRepository;

import java.util.List;

import static pl.ttwug.security.ttwug.config.GeneralConstants.HEADER_STRING;


@RestController
@RequestMapping("/users")
public class UserController {
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody final UserRegistrationForm registrationForm) {
        ResponseEntity responseEntity;
        if (!registrationForm.checkForm()) {
            ApplicationUser user = registrationForm.createUser(bCryptPasswordEncoder.encode(registrationForm.getPassword()));
            if (applicationUserRepository.findByUsername(user.getUsername()).isPresent()) {
                responseEntity = new ResponseEntity("User already created", HttpStatus.CONFLICT);
            } else {
                applicationUserRepository.save(user);
                responseEntity = new ResponseEntity("User Created", HttpStatus.OK);
            }
        } else {
            responseEntity = new ResponseEntity(registrationForm.getErrors(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/user/show")
    public ResponseEntity<ApplicationUser> showMe(@RequestHeader(value = HEADER_STRING) final String token) {
        return new ResponseEntity<>(applicationUserRepository.findByUsername(JWTHelper.getSubject(token)).get(), HttpStatus.OK);
    }

    @GetMapping("/administrator/show")
    public ResponseEntity<List<ApplicationUser>> showAllUsers() {
        return new ResponseEntity<>(applicationUserRepository.findAll(), HttpStatus.OK);
    }

}