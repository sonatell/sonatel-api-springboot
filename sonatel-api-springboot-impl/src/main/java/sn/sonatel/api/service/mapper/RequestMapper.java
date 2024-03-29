/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sn.sonatel.api.service.mapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import sn.sonatel.api.model.*;

public interface RequestMapper {

    static Transaction mapTransactionRequest(TransactionRequest source, String defaultPartnerMsisdn, String defaultPinCode) {
        var transaction = new Transaction();

        transaction.setRequestDate(ObjectUtils.defaultIfNull(source.getRequestDate(), Instant.now()));
        transaction.setReference(ObjectUtils.defaultIfNull(source.getReference(), UUID.randomUUID().toString()));

        transaction.setMetadata(ObjectUtils.defaultIfNull(source.getMetadata(), new HashMap<>()));
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

    static Transaction webPayment(TransactionRequest source, String defaultPartnerMsisdn, String defaultPinCode) {
        var transaction = mapTransactionRequest(source, defaultPartnerMsisdn, defaultPinCode);
        transaction.getPartner().setEncryptedPinCode(null);
        transaction.getPartner().setIdType(IdType.CODE);
        return transaction;
    }
}