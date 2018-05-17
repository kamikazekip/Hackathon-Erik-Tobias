package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators;

import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;

public abstract class BlockValidator {
    public abstract BlockValidatorResponse validate(ValidatorInput input);
}
