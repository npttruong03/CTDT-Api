package api_CTDT.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.Account;
import api_CTDT.Models.ERole;
import api_CTDT.Models.Role;
import api_CTDT.payload.response.Message;
import api_CTDT.repositories.AccountRepository;
import api_CTDT.repositories.roleRepository;
import api_CTDT.request.LoginRequest;
import api_CTDT.request.SignupRequest;
import api_CTDT.security.jwt.JwtUtils;
import api_CTDT.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	roleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	 @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
	    
	    String jwtToken = jwtUtils.getJwtFromResponseCookie(jwtCookie);
	    
//	    Cookie cookie = new Cookie("nhaphocvku", jwtToken);
//	    cookie.setHttpOnly(true); 
//	    cookie.setMaxAge(2000);
//	    cookie.setPath("/api/");
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	    
//	    response.addCookie(cookie);	
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.SET_COOKIE, jwtCookie.toString());
	    LoggedInfo loggedInfo = new LoggedInfo();
	    loggedInfo.setToken(jwtToken);
	    loggedInfo.setRole(roles);
	    loggedInfo.setUsername(loginRequest.getUsername());
	    return ResponseEntity.ok().headers(headers).body(loggedInfo);
       

//	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//	        .body(new UserInfoResponse(userDetails.getId(),
//	                                    userDetails.getUsername(),
//	                                   userDetails.getEmail(),
//	                                   roles));
	  }

	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	    if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity.badRequest().body(new Message("Error: Username is already taken!"));
	    }

	    if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
	      return ResponseEntity.badRequest().body(new Message("Error: Email is already in use!"));
	    }

	    // Create new user's account
	    Account user = new Account(signUpRequest.getUsername(),
	                         signUpRequest.getEmail(),
	                         encoder.encode(signUpRequest.getPassword()));

	    Set<String> strRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	      roles.add(userRole);
//	    	return ResponseEntity.ok(new MessageResponse("Bạn chưa chọn quyền cho tài khoản"));
	    } 
//	    else {
//	      strRoles.forEach(role -> {
//	        switch (role) {
//	        case "admin":
//	          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	          roles.add(adminRole);
//
//	          break;
//	        case "taivu":
//	          Role taivuRole = roleRepository.findByName(ERole.ROLE_TAIVU)
//	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	          roles.add(taivuRole);
//
//	          break;
//	          
//	        case "ctsv":
//		          Role ctsvRole = roleRepository.findByName(ERole.ROLE_CTSV)
//		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//		          roles.add(ctsvRole);
//
//		          break;
//	        default:
//	          Role daotaoRole = roleRepository.findByName(ERole.ROLE_DAOTAO)
//	              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//	          roles.add(daotaoRole);
//	        }
//	      });
//	    }

	    user.setRoles(roles);
	    accountRepository.save(user);

	    return ResponseEntity.ok(new Message("User registered successfully!"));
	  }

	  @PostMapping("/signout")
	  public ResponseEntity<?> logoutUser() {
	    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	    return ResponseEntity.ok("Logout OK");
	  }
	  
//	  @PostMapping("/api/check-cookie")
//	    public String checkCookie(@RequestParam String cookieName, HttpServletRequest request) {
//	        // Lấy giá trị của cookie dựa vào tên
//	        String cookieValue = getCookieValue(cookieName, request);
//
//	        // Kiểm tra xem cookie có tồn tại hay không
//	        if (!StringUtils.isEmpty(cookieValue)) {
//	            return "Cookie exists. Value: " + cookieValue;
//	        } else {
//	            return "Cookie does not exist or has no value.";
//	        }
//	    }
	  
	  class LoggedInfo {
		  String token;
		  List<String> role;
		  String username;

		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public List<String> getRole() {
			return role;
		}
		public void setRole(List<String> roles) {
			this.role = roles;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		
		  
	  }
}
