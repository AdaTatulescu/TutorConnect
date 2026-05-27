package com.tutorconnect.controller;

import com.tutorconnect.model.Meditation;
import com.tutorconnect.model.User;
import com.tutorconnect.repository.MeditationRepository;
import com.tutorconnect.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MeditationController {

    private final MeditationRepository meditationRepository;

    private final UserRepository userRepository;

    public MeditationController(
            MeditationRepository meditationRepository,
            UserRepository userRepository
    ) {

        this.meditationRepository = meditationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create-meditation")
    public String showCreateMeditationForm(
            Model model
    ) {

        model.addAttribute(
                "meditation",
                new Meditation()
        );

        return "create-meditation";
    }

    @PostMapping("/create-meditation")
    public String createMeditation(
            @ModelAttribute Meditation meditation,
            Principal principal
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        meditation.setUserId(
                user.getId()
        );

        meditation.setTutorName(
                user.getName()
        );

        meditationRepository.save(
                meditation
        );

        return "redirect:/meditations";
    }

    @GetMapping("/meditations")
    public String showMeditations(
            Model model,
            Principal principal
    ) {

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        String role =
                userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority();

        List<Meditation> meditations;

        if (role.equals("MEDITATOR")) {

            User user =
                    userRepository.findByEmail(
                            principal.getName()
                    ).orElse(null);

            meditations =
                    meditationRepository.findByTutorName(
                            user.getName()
                    );

        } else {

            meditations =
                    meditationRepository.findAll();
        }

        model.addAttribute(
                "meditations",
                meditations
        );

        return "meditations";
    }

    @GetMapping("/search-meditations")
    public String searchMeditations(
            @RequestParam String subject,
            Model model
    ) {

        List<Meditation> meditations =
                meditationRepository
                        .findBySubjectContainingIgnoreCase(
                                subject
                        );

        model.addAttribute(
                "meditations",
                meditations
        );

        return "meditations";
    }

    @GetMapping("/delete-meditation/{id}")
    public String deleteMeditation(
            @PathVariable Long id
    ) {

        meditationRepository.deleteById(id);

        return "redirect:/meditations";
    }

    @GetMapping("/edit-meditation/{id}")
    public String showEditMeditationForm(
            @PathVariable Long id,
            Model model
    ) {

        Meditation meditation =
                meditationRepository.findById(id)
                        .orElse(null);

        model.addAttribute(
                "meditation",
                meditation
        );

        return "edit-meditation";
    }

    @PostMapping("/update-meditation/{id}")
    public String updateMeditation(
            @PathVariable Long id,
            @ModelAttribute Meditation meditation
    ) {

        Meditation existingMeditation =
                meditationRepository.findById(id)
                        .orElse(null);

        existingMeditation.setTitle(
                meditation.getTitle()
        );

        existingMeditation.setSubject(
                meditation.getSubject()
        );

        existingMeditation.setDescription(
                meditation.getDescription()
        );

        existingMeditation.setPrice(
                meditation.getPrice()
        );

        meditationRepository.save(
                existingMeditation
        );

        return "redirect:/meditations";
    }
}