package com.override.repository;

import com.override.model.JoinRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JoinRequestRepository extends CrudRepository<JoinRequest, Long> {

    List<JoinRequest> findAll();

    JoinRequest findFirstByChatId(String chatId);
}
