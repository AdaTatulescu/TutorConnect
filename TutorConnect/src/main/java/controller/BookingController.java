package com.tutorconnect.controller;

import com.tutorconnect.model.Booking;
import com.tutorconnect.model.Meditation;
import com.tutorconnect.model.User;
import com.tutorconnect.repository.BookingRepository;
import com.tutorconnect.repository.MeditationRepository;
import com.tutorconnect.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class BookingController {

    private final BookingRepository bookingRepository;

    private final MeditationRepository meditationRepository;

    private final UserRepository userRepository;

    public BookingController(
            BookingRepository bookingRepository,
            MeditationRepository meditationRepository,
            UserRepository userRepository
    ) {

        this.bookingRepository = bookingRepository;
        this.meditationRepository = meditationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/request-session/{id}")
    public String showRequestForm(
            @PathVariable Long id,
            Model model
    ) {

        Meditation meditation =
                meditationRepository.findById(id)
                        .orElse(null);

        Booking booking = new Booking();

        booking.setTutorName(
                meditation.getTutorName()
        );

        booking.setMeditationTitle(
                meditation.getTitle()
        );

        booking.setSubject(
                meditation.getSubject()
        );

        model.addAttribute(
                "booking",
                booking
        );

        return "request-session";
    }

    @PostMapping("/request-session")
    public String requestSession(
            @ModelAttribute Booking booking,
            Principal principal
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        booking.setStudentName(
                user.getName()
        );

        booking.setStatus(
                "PENDING"
        );

        bookingRepository.save(
                booking
        );

        return "redirect:/my-requests";
    }

    @GetMapping("/bookings")
    public String showBookings(
            Model model,
            Principal principal
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        List<Booking> bookings =
                bookingRepository.findByTutorName(
                        user.getName()
                );

        model.addAttribute(
                "bookings",
                bookings
        );

        model.addAttribute(
                "loggedUser",
                user.getName()
        );

        return "bookings";
    }

    @GetMapping("/my-requests")
    public String showMyRequests(
            Model model,
            Principal principal
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        List<Booking> bookings =
                bookingRepository.findByStudentName(
                        user.getName()
                );

        model.addAttribute(
                "bookings",
                bookings
        );

        return "my-requests";
    }

    @GetMapping("/schedule-session/{id}")
    public String showScheduleForm(
            @PathVariable Long id,
            Model model
    ) {

        Booking booking =
                bookingRepository.findById(id)
                        .orElse(null);

        model.addAttribute(
                "booking",
                booking
        );

        return "schedule-session";
    }

    @PostMapping("/schedule-session/{id}")
    public String scheduleSession(
            @PathVariable Long id,
            @ModelAttribute Booking booking
    ) {

        Booking existingBooking =
                bookingRepository.findById(id)
                        .orElse(null);

        existingBooking.setSessionDate(
                booking.getSessionDate()
        );

        existingBooking.setSessionTime(
                booking.getSessionTime()
        );

        existingBooking.setStatus(
                "ACCEPTED"
        );

        bookingRepository.save(
                existingBooking
        );

        return "redirect:/bookings";
    }

    @GetMapping("/reject-booking/{id}")
    public String rejectBooking(
            @PathVariable Long id
    ) {

        Booking booking =
                bookingRepository.findById(id)
                        .orElse(null);

        booking.setStatus(
                "REJECTED"
        );

        bookingRepository.save(
                booking
        );

        return "redirect:/bookings";
    }
}