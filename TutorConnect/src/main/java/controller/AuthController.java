package com.tutorconnect.controller;

import com.tutorconnect.dto.ChangePasswordDTO;
import com.tutorconnect.dto.DeleteAccountDTO;
import com.tutorconnect.model.Review;
import com.tutorconnect.model.User;
import com.tutorconnect.repository.ReviewRepository;
import com.tutorconnect.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ReviewRepository reviewRepository;

    public AuthController(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            ReviewRepository reviewRepository
    ) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute(
                "user",
                new User()
        );

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute User user
    ) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {

        List<User> users =
                userRepository.findAll();

        model.addAttribute(
                "users",
                users
        );

        return "users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable Long id
    ) {

        userRepository.deleteById(id);

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model
    ) {

        User user =
                userRepository.findById(id)
                        .orElse(null);

        model.addAttribute(
                "user",
                user
        );

        return "edit-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable Long id,
            @ModelAttribute User user
    ) {

        User existingUser =
                userRepository.findById(id)
                        .orElse(null);

        existingUser.setName(
                user.getName()
        );

        existingUser.setEmail(
                user.getEmail()
        );

        existingUser.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        existingUser.setRole(
                user.getRole()
        );

        existingUser.setStudies(
                user.getStudies()
        );

        existingUser.setExperience(
                user.getExperience()
        );

        existingUser.setDescription(
                user.getDescription()
        );

        existingUser.setProfileImage(
                user.getProfileImage()
        );

        userRepository.save(
                existingUser
        );

        return "redirect:/users";
    }

    @GetMapping("/my-profile")
    public String showMyProfile(
            Principal principal,
            Model model
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        model.addAttribute(
                "user",
                user
        );

        return "my-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @ModelAttribute User user,
            Model model
    ) {

        User existingUser =
                userRepository.findById(
                        user.getId()
                ).orElse(null);

        existingUser.setName(
                user.getName()
        );

        existingUser.setEmail(
                user.getEmail()
        );

        existingUser.setProfileImage(
                user.getProfileImage()
        );

        if(existingUser.getRole().equals("MEDITATOR")) {

            existingUser.setStudies(
                    user.getStudies()
            );

            existingUser.setExperience(
                    user.getExperience()
            );

            existingUser.setDescription(
                    user.getDescription()
            );
        }

        userRepository.save(
                existingUser
        );

        model.addAttribute(
                "user",
                existingUser
        );

        model.addAttribute(
                "success",
                "Profile updated successfully!"
        );

        return "my-profile";
    }

    @GetMapping("/tutor-profile/{id}")
    public String showTutorProfile(
            @PathVariable Long id,
            Model model
    ) {

        User user =
                userRepository.findById(id)
                        .orElse(null);

        List<Review> reviews =
                reviewRepository.findByTutorId(id);

        model.addAttribute(
                "user",
                user
        );

        model.addAttribute(
                "reviews",
                reviews
        );

        return "tutor-profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage(
            Model model
    ) {

        model.addAttribute(
                "passwordDto",
                new ChangePasswordDTO()
        );

        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @ModelAttribute ChangePasswordDTO passwordDto,
            Principal principal,
            Model model
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        if(!passwordEncoder.matches(
                passwordDto.getOldPassword(),
                user.getPassword()
        )) {

            model.addAttribute(
                    "error",
                    "Old password is incorrect!"
            );

            model.addAttribute(
                    "passwordDto",
                    new ChangePasswordDTO()
            );

            return "change-password";
        }

        if(passwordDto.getNewPassword().length() < 6) {

            model.addAttribute(
                    "error",
                    "Password must contain at least 6 characters!"
            );

            model.addAttribute(
                    "passwordDto",
                    new ChangePasswordDTO()
            );

            return "change-password";
        }

        if(!passwordDto.getNewPassword().equals(
                passwordDto.getConfirmPassword()
        )) {

            model.addAttribute(
                    "error",
                    "Passwords do not match!"
            );

            model.addAttribute(
                    "passwordDto",
                    new ChangePasswordDTO()
            );

            return "change-password";
        }

        user.setPassword(
                passwordEncoder.encode(
                        passwordDto.getNewPassword()
                )
        );

        userRepository.save(user);

        model.addAttribute(
                "success",
                "Password changed successfully!"
        );

        model.addAttribute(
                "passwordDto",
                new ChangePasswordDTO()
        );

        return "change-password";
    }

    @GetMapping("/delete-account")
    public String showDeleteAccountPage(
            Model model
    ) {

        model.addAttribute(
                "deleteDto",
                new DeleteAccountDTO()
        );

        return "delete-account";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(
            @ModelAttribute DeleteAccountDTO deleteDto,
            Principal principal,
            Model model
    ) {

        User user =
                userRepository.findByEmail(
                        principal.getName()
                ).orElse(null);

        if(!passwordEncoder.matches(
                deleteDto.getPassword(),
                user.getPassword()
        )) {

            model.addAttribute(
                    "error",
                    "Incorrect password!"
            );

            model.addAttribute(
                    "deleteDto",
                    new DeleteAccountDTO()
            );

            return "delete-account";
        }

        userRepository.delete(user);

        return "redirect:/login";
    }
}