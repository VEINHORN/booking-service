package com.veinhorn.booking.service.repository;

import com.veinhorn.booking.service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
