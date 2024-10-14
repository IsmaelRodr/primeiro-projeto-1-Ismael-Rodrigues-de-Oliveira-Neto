package br.com.mangarosa.messages.interfaces.impl.topics.FastDeliveryItems.producer;

import java.util.ArrayList;
import java.util.List;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Producer;
import br.com.mangarosa.messages.interfaces.Topic;
import br.com.mangarosa.messages.interfaces.impl.repository.MessageRepositoryImpl;

public class PhysicPersonDeliveryProducer implements Producer {

    private final String nameProducer;
    private final List<String> queueTopics = new ArrayList<>();
    private final MessageRepositoryImpl messageRepository = new MessageRepositoryImpl();

    public PhysicPersonDeliveryProducer(String nameProducer ) {
        this.nameProducer = "Physic Person Delivery Producer";
    }

    /**
     * Adiciona um tópico à lista de tópicos aos quais o produtor está associado.
     *
     * @param topic O tópico a ser adicionado.
     */
    @Override
    public void addTopic(Topic topic) {
        queueTopics.add(topic.name());
    }

    /**
     * Remove um tópico da lista de tópicos aos quais o produtor está associado.
     *
     * @param topic O tópico a ser removido.
     */
    @Override
    public void removeTopic(Topic topic) {
        queueTopics.remove(topic.name());
    }

    /**
     * Envia uma mensagem para todos os tópicos associados ao produtor.
     * Antes de enviar, verifica se a mensagem é válida (não nula).
     * Se a mensagem for válida, ela é adicionada ao repositório de mensagens para cada tópico.
     *
     * @param message A mensagem a ser enviada.
     * @throws IllegalArgumentException Se a mensagem for nula.
     */
    @Override
    public void sendMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        PhysicPersonDeliveryProducer Producer = new PhysicPersonDeliveryProducer(message);

        // Cria uma nova mensagem com um ID único
        Message newMessage = new Message( Producer,message);

        for (String topic : queueTopics) {
            messageRepository.append(topic, newMessage);
        }
    }

    /**
     * Retorna o nome do produtor.
     *
     * @return O nome do produtor.
     */
    @Override
    public String name() {
        return nameProducer;
    }
}