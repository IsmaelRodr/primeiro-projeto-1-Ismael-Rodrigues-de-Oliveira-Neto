package br.com.mangarosa.datastructures.interfaces.impl;

import br.com.mangarosa.datastructures.interfaces.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma fila (Queue) usando uma lista encadeada.
 * 
 * @param <T> o tipo de elementos armazenados na fila, que deve implementar Comparable.
 */
public class LinkedQueue<T extends Comparable<T>> implements Queue<T> {

    /**
     * Classe interna que representa um nó na fila.
     */
    private static class QueueNode<T> {
        T data; // Dado armazenado no nó
        QueueNode<T> next; // Referência para o próximo nó

        /**
         * Construtor do nó.
         * 
         * @param data o dado a ser armazenado neste nó.
         */
        QueueNode(T data) {
            this.data = data;
            this.next = null; // Inicialmente, o próximo nó é null
        }
    }

    private QueueNode<T> beginNode; // Nó da frente da fila
    private QueueNode<T> endNode; // Nó de trás da fila
    private int size; // Tamanho da fila

    /**
     * Construtor que inicializa a fila.
     */
    public LinkedQueue() {
        this.beginNode = null;
        this.endNode = null;
        this.size = 0;
    }

    /**
     * Adiciona um elemento ao final da fila.
     * 
     * @param value o valor a ser adicionado.
     */
    @Override
    public void enqueue(T value) {
        QueueNode<T> newNode = new QueueNode<>(value); // Cria um novo nó
        if (endNode != null) {
            endNode.next = newNode; // Conecta o novo nó ao final
        }
        endNode = newNode; // Atualiza o nó de trás
        if (beginNode == null) {
            beginNode = newNode; // Se a fila estava vazia, o novo nó é também o da frente
        }
        size++; // Aumenta o tamanho da fila
    }

    /**
     * Remove e retorna o elemento do início da fila.
     * 
     * @return o elemento removido da fila.
     * @throws NoSuchElementException se a fila estiver vazia.
     */
    @Override
    public T dequeue() {
        if (beginNode == null) {
            throw new NoSuchElementException("Fila vazia");
        }
        T value = beginNode.data; // Armazena o valor a ser retornado
        beginNode = beginNode.next; // Move o início da fila para o próximo nó
        if (beginNode == null) { // Se a fila agora está vazia
            endNode = null; // Atualiza o nó de trás para null
        }
        size--; // Decrementa o tamanho da fila
        return value; // Retorna o valor removido
    }

    /**
     * Retorna o primeiro elemento no início da fila, sem removê-lo.
     * 
     * @return o primeiro elemento da fila.
     * @throws NoSuchElementException se a fila estiver vazia.
     */
    @Override
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Fila vazia");
        }
        return beginNode.data; // Retorna o valor do primeiro nó
    }

    /**
     * Verifica se a fila está vazia.
     * 
     * @return true se a fila estiver vazia, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retorna o número de elementos na fila.
     * 
     * @return o tamanho da fila.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Converte a fila em um array.
     * 
     * @return um array contendo todos os elementos da fila.
     * para que seja possivel exibir os values do array e necessario chamar o method dessa forma: "System.out.println(Arrays.toString(Nome_lista.toArray()));".
     */
    @Override
    public T[] toArray( ) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) new Comparable[size]; // Cria um novo array
        QueueNode<T> current = beginNode;

        for (int i = 0; i < size; i++) {
            array[i] = current.data; // Atribui os dados ao array
            current = current.next; // Move para o próximo nó
        }
        
        return  array; // Retorna o array 
    }

    /**
     * Limpa todos os elementos da fila.
     */
    @Override
    public void clear() {
        beginNode = null; // Remove a referência ao início
        endNode = null; // Remove a referência ao fim
        size = 0; // Reseta o tamanho
    }

    /**
     * Retorna um iterador para a fila.
     * 
     * @return um iterador sobre os elementos da fila.
     */
    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            private QueueNode<T> current = beginNode; // Começa do nó da frente

            @Override
            public boolean hasNext() {
                return current != null; // Verifica se há mais elementos
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Não há próximo elemento"); // Lança exceção se não houver próximo
                }
                T value = current.data; // Armazena o valor atual
                current = current.next; // Avança para o próximo nó
                return value; // Retorna o valor
            }
        };
    }
}
