package br.com.fiap.market.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Long id) {
        super("Item não encontrado com o id: " + id);
    }
}
