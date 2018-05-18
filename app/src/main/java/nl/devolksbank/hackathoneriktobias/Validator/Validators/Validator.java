package nl.devolksbank.hackathoneriktobias.Validator.Validators;

import android.content.Context;

import java.util.ArrayList;

import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidator;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.General.MainTextBlockValidator;

public class Validator {
    private ArrayList<BlockValidator> blockValidators;
    private ArrayList<BlockValidatorResponse> blockValidatorResponses;

    private ValidatorResponse validatorResponse;

    private MainTextBlockValidator mainTextBlockValidator;

    public Validator(Context context) {
        blockValidators = new ArrayList<>();
        blockValidatorResponses = new ArrayList<>();
        this.initializeBlockValidators(context);
    }

    protected void initializeBlockValidators(Context context) {
        this.mainTextBlockValidator = new MainTextBlockValidator(context);
        this.blockValidators.add(this.mainTextBlockValidator);
    }

    public ValidatorResponse validate(ValidatorInput input) {
        this.blockValidatorResponses.clear();

        // Give each BlockValidator the chance to say something about the input
        for (BlockValidator blockValidator : this.blockValidators) {
            BlockValidatorResponse response = blockValidator.validate(input);
            blockValidatorResponses.add(response);
        }

        this.validatorResponse = new ValidatorResponse(blockValidatorResponses);

        return validatorResponse;
    }
}
