package br.com.mangarosa.messages;

import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.Producer;

import java.time.Duration;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Messagem para ser processada
 */
public class Message implements Serializable, Comparable<Message>{

    private String id;
    private Producer producer;
    private final LocalDateTime createdAt;
    private final List<MessageConsumption> consumptionList;
    private boolean isConsumed;
    private String message;

    private final Duration TimeDuration = Duration.ofMinutes(5) ; 


    public Message(Producer producer, String message){
        setProducer(producer);
        setMessage(message);
        this.createdAt = LocalDateTime.now();
        this.consumptionList = new ArrayList<>();
    }


    /**
     * Retorna o id da mensagem baseado na data de criação
     * @return o id da mensagem
     */
    public String getId() {
        return id;
    }

    /**
     * Atribui o valor de id gerado
     * @param id id da mensagem
     */
    public void setId(String id){
        if(id == null || id.isBlank() || id.isEmpty())
            throw new IllegalArgumentException("The message id can't be null, blank or empty");
        this.id = id;
    }

    /**
     * Retorna o produtor que criou a mensagem
     * @return o producer
     */
    public Producer getProducer() {
        return producer;
    }

    private void setProducer(Producer producer) {
        if(producer == null)
            throw new IllegalArgumentException("The message's producer can't be null");
        this.producer = producer;
    }

    /**
     * Retorna o horário de criação da mensagem
     * @return o horário que foi criado
     */
    public LocalDateTime getCreatedAt() {
        return createdAt; 
    }

   /**
     * Verifica se o objeto está expirado.
     * 
     * Este método calcula o tempo de expiração com base na data de criação 
     * do objeto e uma duração especificada. Ele compara o tempo atual 
      * de expiração, o objeto é considerado expirado.
     * 
     * @return true se o objeto estiver expirado, false caso contrário.
    */
    public boolean isExpired() {
    // Calcula o tempo de expiração adicionando a duração ao tempo de criação.
    LocalDateTime expirationTime = createdAt.plus(TimeDuration);
    // Verifica se o tempo atual é posterior ao tempo de expiração.
    return LocalDateTime.now().isAfter(expirationTime);
    }

    /**
     * Retorna se a mensagem foi consumida ou não
     * @return se foi consumido
     */
    public boolean isConsumed() {
        return isConsumed;
    }

    /**
     * Informa que a mensagem foi consumida
     * @param consumed se foi consumido
     */
    public void setConsumed(boolean consumed) {
        isConsumed = consumed;
    }

    /**
     * Retorna a mensagem que deve ser consumida
     * @return mensagem
     */
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        if(message == null || message.isBlank() || message.isEmpty())
            throw new IllegalArgumentException("The message content can't be null or empty or blank");
        this.message = message;

    }

    /**
     * Adiciona o consumo da mensagem
     * @param consumer consumer
     */
    public void addConsumption(Consumer consumer){
        if(consumer == null)
            throw new IllegalArgumentException("Consumer can't be null in a consumptio");
        this.consumptionList.add(new MessageConsumption(consumer));
    }

    public Map<String, String> toMap() throws IllegalAccessException {
        final HashMap<String, String> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);
            Object value = field.get(this);
            if(value != null) {
                map.put(field.getName(), value.toString());
            }
        }
        return map;
    }


    @Override
    public int compareTo(Message arg0) {
    if (arg0 == null) {
        return 1; // Se o outro é null, this é considerado maior
    }
    return this.id.compareTo(arg0.id); // Compara pelos IDs
    }
 }


