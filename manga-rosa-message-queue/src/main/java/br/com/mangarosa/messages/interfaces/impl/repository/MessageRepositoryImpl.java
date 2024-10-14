package br.com.mangarosa.messages.interfaces.impl.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.mangarosa.datastructures.interfaces.impl.LinkedQueue;
import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.MessageRepository;

/**
 * A classe Repository implementa a interface MessageRepository,
 * gerenciando mensagens em duas categorias: não consumidas e consumidas.
 * As mensagens são armazenadas em mapas indexados por tópicos.
 */
public class MessageRepositoryImpl implements MessageRepository {

    public final Map<String, LinkedQueue<Message>> notConsumed;
    // Mapa que armazena mensagens não consumidas, indexadas por tópicos.
    
    public MessageRepositoryImpl (){
        this.notConsumed = new HashMap<>();
    }
    


    /**
     * Adiciona uma nova mensagem ao tópico especificado.
     * Se o tópico não existir, ele é criado automaticamente.
     *
     * @param topic   o nome do tópico onde a mensagem deve ser adicionada
     * @param message a mensagem a ser adicionada
     */
    @Override
    public void append(String topic, Message message) {
        
            notConsumed.computeIfAbsent(topic, k -> new LinkedQueue<>()).enqueue(message);
        
        
    }

    /**
     * Consome uma mensagem identificada pelo seu ID, removendo-a da lista de não consumidas
     * e adicionando-a à lista de consumidas do tópico correspondente.
     * Se a mensagem não for encontrada, uma IllegalArgumentException é lançada.
     *
     * @param topic     o nome do tópico de onde a mensagem deve ser consumida
     * @param messageId o ID da mensagem a ser consumida
     * @throws IllegalArgumentException se a mensagem não for encontrada ou já tiver sido consumida
     */
    @Override
    public void consumeMessage(String topic, UUID messageId) {
        LinkedQueue<Message> notConsumed = this.notConsumed.get(topic);
        if (notConsumed != null) {
            for (Message message : notConsumed) {
                if (message.getId().equals(messageId)) {
                    notConsumed.dequeue(); 
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Message not found or already consumed.");
    }

    /**
     * Retorna todas as mensagens não consumidas associadas a um tópico específico.
     *
     * @param topic o nome do tópico cujas mensagens não consumidas devem ser retornadas
     * @return uma lista de mensagens não consumidas do tópico, ou uma lista vazia se não houver mensagens
     */
    @Override
    public List<Message> getAllNotConsumedMessagesByTopic(String topic) {
        
        if (topic == null){
            throw new IllegalArgumentException("Message not found or already consumed.");
        }
       
    // Obtenha a LinkedQueue de mensagens não consumidas
    LinkedQueue<Message> messages = notConsumed.getOrDefault(topic, new LinkedQueue<>());

    // Converte LinkedQueue para List
    List<Message> messageList = new ArrayList<>();
    for (Message message : messages) {
        messageList.add(message);
    }

    return messageList; // Retorna a lista de mensagens
    }    
}
