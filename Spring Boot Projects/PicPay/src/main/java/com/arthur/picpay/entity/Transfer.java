package com.arthur.picpay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "wallet_sender_id")
    @ManyToOne
    private Wallet sender;

    @JoinColumn(name = "wallet_receiver_id")
    @ManyToOne
    private Wallet receiver;

    @JoinColumn(name = "value")
    private BigDecimal value;

    public Transfer(BigDecimal value, Wallet sender, Wallet receiver) {
        this.value = value;
        this.sender = sender;
        this.receiver = receiver;
    }
}
