package br.com.wellingtoncosta.bankslipsapi.data.entity;

import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Wellington Costa on 16/11/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BankSlip")
@Table(name = "tb_bank_slip")
public class BankSlipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "due_date", columnDefinition = "DATE")
    private LocalDate dueDate;

    @Column(name = "payment_date", columnDefinition = "DATE")
    private LocalDate paymentDate;

    @Column(name = "total_in_cents", scale = 2)
    private BigDecimal totalInCents;

    @Column(name = "customer")
    private String customer;

    @Column(name = "status")
    private String status;

    public BankSlip toModel() {
        return BankSlip.builder()
                .id(id)
                .dueDate(dueDate)
                .paymentDate(paymentDate)
                .totalInCents(totalInCents)
                .customer(customer)
                .status(Status.valueOf(status))
                .build();
    }

}
