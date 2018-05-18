package nl.devolksbank.hackathoneriktobias.Validator.Validators;

import android.content.Context;

import java.util.ArrayList;

import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidator;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.General.MainTextBlockValidator;

public class Validator implements BlockValidator.BlockValidatorResponseListener {
    private ArrayList<BlockValidator> blockValidators;
    private ArrayList<BlockValidatorResponse> blockValidatorResponses;

    private ValidatorResponseListener listener;
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

    public void validate(ValidatorInput input, ValidatorResponseListener listener) {
        this.listener = listener;
        this.blockValidatorResponses.clear();

        // Give each BlockValidator the chance to say something about the input
        for (BlockValidator blockValidator : this.blockValidators) {
            blockValidator.validate(input, this);
        }
    }

    @Override
    public void receiveBlockValidatorResponse(BlockValidatorResponse response) {
        blockValidatorResponses.add(response);
        if (blockValidatorResponses.size() == blockValidators.size()){
            this.validatorResponse = new ValidatorResponse(blockValidatorResponses);
            this.listener.receiveValidatorResponse(this.validatorResponse);
        }
    }

    public interface ValidatorResponseListener {
        public void receiveValidatorResponse(ValidatorResponse response);
    }
}
