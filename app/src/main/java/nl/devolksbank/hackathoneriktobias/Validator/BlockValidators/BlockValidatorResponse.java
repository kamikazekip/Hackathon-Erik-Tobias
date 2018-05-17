package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators;

import java.util.ArrayList;
import java.util.List;

import nl.devolksbank.hackathoneriktobias.Validator.Outcome;

public class BlockValidatorResponse {
    public Outcome outcome;
    public ArrayList<String> reasons;

    public BlockValidatorResponse(){
        reasons = new ArrayList<>();
    }

    public BlockValidatorResponse(Outcome outcome, ArrayList<String> reasons) {
        this.outcome = outcome;
        this.reasons = reasons;
    }
}
