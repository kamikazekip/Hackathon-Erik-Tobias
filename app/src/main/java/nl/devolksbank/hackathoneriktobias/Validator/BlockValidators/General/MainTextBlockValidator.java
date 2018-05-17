package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.General;

import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidator;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.Outcome;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;

public class MainTextBlockValidator extends BlockValidator {

    @Override
    public BlockValidatorResponse validate(ValidatorInput input) {
        BlockValidatorResponse response = new BlockValidatorResponse();
        response.outcome = Outcome.FraudDetected;
        response.reasons.add("Geen spelfouten");
        return response;
    }
}
