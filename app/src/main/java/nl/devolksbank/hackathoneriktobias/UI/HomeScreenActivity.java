package nl.devolksbank.hackathoneriktobias.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.devolksbank.hackathoneriktobias.BlockGenerator.LetterBlockGenerator;
import nl.devolksbank.hackathoneriktobias.R;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.LetterValidator;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorResponse;

public class HomeScreenActivity extends FragmentActivity implements OutcomeFragment.OutcomeFragmentInteractionListener, HomeStartFragment.HomeStartListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    private FrameLayout fragmentContainer;
    private HomeStartFragment homeStartFragment;
    private OutcomeFragment outcomeFragment;

    private LetterValidator letterValidator;
    private ValidatorInput validatorInput;
    private ValidatorResponse validatorResponse;

    private Boolean cameFromCamera;

    public HomeScreenActivity() {
        letterValidator = new LetterValidator();
        validatorInput = new ValidatorInput();
        validatorInput.mainText = "Lol stuur je pinpas op haha xD";
        this.cameFromCamera = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.homeStartFragment = new HomeStartFragment();
        this.outcomeFragment = new OutcomeFragment();
        fragmentContainer = findViewById(R.id.fragment_container);
        this.showHomeStartFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.cameFromCamera) {
            this.showOutcome();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            detectText();
        }
    }

    private void detectText() {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Snackbar.make(findViewById(R.id.fragment_container), R.string.text_recognizer_error, Snackbar.LENGTH_LONG);
        } else {
            LetterBlockGenerator blockGenerator = new LetterBlockGenerator();
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            SparseArray<TextBlock> list = blockGenerator.detectText(bitmap, textRecognizer);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "nl.devolksbank.hackathoneriktobias.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void showHomeStartFragment() {
        this.showFragment(this.homeStartFragment);
    }

    private void showOutcome() {
        validatorResponse = letterValidator.validate(validatorInput);
        outcomeFragment.setValidatorResponse(validatorResponse);
        this.showFragment(this.outcomeFragment);
    }

    public void onCameraClicked(View v) {
        this.cameFromCamera = true;
        dispatchTakePictureIntent();
    }

    public void onAgainClicked(View v) {
        this.showHomeStartFragment();
    }
}

