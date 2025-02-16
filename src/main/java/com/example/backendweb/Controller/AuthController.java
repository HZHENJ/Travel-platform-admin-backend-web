package com.example.backendweb.Controller;

import com.example.backendweb.DTO.AuthResponseDTO;
import com.example.backendweb.DTO.ErrorResponseDTO;
import com.example.backendweb.Entity.Admin.Admin;
import com.example.backendweb.Repository.AdminRepository;
import com.example.backendweb.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://172.31.26.105:9091/")
@RestController
@RequestMapping("/admins-api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin admin) {
        Admin existingAdmin = adminRepository.findByUsername(admin.getUsername());
        if (existingAdmin != null && passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())) {
            String token = jwtService.generateToken(existingAdmin.getUsername());
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO("Invalid username or password."));
        }
    }

    @GetMapping("/admin/me")
    public ResponseEntity<?> getAdminDetails(HttpServletRequest request) {
        try {
            String token = jwtService.extractTokenFromRequest(request);
            if (token == null || !jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO("Invalid or expired token."));
            }

            String username = jwtService.extractUsername(token);
            Admin admin = adminRepository.findByUsername(username);

            if (admin == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Admin not found."));
            }

            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO("An error occurred while fetching admin details."));
        }
    }
}
