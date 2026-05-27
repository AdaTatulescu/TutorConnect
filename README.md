# TutorConnect

TutorConnect este o aplicație web realizată cu Spring Boot care conectează cursanții cu meditatori.

---

# Funcționalități

## Autentificare și utilizatori
- Register / Login
- Logout
- Schimbare parolă
- Ștergere cont
- Editare profil

## Meditații
- Creare meditații
- Editare meditații
- Ștergere meditații
- Căutare după materie

## Booking-uri
- Request session
- Accept / Reject request
- Programare ședință
- Vizualizare requests

## Review-uri
- Adăugare review
- Rating pentru meditatori
- Afișare review-uri pe profil

---

# Tehnologii utilizate

- Java 17
- Spring Boot
- Spring Security
- Thymeleaf
- Bootstrap 5
- MySQL
- Hibernate / JPA
- Maven

---

# Roluri utilizatori

## CURSANT
- poate căuta meditatori
- poate trimite request-uri
- poate lăsa review-uri

## MEDITATOR
- poate crea meditații
- poate accepta/reject requests
- poate vedea review-uri

---

# Structura proiectului

```text
src/main/java/com.tutorconnect
│
├── controller
├── model
├── repository
├── dto
├── config
└── service
```

---

# Rulare proiect

## 1. Clonare proiect

```bash
git clone LINK_REPOSITORY
```

---

## 2. Configurare baza de date

În `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tutorconnect
spring.datasource.username=root
spring.datasource.password=PAROLA
```

---

## 3. Pornire aplicație

Rulează:

```text
TutorConnectApplication.java
```

Aplicația va porni pe:

```text
http://localhost:8080
```

---

# Baza de date

Entități principale:
- User
- Meditation
- Booking
- Review

---
