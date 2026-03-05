package sorrentino.sbirocalendar.service;

// src/main/java/com/tuoprogetto/service/CalendarService.java

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sorrentino.sbirocalendar.dto.CalendarDataResponse;
import sorrentino.sbirocalendar.entity.Availability;
import sorrentino.sbirocalendar.entity.User;
import sorrentino.sbirocalendar.repository.AvailabilityRepository;
import sorrentino.sbirocalendar.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private final UserRepository utenteRepository;
    private final AvailabilityRepository availabilityRepository;

    public CalendarService(UserRepository utenteRepository, AvailabilityRepository availabilityRepository) {
        this.utenteRepository = utenteRepository;
        this.availabilityRepository = availabilityRepository;
    }


    public CalendarDataResponse getCalendarData(String username) {

        User currentUser = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        CalendarDataResponse response = new CalendarDataResponse();

        // 1. Totale utenti nel gruppo
        response.totalUsers = (int) utenteRepository.count();

        // 2. I miei giorni
        response.myDays = availabilityRepository.findByUser_Id(currentUser.getId())
                .stream()
                .map(Availability::getDay)
                .collect(Collectors.toList());

        // 3. Disponibilità degli altri (Mappa: Giorno -> Lista Nomi)
        List<Availability> othersDisponibilita = availabilityRepository
                .findByUser_IdNot(currentUser.getId());

        response.othersAvailability = othersDisponibilita.stream()
                .collect(Collectors.groupingBy(
                        d -> String.valueOf(d.getDay()),
                        Collectors.mapping(d -> d.getUser().getUsername(), Collectors.toList())
                ));

        return response;
    }

    @Transactional
    public void toggleDay(String username, Integer day) {
        User currentUser = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Cerca se esiste già la disponibilità per quel giorno
        availabilityRepository.findByUser_idAndDay(currentUser.getId(), day).ifPresentOrElse(
                // Se esiste, eliminala (toglie la disponibilità)
                availabilityRepository::delete,

                // Se non esiste, creala (aggiunge la disponibilità)
                () -> {
                    Availability nuova = new Availability();
                    nuova.setUser(currentUser);
                    nuova.setDay(day);
                    nuova.setGroup(currentUser.getGroup());
                    availabilityRepository.save(nuova);
                }
        );
    }
}