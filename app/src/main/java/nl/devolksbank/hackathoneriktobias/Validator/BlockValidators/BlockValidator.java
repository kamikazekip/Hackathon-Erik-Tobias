package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators;

import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;

public abstract class BlockValidator {
    public abstract void validate(ValidatorInput input, BlockValidatorResponseListener listener);

    public interface BlockValidatorResponseListener {
        void receiveBlockValidatorResponse(BlockValidatorResponse response);
    }
}
