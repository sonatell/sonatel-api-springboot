package sn.sonatel.api.service.mapper;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import sn.sonatel.api.model.Money;
import sn.sonatel.api.model.RelatedParty;
import sn.sonatel.api.model.Transaction;
import sn.sonatel.api.model.TransactionRequest;

public interface RequestMapper {

    static Transaction mapTransactionRequest(TransactionRequest source, String defaultPartnerMsisdn, String defaultPinCode) {
        var transaction = new Transaction();

        transaction.setRequestDate(ObjectUtils.defaultIfNull(source.getRequestDate(), Instant.now()));
        transaction.setReference(ObjectUtils.defaultIfNull(source.getReference(), UUID.randomUUID().toString()));

        transaction.setMetadata(source.getMetadata());
        transaction.setAmount(new Money(source.getAmount()));

        var partner = new RelatedParty();
        partner.setId(ObjectUtils.defaultIfNull(source.getPartnerMsisdn(), defaultPartnerMsisdn));
        partner.setEncryptedPinCode(ObjectUtils.defaultIfNull(source.getPartnerEncryptedPinCode(), defaultPinCode));
        transaction.setPartner(partner);

        var customer = new RelatedParty();
        customer.setId(source.getCustomerMsisdn());
        transaction.setCustomer(customer);

        return transaction;

    }
}
