package nl.devolksbank.hackathoneriktobias.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nl.devolksbank.hackathoneriktobias.R;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.LetterValidator;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorInput;
import nl.devolksbank.hackathoneriktobias.Validator.Validators.ValidatorResponse;

public class HomeScreenActivity extends FragmentActivity implements OutcomeFragment.OutcomeFragmentInteractionListener, HomeStartFragment.HomeStartListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    private FrameLayout fragmentContainer;
    private HomeStartFragment homeStartFragment;
    private OutcomeFragment outcomeFragment;

    private LetterValidator letterValidator;
    private ValidatorInput validatorInput;
    private ValidatorResponse validatorResponse;

    private Boolean cameFromCamera;

    public HomeScreenActivity(){
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
        if(this.cameFromCamera){
            this.showOutcome();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            galleryAddPicture();
            detectText();
        }
    }

    private void detectText() {
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            //Snackbar.make(findViewById(R.id.home_screen), R.string.text_recognizer_error, Snackbar.LENGTH_LONG);
        }else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);



            List<Text> textLines = new ArrayList<>();
            List<String> langList = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                TextBlock textBlock = items.valueAt(i);
                langList.add(textBlock.getLanguage());
                List<? extends Text> textComponents = textBlock.getComponents();
                textLines.addAll(textComponents);
            }


            Collections.sort(textLines, (t1, t2) -> {
                int diffOfTops = t1.getBoundingBox().top -  t2.getBoundingBox().top;
                int diffOfLefts = t1.getBoundingBox().left - t2.getBoundingBox().left;

                if (diffOfTops != 0) {
                    return diffOfTops;
                }
                return diffOfLefts;
            });

            StringBuilder textBuilder = new StringBuilder();
            for (Text text : textLines) {
                if (text != null && text.getValue() != null) {
                    textBuilder.append(text.getValue()).append("\n");
                }
            }
            //textBuilder.toString is the end result.
            System.out.println(textBuilder.toString());
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPicture() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void showHomeStartFragment(){
        this.showFragment(this.homeStartFragment);
    }

    private void showOutcome() {
        validatorResponse = letterValidator.validate(validatorInput);
        outcomeFragment.setValidatorResponse(validatorResponse);
        this.showFragment(this.outcomeFragment);
    }

    public void onCameraClicked(View v){
        this.cameFromCamera = true;
        dispatchTakePictureIntent();
    }

    public void onAgainClicked(View v){
        this.showHomeStartFragment();
    }
}
