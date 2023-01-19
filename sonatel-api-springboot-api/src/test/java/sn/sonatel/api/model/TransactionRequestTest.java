package sn.sonatel.api.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionRequestTest {
    /**
     * Method under test: {@link TransactionRequest#TransactionRequest(Float, String)}
     */
    @Test
    void testConstructor() {
        TransactionRequest actualTransactionRequest = new TransactionRequest(10.0f, "Customer Msisdn");

        assertEquals(10.0f, actualTransactionRequest.getAmount().floatValue());
        assertTrue(actualTransactionRequest.isReceivedNotification());
        assertTrue(actualTransactionRequest.getMetadata().isEmpty());
        assertEquals("Customer Msisdn", actualTransactionRequest.getCustomerMsisdn());
    }

    /**
     * Method under test: {@link TransactionRequest#TransactionRequest(Float, String)}
     */
    @Test
    void testConstructor2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TransactionRequest(null, null));

    }

    /**
     * Method under test: {@link TransactionRequest#TransactionRequest(Float, String)}
     */
    @Test
    void testConstructor3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TransactionRequest(10.0f, null));

    }

    /**
     * Method under test: {@link TransactionRequest#withPartnerMsisdn(String)}
     */
    @Test
    void testWithPartnerMsisdn() {
        TransactionRequest forAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");
        TransactionRequest actualWithPartnerMsisdnResult = forAmountAndCustomerResult.withPartnerMsisdn("Partner Msisdn");
        assertSame(forAmountAndCustomerResult, actualWithPartnerMsisdnResult);
        assertEquals("Partner Msisdn", actualWithPartnerMsisdnResult.getPartnerMsisdn());
    }

    /**
     * Method under test: {@link TransactionRequest#withPartnerEncryptedPinCode(String)}
     */
    @Test
    void testWithPartnerEncryptedPinCode() {
        TransactionRequest forAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");
        TransactionRequest actualWithPartnerEncryptedPinCodeResult = forAmountAndCustomerResult
                .withPartnerEncryptedPinCode("Partner Encrypted Pin Code");
        assertSame(forAmountAndCustomerResult, actualWithPartnerEncryptedPinCodeResult);
        assertEquals("Partner Encrypted Pin Code", actualWithPartnerEncryptedPinCodeResult.getPartnerEncryptedPinCode());
    }

    /**
     * Method under test: {@link TransactionRequest#withAmount(Float)}
     */
    @Test
    void testWithAmount() {
        TransactionRequest forAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");
        TransactionRequest actualWithAmountResult = forAmountAndCustomerResult.withAmount(10.0f);
        assertSame(forAmountAndCustomerResult, actualWithAmountResult);
        assertEquals(10.0f, actualWithAmountResult.getAmount().floatValue());
    }

    /**
     * Method under test: {@link TransactionRequest#withAmount(Float)}
     */
    @Test
    void testWithAmount2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new TransactionRequest(10.0f, "771234567").withAmount(null));
    }

    /**
     * Method under test: {@link TransactionRequest#withCustomerMsisdn(String)}
     */
    @Test
    void testWithCustomerMsisdn() {
        TransactionRequest forAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");
        TransactionRequest actualWithCustomerMsisdnResult = forAmountAndCustomerResult
                .withCustomerMsisdn("Customer Msisdn");
        assertSame(forAmountAndCustomerResult, actualWithCustomerMsisdnResult);
        assertEquals("Customer Msisdn", actualWithCustomerMsisdnResult.getCustomerMsisdn());
    }

    /**
     * Method under test: {@link TransactionRequest#withMetadata(Map)}
     */
    @Test
    void testWithMetadata() {
        TransactionRequest forAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");
        assertSame(forAmountAndCustomerResult, forAmountAndCustomerResult.withMetadata(new HashMap<>()));
    }

    /**
     * Method under test: {@link TransactionRequest#withMetadata(Map)}
     */
    @Test
    void testWithMetadata2() {
        TransactionRequest forAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");

        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("Key", "42");
        TransactionRequest actualWithMetadataResult = forAmountAndCustomerResult.withMetadata(stringStringMap);
        assertSame(forAmountAndCustomerResult, actualWithMetadataResult);
        assertEquals(1, actualWithMetadataResult.getMetadata().size());
    }

    /**
     * Method under test: {@link TransactionRequest#withMetadata(Map)}
     */
    @Test
    void testWithMetadata3() {
        var actualForAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn").withMetadata(null);
        assertTrue(actualForAmountAndCustomerResult.getMetadata().isEmpty());

    }

    /**
     * Method under test: {@link TransactionRequest#forAmountAndCustomer(Float, String)}
     */
    @Test
    void testForAmountAndCustomer() {
        TransactionRequest actualForAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f,
                "Customer Msisdn");
        assertEquals(10.0f, actualForAmountAndCustomerResult.getAmount().floatValue());
        assertTrue(actualForAmountAndCustomerResult.isReceivedNotification());
        assertNull(actualForAmountAndCustomerResult.getRequestDate());
        assertNull(actualForAmountAndCustomerResult.getReference());
        assertNull(actualForAmountAndCustomerResult.getPartnerMsisdn());
        assertNull(actualForAmountAndCustomerResult.getPartnerEncryptedPinCode());
        assertTrue(actualForAmountAndCustomerResult.getMetadata().isEmpty());
        assertEquals("Customer Msisdn", actualForAmountAndCustomerResult.getCustomerMsisdn());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link TransactionRequest#forAmountAndCustomer(Float, String)}
     *   <li>{@link TransactionRequest#allowNotification(boolean)}
     *   <li>{@link TransactionRequest#withReference(String)}
     *   <li>{@link TransactionRequest#withRequestDate(Instant)}
     * </ul>
     */
    @Test
    void testForAmountAndCustomer2() {
        TransactionRequest actualForAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f,
                "Customer Msisdn");
        actualForAmountAndCustomerResult.allowNotification(true);
        actualForAmountAndCustomerResult.withReference("Reference");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualForAmountAndCustomerResult.withRequestDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        assertEquals("Reference", actualForAmountAndCustomerResult.getReference());
        assertTrue(actualForAmountAndCustomerResult.isReceivedNotification());
    }

    /**
     * Method under test: {@link TransactionRequest#forAmountAndCustomer(Float, String)}
     */
    @Test
    void testForAmountAndCustomer3() {
        TransactionRequest actualForAmountAndCustomerResult = TransactionRequest.forAmountAndCustomer(10.0f, "Customer Msisdn");
        actualForAmountAndCustomerResult.withOtp("123456");
        assertEquals(10.0f, actualForAmountAndCustomerResult.getAmount().floatValue());
        assertTrue(actualForAmountAndCustomerResult.isReceivedNotification());
        assertTrue(actualForAmountAndCustomerResult.getMetadata().isEmpty());
        assertEquals("Customer Msisdn", actualForAmountAndCustomerResult.getCustomerMsisdn());
        assertEquals("123456", actualForAmountAndCustomerResult.getOtp());
    }

}

