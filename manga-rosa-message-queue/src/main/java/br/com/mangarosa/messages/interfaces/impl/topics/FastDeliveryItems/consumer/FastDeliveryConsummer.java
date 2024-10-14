package br.com.mangarosa.messages.interfaces.impl.topics.FastDeliveryItems.consumer;

import java.util.UUID;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.impl.repository.MessageRepositoryImpl;

/**
 * Consumidor responsável por consumir mensagens relacionadas a itens de entrega rápida.
 * Implementa a interface Consumer para fornecer a lógica de consumo.
 */
public class FastDeliveryConsummer implements Consumer { 

    private final String nameConsumer; // Nome do consumidor
    private final MessageRepositoryImpl Repository; // Repositório de mensagens

    /**
     * Construtor da classe FastDeliveryConsummer.
     * Inicializa o nome do consumidor e o repositório de mensagens.
     */
    public FastDeliveryConsummer(){
        this.nameConsumer = "Fast Delivery Consumer";
        this.Repository = new MessageRepositoryImpl();
    }

    /**
     * Consome uma mensagem, realizando as seguintes etapas:
     * 1. Verifica se a mensagem é válida (não nula, não expirada e não consumida).
     * 2. Se válida, marca a mensagem como consumida e a remove do repositório.
     * 3. Registra o consumo da mensagem.
     *
     * @param message A mensagem a ser consumida.
     * @return true se a mensagem foi consumida com sucesso, false caso contrário.
     */
    @Override
    public boolean consume(Message message) {
        try {
            if (message == null || message.isExpired() || message.isConsumed()) {
                message.setConsumed(false);
                return false;
            } else {
                System.out.println("Consumindo mensagem: " + message.getMessage());
                message.setConsumed(true);

                // Remove a mensagem do repositório
                Repository.consumeMessage(nameConsumer, UUID.fromString(message.getId()));

                message.addConsumption(this);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Erro ao consumir mensagem: " + e.getMessage());
            return false;
        }
    }
    

    /**
     * Retorna o nome do consumidor.
     *
     * @return O nome do consumidor.
     */
    @Override
    public String name() {
        return nameConsumer;
    }
}