package nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.General;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;

import java.io.IOException;
import java.util.List;

import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidator;
import nl.devolksbank.hackathoneriktobias.Validator.BlockValidators.BlockValidatorResponse;
import nl.devolksbank.hackathoneriktobias.Validator.Outcome;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;

public class MainTextBlockValidator extends BlockValidator {

    private BlockValidatorResponse response;
    private ValidatorInput input;

    private static final String couldNotCheckLanguage = "We konden de taal niet controleren, controleer zelf of de taal Nederlands is!";
    private List<LanguageProfile> languageProfiles;
    private LanguageDetector languageDetector;
    private TextObjectFactory languageTextObjectFactory;
    private TextObject languageTextObject;
    private Boolean languageProfilesLoaded = false;

    public MainTextBlockValidator(){
        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();
            languageProfilesLoaded = true;
        } catch (IOException e) {
            System.out.println("Kon geen taalprofielen inladen!");
        }

        //build language detector:
        languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                .withProfiles(languageProfiles)
                .build();

        //create a text object factory
        languageTextObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();
    }

    @Override
    public BlockValidatorResponse validate(ValidatorInput input) {
        response = new BlockValidatorResponse();
        this.input = input;
        if(input.mainText.contains("Brandsma")){
            response.outcome = Outcome.NoFraudDetected;
        } else {
            response.outcome = Outcome.FraudDetected;
        }

        checkLanguage();
        return response;
    }

    private void checkLanguage() {
        //load all languages:
        if(!languageProfilesLoaded){
            response.reasons.add(couldNotCheckLanguage);
            return;
        }

        //query:
        languageTextObject = languageTextObjectFactory.forText(this.input.mainText);
        Optional<LdLocale> lang = languageDetector.detect(languageTextObject);

        if (!lang.isPresent()) {
            response.reasons.add(couldNotCheckLanguage);
            return;
        }

        String langu = lang.get().getLanguage();
        if (!langu.equals("nl")) {
            response.reasons.add("De taal is niet nederlands, alle communicatie vanuit SNS is in de Nederlandse taal!");
            return;
        }

        response.reasons.add("De taal is gecontroleerd en goed gekeurd!");
    }

    public void checkForSpellingErrors() {

    }
}
