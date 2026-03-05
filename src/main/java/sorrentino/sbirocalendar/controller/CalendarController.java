package sorrentino.sbirocalendar.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sorrentino.sbirocalendar.dto.CalendarDataResponse;
import sorrentino.sbirocalendar.dto.ToggleRequest;
import sorrentino.sbirocalendar.service.CalendarService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // ==========================================
    // 2. ENDPOINT RECUPERO DATI CALENDARIO
    // ==========================================
    @GetMapping("/calendar/data")
    public ResponseEntity<CalendarDataResponse> getCalendarData(Principal principal) {

        // TODO: Estrarre l'utente dal token (authHeader)
        // TODO: Chiamare il Service per recuperare i giorni dell'utente e degli altri

        // Costruiamo una risposta finta per far felice il frontend
        CalendarDataResponse data = calendarService.getCalendarData(principal.getName());

        return ResponseEntity.ok(data);
    }
    @PostMapping("/calendar/toggle")
    public ResponseEntity<?> toggleDay(
            Principal principal,
            @RequestBody @Valid ToggleRequest request) {

        calendarService.toggleDay(principal.getName(), request.day);
        return ResponseEntity.ok().body(Map.of("success", true));
    }
}
