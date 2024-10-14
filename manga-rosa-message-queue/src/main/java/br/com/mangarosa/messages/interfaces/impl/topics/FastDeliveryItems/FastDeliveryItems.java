package br.com.mangarosa.messages.interfaces.impl.topics.FastDeliveryItems;

import java.util.ArrayList;
import java.util.List;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;
import br.com.mangarosa.messages.interfaces.Topic;
import br.com.mangarosa.messages.interfaces.impl.repository.MessageRepositoryImpl;

/**
 * Representa o tópico "Fast Delivery Items" para mensagens relacionadas a itens de entrega rápida.
 * Implementa a interface Topic para gerenciar o ciclo de vida das mensagens, 
 * incluindo assinatura de consumidores, notificação e acesso ao repositório.
*/
public class FastDeliveryItems implements Topic {

    private final String nomeTopic = "queue/fast-delivery-items";
    private final MessageRepositoryImpl repository = new MessageRepositoryImpl(); // Cria uma única instância
    private final List<Consumer> queueconsumers = new  ArrayList<>();
    

    /**
     * Retorna o nome do tópico.
     * @return O nome do tópico.
    */
    @Override
    public String name() {
       return nomeTopic;
    }

    /**
     * Adiciona uma mensagem ao tópico e notifica os consumidores.
     * @param message A mensagem a ser adicionada.
     */
    @Override
    public void addMessage(Message message) {

        try{ 
            repository.append(nomeTopic, message);
            notifyConsumers(message);
        }
        catch (Exception e) {
           System.err.println("Erro ao adicionar mensagem: " + e.getMessage()); 
        }

    }

    /**
     * Inscreve um consumidor no tópico.
     * @param consumer O consumidor a ser inscrito.
     */
    @Override
    public void subscribe(Consumer consumer) {
        queueconsumers.add(consumer);
    }

    /**
     * Remove a inscrição de um consumidor no tópico.
     * @param consumer O consumidor a ser removido.
     */
    @Override
    public void unsubscribe(Consumer consumer) {
        queueconsumers.remove(consumer);
    }

    /**
     * Retorna a lista de consumidores inscritos no tópico.
     * @return A lista de consumidores.
     */
    @Override
    public List<Consumer> consumers() {
      
        return queueconsumers;
        
    }

    /**
     * Retorna o repositório de mensagens associado ao tópico.
     * @return O repositório de mensagens.
     */
    @Override
    public MessageRepository getRepository() {
        return repository;
    }  
}
