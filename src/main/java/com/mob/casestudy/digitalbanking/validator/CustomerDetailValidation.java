package com.mob.casestudy.digitalbanking.validator;

import com.mob.casestudy.digitalbanking.configuration.RegexValues;
import com.mob.casestudy.digitalbanking.dto.CustomerDto;
import com.mob.casestudy.digitalbanking.enums.Language;
import com.mob.casestudy.digitalbanking.exceptionresponse.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

import static com.mob.casestudy.digitalbanking.errorcodes.CustomisedErrorCodesAndDescription.*;


@Component
public class CustomerDetailValidation {
    @Autowired
    RegexValues regexValues;
    public void phoneEmailLanguageValidation(CustomerDto customerDto) {
        if (!isPhoneValid(customerDto.getPhoneNumber())) {
            throw new InvalidDataException(INVALID_PHONE_NUMBER,INVALID_PHONE_NUMBER_DESCRIPTION);
        }
        if (!isEmailValid(customerDto.getEmail())) {
            throw new InvalidDataException(INVALID_EMAIL,INVALID_EMAIL_DESCRIPTION);
        }
        if (customerDto.getPreferredLanguage() != null && !isLanguageValid(customerDto.getPreferredLanguage())) {
            throw new InvalidDataException(INVALID_LANGUAGE_INPUT,INVALID_LANGUAGE_INPUT_DESCRIPTION);
        }
    }

    public boolean isEmailValid(String email) {
        return Objects.isNull(email) || email.isEmpty() || email.matches(regexValues.getEmailRegex());
    }

    public boolean isPhoneValid(String number) {
        return number == null || number.isEmpty() || number.matches(regexValues.getPhoneRegex());
    }

    public boolean isLanguageValid(String language) {
        return language.trim().isEmpty() || Arrays.stream(Language.values()).map(Enum::toString).anyMatch(l -> l.equalsIgnoreCase(language));
    }
}
