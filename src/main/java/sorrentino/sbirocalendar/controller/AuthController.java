package sorrentino.sbirocalendar.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sorrentino.sbirocalendar.configuration.JwtUtil;
import sorrentino.sbirocalendar.dto.LoginRequest;
import sorrentino.sbirocalendar.dto.LoginResponse;
import sorrentino.sbirocalendar.entity.Group;
import sorrentino.sbirocalendar.entity.User;
import sorrentino.sbirocalendar.repository.GroupRepository;
import sorrentino.sbirocalendar.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final GroupRepository groupRepository;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.groupRepository = groupRepository;
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenziali non valide");
        }

        // Trova o crea il group
        Group group = groupRepository .findByName(request.getGroup())
                .orElseGet(() -> groupRepository.save(new Group(request.getGroup())));

        // Aggiorna il group dell'utente
        user.setGroup(group);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), group.getName()));
    }

    // POST /api/auth/register — da usare solo in locale per creare i primi utenti
    // In produzione puoi rimuovere o proteggere questo endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody LoginRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username già esistente");
        }
        User user = new User(request.getUsername(),
                passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Utente creato: " + user.getUsername());
    }
}
