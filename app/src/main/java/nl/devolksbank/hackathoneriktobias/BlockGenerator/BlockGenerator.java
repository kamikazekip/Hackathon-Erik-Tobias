package nl.devolksbank.hackathoneriktobias.BlockGenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

/**
 * Created by tobias
 * On 5/17/18.
 */

public abstract class BlockGenerator {

    public SparseArray<TextBlock> detectText(Bitmap bitmap, TextRecognizer textRecognizer) {

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        return textRecognizer.detect(frame);
    }

    public abstract String generatedBlocks(SparseArray<TextBlock> items);
}
