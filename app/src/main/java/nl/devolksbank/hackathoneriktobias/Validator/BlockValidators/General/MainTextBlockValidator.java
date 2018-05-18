package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.General;

import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidator;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.Outcome;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;

public class MainTextBlockValidator extends BlockValidator {

    private BlockValidatorResponse response;

    @Override
    public BlockValidatorResponse validate(ValidatorInput input) {
        response = new BlockValidatorResponse();
        if(input.mainText.contains("Brandsma")){
            response.outcome = Outcome.NoFraudDetected;
        }else {
            response.outcome = Outcome.FraudDetected;
        }
        return response;
    }

    private void checkLanguage() {

    }

    public void checkForSpellingErrors() {

    }
}
