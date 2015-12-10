package tc.easygo;
import java.io.Serializable;
/**
 * Created by ikromaulia on 12/1/2015.
 */
public class TipsModel implements Serializable{
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
