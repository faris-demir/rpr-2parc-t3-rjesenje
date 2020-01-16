package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGlavnaTest {
    Stage theStage;
    GlavnaController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        ctrl = new GlavnaController();
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
    public void testDodajGradNerazvijeni(FxRobot robot) {
        ctrl.resetujBazu();

        // Otvaranje forme za dodavanje
        robot.clickOn("#btnDodajGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#fieldNaziv");
        robot.write("Sarajevo");

        robot.clickOn("#fieldBrojStanovnika");
        robot.write("350000");

        robot.clickOn("#choiceTipGrada");
        robot.clickOn("Nerazvijen");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je Sarajevo dodano u bazu?
        GeografijaDAO dao = GeografijaDAO.getInstance();
        assertEquals(6, dao.gradovi().size());

        Grad sarajevo = null;
        for(Grad grad : dao.gradovi())
            if (grad.getNaziv().equals("Sarajevo"))
                sarajevo = grad;
        assertNotNull(sarajevo);
        assertTrue(sarajevo instanceof NerazvijeniGrad);

        assertEquals(4, sarajevo.brojBolnica());
    }

    @Test
    public void testIzmijeniTipGrada(FxRobot robot) {
        ctrl.resetujBazu();

        // Graz ne smije biti nerazvijen jer je to "varanje"
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad graz = dao.nadjiGrad("Graz");
        assertFalse(graz instanceof NerazvijeniGrad);

        // Mijenjamo grad Graz
        robot.clickOn("Graz");
        robot.clickOn("#btnIzmijeniGrad");

        // Čekamo da dijalog postane vidljiv
        robot.clickOn("#choiceTipGrada");
        robot.clickOn("Nerazvijen");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je Graz nerazvijeni grad?
        Grad graz2 = dao.nadjiGrad("Graz");
        assertTrue(graz2 instanceof NerazvijeniGrad);
        assertEquals(3, graz2.brojBolnica());
    }


}
