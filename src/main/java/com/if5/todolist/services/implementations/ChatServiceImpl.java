package com.if5.todolist.services.implementations;

import com.if5.todolist.dtos.requets.TopicRequestDto;
import com.if5.todolist.dtos.responses.TopicResponseDto;
import com.if5.todolist.services.interfaces.ChatService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final KafkaAdmin kafkaAdmin;

    public ChatServiceImpl(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    @Override
    public TopicResponseDto createTopic(TopicRequestDto topicRequestDto) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            NewTopic newTopic = new NewTopic(topicRequestDto.getLibelle(), 1, (short) 1);
            adminClient.createTopics(Collections.singleton(newTopic));
        } catch (Exception e) {
            // Gérer les erreurs ici
            e.printStackTrace();

            return TopicResponseDto.builder().build(); // Par exemple, retourner une réponse vide
        }
        return TopicResponseDto.builder()
                .libelle(topicRequestDto.getLibelle())
                .description(topicRequestDto.getDescription())
                .build();
        }
}
