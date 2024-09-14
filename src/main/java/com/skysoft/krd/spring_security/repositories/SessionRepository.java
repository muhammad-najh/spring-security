package com.skysoft.krd.spring_security.repositories;

import com.skysoft.krd.spring_security.entities.Session;
import com.skysoft.krd.spring_security.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    List<Session> findByUser(User user);
    Optional<Session> findByRefreshToken(String refreshToken);
}
