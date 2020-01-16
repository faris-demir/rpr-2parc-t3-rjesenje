package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGradControllerSet {
    Stage theStage;
    GradController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));

        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        SrednjeRazvijeniGrad leeds = new SrednjeRazvijeniGrad(0, "Leeds", 350000, vb);

        ctrl = new GradController(leeds, dao.drzave());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testIspravnaVrijednost(FxRobot robot) {
        ChoiceBox cb = robot.lookup("#choiceTipGrada").queryAs(ChoiceBox.class);
        assertEquals("Srednje razvijen", cb.getValue());
    }


    @Test
    public void testPromjenaTipa(FxRobot robot) {
        robot.clickOn("#choiceTipGrada");
        robot.clickOn("Razvijen");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Grad leeds = ctrl.getGrad();
        assertTrue(leeds instanceof RazvijeniGrad);
    }

}
