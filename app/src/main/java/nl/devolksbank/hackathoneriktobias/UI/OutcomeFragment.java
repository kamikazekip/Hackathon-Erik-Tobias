package nl.devolksbank.hackathoneriktobias.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import nl.devolksbank.hackathoneriktobias.R;
import nl.devolksbank.hackathoneriktobias.Validator.Outcome;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorResponse;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OutcomeFragment.OutcomeFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class OutcomeFragment extends Fragment {


    private static final String VALIDATOR_RESPONSE = "param1";

    private ValidatorResponse validatorResponse;

    private OutcomeFragmentInteractionListener mListener;
    public HashMap<Outcome, Integer> outcomeIconMap;
    public HashMap<Outcome, String> outcomeLabelMap;

    private ImageView outcomeIcon;
    private TextView outcomeLabel;

    public OutcomeFragment() {
        this.outcomeIconMap = new HashMap<Outcome, Integer>();
        this.outcomeIconMap.put(Outcome.FraudDetected, R.drawable.fraud_detected);
        this.outcomeIconMap.put(Outcome.PossibleFraudDetected, R.drawable.possible_fraud_detected);
        this.outcomeIconMap.put(Outcome.NoFraudDetected, R.drawable.no_fraud_detected);

        this.outcomeLabelMap = new HashMap<Outcome, String>();
        this.outcomeLabelMap.put(Outcome.FraudDetected, "Fraude gedetecteerd!");
        this.outcomeLabelMap.put(Outcome.PossibleFraudDetected, "Mogelijke fraude gedetecteerd!");
        this.outcomeLabelMap.put(Outcome.NoFraudDetected, "Geen fraude gedetecteerd!");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_outcome, container, false);
        outcomeIcon = rootView.findViewById(R.id.outcome_icon);
        outcomeLabel = rootView.findViewById(R.id.outcome_label);
        draw();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OutcomeFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setValidatorResponse(ValidatorResponse validatorResponse){
        this.validatorResponse = validatorResponse;
    }

    public void draw(){
        outcomeIcon.setImageResource(getOutcomeIcon());
        outcomeLabel.setText(getOutcomeLabel());
    }

    public Integer getOutcomeIcon(){
        return outcomeIconMap.get(this.validatorResponse.outcome);
    }

    public String getOutcomeLabel(){
        return outcomeLabelMap.get(this.validatorResponse.outcome);
    }

    public interface OutcomeFragmentInteractionListener {

    }
}
