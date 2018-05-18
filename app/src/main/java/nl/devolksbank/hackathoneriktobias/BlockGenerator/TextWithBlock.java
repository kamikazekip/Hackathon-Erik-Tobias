package nl.devolksbank.hackathoneriktobias.BlockGenerator;

import com.google.android.gms.vision.text.Text;

/**
 * Created by tobias
 * On 5/17/18.
 */

public class TextWithBlock {
    private Text text;
    private int box;

    public TextWithBlock(Text text, int box) {
        this.text = text;
        this.box = box;
    }

    public Text getText() {
        return text;
    }

    public int getBox() {
        return box;
    }


}
