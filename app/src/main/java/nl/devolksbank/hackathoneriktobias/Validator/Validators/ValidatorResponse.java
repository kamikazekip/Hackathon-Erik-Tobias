package nl.devolksbank.hackathoneriktobias.Validator.Validators;

import java.util.ArrayList;

import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.Outcome;

public class ValidatorResponse {
    public Outcome outcome;
    public ArrayList<BlockValidatorResponse> blockValidatorResponses;

    public ValidatorResponse(ArrayList<BlockValidatorResponse> blockValidatorResponses){
        this.blockValidatorResponses = blockValidatorResponses;
        this.outcome = getOutcome(this.blockValidatorResponses);
    }

    public Outcome getOutcome(ArrayList<BlockValidatorResponse> blockValidatorResponses){
        Outcome result = Outcome.NoFraudDetected;

        for(BlockValidatorResponse response : blockValidatorResponses){
            switch (response.outcome) {
                case FraudDetected:
                    return Outcome.FraudDetected;
                case PossibleFraudDetected:
                    result = Outcome.PossibleFraudDetected;
                    break;
            }
        }

        return result;
    }
}
