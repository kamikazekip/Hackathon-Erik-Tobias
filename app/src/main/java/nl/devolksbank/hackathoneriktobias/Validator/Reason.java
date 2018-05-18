package nl.devolksbank.hackathoneriktobias.Validator;

public class Reason {
    public Outcome outcome;
    public String text;

    public Reason(Outcome outcome){
        this.outcome = outcome;
    }

    public Reason(String text){
        this.text = text;
    }

    public Reason(Outcome outcome, String text) {
        this.outcome = outcome;
        this.text = text;
    }

}
