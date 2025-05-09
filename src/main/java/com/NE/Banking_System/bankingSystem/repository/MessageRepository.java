package com.NE.Banking_System.repository;

import com.NE.Banking_System.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByCustomerId(Long customerId);
}

