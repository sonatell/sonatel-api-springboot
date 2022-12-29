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
package sn.sonatel.api.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Getter
public class TransactionRequest {

    protected TransactionRequest(@NonNull Float amount, @NonNull String customerMsisdn) {
        this.withAmount(amount);
        this.withCustomerMsisdn(customerMsisdn);
    }

    private String partnerMsisdn;
    private String partnerEncryptedPinCode;

    @NonNull
    private Float amount;

    @NonNull
    private String customerMsisdn;

    private Map<String, String> metadata = new HashMap<>();

    private String reference;

    private Instant requestDate;

    private boolean receivedNotification = true;

    public TransactionRequest withPartnerMsisdn(@NonNull String partnerMsisdn) {
        Assert.notNull(partnerMsisdn, "Partner msisdn is required");
        this.partnerMsisdn = partnerMsisdn;
        return this;
    }

    public TransactionRequest withPartnerEncryptedPinCode(@NonNull String partnerEncryptedPinCode) {
        Assert.notNull(partnerEncryptedPinCode, "Partner encrypted pin code is required");
        this.partnerEncryptedPinCode = partnerEncryptedPinCode;
        return this;
    }

    public TransactionRequest withAmount(@NonNull Float amount) {
        Assert.notNull(amount, "Amount is required");
        this.amount = amount;
        return this;
    }

    public TransactionRequest withCustomerMsisdn(@NonNull String customerMsisdn) {
        Assert.notNull(customerMsisdn, "Customer msisdn is required");
        this.customerMsisdn = customerMsisdn;
        return this;
    }

    public TransactionRequest withRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public TransactionRequest withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public TransactionRequest withMetadata(@NonNull Map<String, String> metadata) {
        if(!CollectionUtils.isEmpty(metadata)){
            this.metadata = metadata;
        }
        return this;
    }

    public TransactionRequest allowNotification(boolean receivedNotification) {
        this.receivedNotification = receivedNotification;
        return this;
    }

    public static TransactionRequest forAmountAndCustomer(@NonNull Float amount, @NonNull String customerMsisdn) {
        return new TransactionRequest(amount, customerMsisdn);
    }


    @Override
    public String toString() {
        return "{" +
                "partnerMsisdn='" + partnerMsisdn + '\'' +
                ", partnerEncryptedPinCode=********" +
                ", amount=" + amount +
                ", customerMsisdn='" + customerMsisdn + '\'' +
                ", metadata=" + metadata +
                ", reference='" + reference + '\'' +
                ", requestDate=" + requestDate +
                ", receivedNotification=" + receivedNotification +
                '}';
    }
}
