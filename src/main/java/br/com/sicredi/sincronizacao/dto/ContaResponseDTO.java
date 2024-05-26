package br.com.sicredi.sincronizacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContaResponseDTO {
    private String agencia;
    private String conta;
    private Double saldo;
    private boolean status;

    public ContaResponseDTO(ContaDTO conta, boolean status) {
        this.agencia = conta.agencia();
        this.conta = conta.conta();
        this.saldo = conta.saldo();
        this.status = status;
    }
}
