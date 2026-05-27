package com.tutorconnect.repository;

import com.tutorconnect.model.Meditation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeditationRepository
        extends JpaRepository<Meditation, Long> {

    List<Meditation> findBySubjectContainingIgnoreCase(
            String subject
    );

    List<Meditation> findByTutorName(
            String tutorName
    );
}