package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators;

import java.util.ArrayList;
import java.util.List;

import nl.devolksbank.hackathoneriktobias.Validator.Outcome;
import nl.devolksbank.hackathoneriktobias.Validator.Reason;

public class BlockValidatorResponse {
    public Outcome outcome;
    public ArrayList<Reason> reasons;

    public BlockValidatorResponse(){
        reasons = new ArrayList<>();
    }

    public BlockValidatorResponse(Outcome outcome, ArrayList<Reason> reasons) {
        this.outcome = outcome;
        this.reasons = reasons;
    }

    public void determineOutcome(){
        Outcome result = Outcome.NoFraudDetected;

        for(Reason reason : reasons){
            switch (reason.outcome) {
                case FraudDetected:
                    this.outcome = Outcome.FraudDetected;
                    return;
                case PossibleFraudDetected:
                    result = Outcome.PossibleFraudDetected;
                    break;
            }
        }
        this.outcome = result;
    }
}
