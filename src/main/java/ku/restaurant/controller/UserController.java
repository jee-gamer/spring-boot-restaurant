package ku.restaurant.controller;

import jakarta.validation.Valid;
import ku.restaurant.dto.LoginRequest;
import ku.restaurant.dto.SignUpRequest;
import ku.restaurant.security.JwtUtils;
import ku.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequest request) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(jwtUtils.generateToken(userDetails.getUsername()));
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest request) {

        if (userService.userExists(request.getUsername())) {
            new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);

        }

        userService.createUser(request);
        return ResponseEntity.ok("User registered successfully!");
    }
}
