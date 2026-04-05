package org.acme;

import java.time.LocalDateTime;

public class Mensagem {
    public Long id;
    public String remetente;
    public String conteudo;
    public LocalDateTime timestamp;

    // Construtor vazio exigido pelo Jackson para serialização JSON
    public Mensagem() {}
}