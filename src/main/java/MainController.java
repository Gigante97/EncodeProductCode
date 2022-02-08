import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    public TextField cis;
    public TextField productcode;

    private void updateField() {
        Platform.runLater(()->{
            cis.setText("KM");
        });
    }



    public void encode(ActionEvent actionEvent) {
        String text = cis.getText();
        String gtin = text.substring(2,16);
        String serial = text.substring(18,31);
        String gtinInHex = Long.toHexString(Long.parseLong(gtin));
        if (gtinInHex.length()<12) {
            int check = 12 - gtinInHex.length();
            for ( int i=0;i<check;i++) {
                gtinInHex = "0"+gtinInHex;
            }

        }
        gtinInHex = gtinInHex.toUpperCase();
        serial=serial.chars().mapToObj(Integer::toHexString).collect(Collectors.joining()).toUpperCase();

        String kmOfd = "444D"+gtinInHex+serial;
        if (kmOfd.length()<42) {
            int ch = 42-kmOfd.length();
            for (int i=0; i<ch; i++){
                kmOfd = kmOfd+"20";
            }
        }
        productcode.setText(kmOfd);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateField();
    }
}
