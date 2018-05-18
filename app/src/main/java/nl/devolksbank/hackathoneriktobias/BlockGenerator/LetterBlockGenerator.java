package nl.devolksbank.hackathoneriktobias.BlockGenerator;

import android.util.SparseArray;

import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tobias
 * On 5/17/18.
 */

public class LetterBlockGenerator extends BlockGenerator {
    @Override
    public String generatedBlocks(SparseArray<TextBlock> items) {
        List<TextWithBlock> mainTextLines = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            TextBlock textBlock = items.valueAt(i);
            List<? extends Text> textComponents = textBlock.getComponents();
            for (Text text : textComponents) {
                TextWithBlock textWithBlock = new TextWithBlock(text, i);
                mainTextLines.add(textWithBlock);
            }
        }

        //Sorting everything on the letter.
        Collections.sort(mainTextLines, (t1, t2) -> {
            int diffOfTops = t1.getText().getBoundingBox().top - t2.getText().getBoundingBox().top;
            int diffOfLefts = t1.getText().getBoundingBox().left - t2.getText().getBoundingBox().left;

            if (diffOfTops != 0) {
                return diffOfTops;
            }
            return diffOfLefts;
        });

        List<TextWithBlock> firstMainBlockLines = new ArrayList<>();
        for (TextWithBlock t : mainTextLines) {
            if (t.getText().getValue().startsWith("Beste")) {
                mainTextLines.removeAll(firstMainBlockLines);
                break;
            }
            firstMainBlockLines.add(t);
        }

        Collections.sort(firstMainBlockLines, (o1, o2) -> Integer.compare(o1.getBox(), o2.getBox()));

        StringBuilder mainTextBuilder = new StringBuilder();
        for (TextWithBlock textWithBlock : mainTextLines) {
            Text text = textWithBlock.getText();
            if (text != null && text.getValue() != null) {
                mainTextBuilder.append(text.getValue()).append(" ");
            }
        }

        StringBuilder firstMainTextBuilder = new StringBuilder();
        for (TextWithBlock textWithBlock : firstMainBlockLines) {
            Text text = textWithBlock.getText();
            if (text != null && text.getValue() != null) {
                firstMainTextBuilder.append(text.getValue()).append(" ");
            }
        }

        //textBuilder.toString is the end result.
        return mainTextBuilder.toString();
    }
}
