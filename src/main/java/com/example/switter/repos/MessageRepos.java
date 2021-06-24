package com.example.switter.repos;

import com.example.switter.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepos extends CrudRepository<Message, Integer> {

    List<Message> findByTag(String tag); // поиск в БД по тегу
}


