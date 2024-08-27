package com.bitaqaty.reseller.utilities

object Errors {
    enum class AuthError(val value: String) {
        PasswordNotChanged("15"),
        AgreementNotAccepted("17"),
        InvalidAccessType("19"),
        NewPassEqualsOldPass("20"),
        NewPassNotEqualsConfPass("21"),
        NewPassNotEqualsRegex("22"),
        MissingNewPass("28"),
        MissingConfPass("29"),
        InvalidUserNameOrPassword("31"),
        InvalidLoginProcessToken("33"),
        SmsAuthSent("37"),
        InvalidSmsVerificationCode("39"),
        ExceededMaxAllowedSms("41"),
        ExceededMaxAllowedResendSmsLimit("42"),
        INVALID_USERNAME("44"),
        IncorrectCurrentPassword("45"),
        IpLocationSMSAuthSent("101"),
        SmsVerificationExpired("108"),
        EXCEEDEDALLOWEDVERIFICATIOTRIALS("123"),

    }

    enum class BankTransferError(val value: String) {
        USER_DID_NOT_HAS_PERMISSION("56"),
        BANK_ACCOUNT_NOT_FOUND("86"),
        BANK_ACCOUNT_ALREADY_EXIST("87"),
        CHARGING_BANK_TRANSFER_AMOUNT_LESS_THAN_MIN_AMOUNT("89"),
        CHARGING_BANK_TRANSFER_AMOUNT_GREATER_THAN_MAX_AMOUNT("90"),
        INVALID_AMOUNT("92"),
        EXCEEDED_MAXIMUM_NUMBER_OF_REQUESTS("93"),
        CHARGING_BANK_TRANSFER_AMOUNT_PER_DAY("94"),
    }


    enum class PurchaseErrors(val value: String) {
        PRODUCT_PRICE_CHANGED("51"),
        UNABLE_TO_PURCHASE("52"),
        SERVICE_NOT_AVAILABLE("70"),
        INSUFFICIENT_RESELLER_BALANCE("62"),
        INSUFFICIENT_ITEMS_IN_STOCK("63"),
        PRODUCT_OUT_OF_STOCK("134"),
        UNABLE_TO_CHARGE("147"),
        PARTNER_ERROR("168"),
        INVALID_TRANSACTION("146"),
       TRANSACTION_NOT_FOUND("163"),
        INVALID_PRODUCT_PRICE("53"),
        NOT_SELLABLE_PRODUCT("54"),
        BULK_PURCHASE_DISABLED("64"),
        INVALID_PRODUCT_TYPE_FOR_BULK_PURCHASE("65"),
        INVALID_NUMBER_OF_ITEMS_IN_BULK_PURCHASE("66"),
        BULK_NOT_ALLOWED_FOR_SERVICE_PRODUCT("67"),
        NOT_PRODUCT_ITEMS_FOUND("80");

    }
}