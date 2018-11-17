package br.com.wellingtoncosta.bankslipsapi.data.repository;

import br.com.wellingtoncosta.bankslipsapi.data.entity.BankSlipEntity;
import br.com.wellingtoncosta.bankslipsapi.data.entity.QBankSlipEntity;
import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.domain.repository.BankSlipRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * @author Wellington Costa on 16/11/18
 */
@Repository public class BankSlipJpaRepository implements BankSlipRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @Inject @Lazy public void setQueryFactory(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override public BankSlip create(BankSlip bankSlip) {
        BankSlipEntity entity = bankSlip.toEntity();
        entityManager.persist(entity);
        return entity.toModel();
    }

    @Override public BankSlip update(BankSlip bankSlip) {
        BankSlipEntity entity = entityManager.merge(bankSlip.toEntity());
        return entity.toModel();
    }

    @Override public List<BankSlip> listAll() {
        return queryFactory.selectFrom(QBankSlipEntity.bankSlipEntity)
                .fetch()
                .stream()
                .map(BankSlipEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override public BankSlip findById(String uuid) {
        BankSlipEntity entity = entityManager.find(BankSlipEntity.class, uuid);
        if(isNull(entity)) return null;
        else return entity.toModel();
    }

    @Override
    public void delete(BankSlip bankSlip) {
        entityManager.remove(bankSlip.toEntity());
    }
}
