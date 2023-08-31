package com.example.businessprofile.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

public class IdValidator implements ConstraintValidator<IdValidation, BigInteger> {

    public boolean isValid(BigInteger id, ConstraintValidatorContext cxt) {
        if (id != null)
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

}
