package com.tutorconnect.controller;

import com.tutorconnect.model.Review;
import com.tutorconnect.model.User;
import com.tutorconnect.repository.ReviewRepository;
import com.tutorconnect.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ReviewController {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    public ReviewController(
            ReviewRepository reviewRepository,
            UserRepository userRepository
    ) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/add-review/{tutorId}")
    public String showReviewForm(
            @PathVariable Long tutorId,
            Model model
    ) {

        Review review = new Review();

        review.setTutorId(
                tutorId
        );

        model.addAttribute(
                "review",
                review
        );

        return "add-review";
    }

    @PostMapping("/add-review")
    public String addReview(
            @ModelAttribute Review review,
            Principal principal
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        review.setStudentName(
                user.getName()
        );

        reviewRepository.save(
                review
        );

        return "redirect:/tutor-profile/" + review.getTutorId();
    }
}