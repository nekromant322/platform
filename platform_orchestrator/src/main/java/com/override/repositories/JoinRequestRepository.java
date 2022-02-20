package com.override.repositories;

import com.override.models.JoinRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends CrudRepository<JoinRequest, Long> {

    List<JoinRequest> findAll();

    JoinRequest findFirstByChatId(String chatId);
}
