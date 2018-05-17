package nl.devolksbank.hackathoneriktobias.UI;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.dynamic.SupportFragmentWrapper;

import nl.devolksbank.hackathoneriktobias.R;
import nl.devolksbank.hackathoneriktobias.UI.OutcomeFragment.OutcomeFragmentInteractionListener;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.LetterValidator;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorResponse;

public class HomeScreenActivity extends AppCompatActivity implements OutcomeFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private ValidatorInput validatorInput;
    private LetterValidator letterValidator;
    private OutcomeFragment outcomeFragment;

    public HomeScreenActivity() {
        this.validatorInput = new ValidatorInput();
        this.validatorInput.mainText = "Lol stur je digipas eens op";
        letterValidator = new LetterValidator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.fragmentManager = getSupportFragmentManager();

        ValidatorResponse response = letterValidator.validate(this.validatorInput);
        outcomeFragment = (OutcomeFragment) this.fragmentManager.findFragmentById(R.id.outcome_fragment);
        outcomeFragment.setValidatorResponse(response);
    }

    @Override
    public void onOutcomeDismissPressed() {

    }
}
