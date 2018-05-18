package nl.devolksbank.hackathoneriktobias.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.devolksbank.hackathoneriktobias.R;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.Outcome;
import nl.devolksbank.hackathoneriktobias.Validator.Reason;
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
    public HashMap<Outcome, String> outcomeColorMap;

    private ImageView outcomeIcon;
    private TextView outcomeLabel;
    private TextView outcomeExplanationLabel;

    public OutcomeFragment() {
        this.outcomeIconMap = new HashMap<>();
        this.outcomeIconMap.put(Outcome.FraudDetected, R.drawable.fraud_detected);
        this.outcomeIconMap.put(Outcome.PossibleFraudDetected, R.drawable.possible_fraud_detected);
        this.outcomeIconMap.put(Outcome.NoFraudDetected, R.drawable.no_fraud_detected);

        this.outcomeLabelMap = new HashMap<>();
        this.outcomeLabelMap.put(Outcome.FraudDetected, "Fraude gedetecteerd!");
        this.outcomeLabelMap.put(Outcome.PossibleFraudDetected, "Mogelijke fraude gedetecteerd!");
        this.outcomeLabelMap.put(Outcome.NoFraudDetected, "Geen fraude gedetecteerd!");

        this.outcomeColorMap = new HashMap<>();
        this.outcomeColorMap.put(Outcome.FraudDetected, "#FF0000");
        this.outcomeColorMap.put(Outcome.PossibleFraudDetected, "#FFFF00");
        this.outcomeColorMap.put(Outcome.NoFraudDetected, "00FF00");
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
        outcomeExplanationLabel = rootView.findViewById(R.id.outcome_explanation_label);
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
        outcomeExplanationLabel.setText(getOutcomeExplanationText());
    }

    public Integer getOutcomeIcon(){
        return outcomeIconMap.get(this.validatorResponse.outcome);
    }

    public String getOutcomeLabel(){
        return outcomeLabelMap.get(this.validatorResponse.outcome);
    }

    public String getOutcomeExplanationText(){
        String result = "";
        HashMap<Outcome, ArrayList<String>> map = new HashMap<>();
        map.put(Outcome.FraudDetected, new ArrayList<>());
        map.put(Outcome.PossibleFraudDetected, new ArrayList<>());
        map.put(Outcome.NoFraudDetected, new ArrayList<>());

        for(BlockValidatorResponse response : validatorResponse.blockValidatorResponses){
            for (Reason reason: response.reasons) {
                String text = getOutcomeExplanationTextLine(reason);
                map.get(reason.outcome).add(text);
            }
        }

        for(String string : map.get(Outcome.FraudDetected)) {
            result += string;
        }
        for(String string : map.get(Outcome.PossibleFraudDetected)) {
            result += string;
        }
        for(String string : map.get(Outcome.NoFraudDetected)) {
            result += string;
        }
        return result;
    }

    public String getOutcomeExplanationTextLine(Reason reason){
        String color = this.outcomeColorMap.get(reason.outcome);
        return reason.text + "\n\n";

    }

    public interface OutcomeFragmentInteractionListener {

    }
}
