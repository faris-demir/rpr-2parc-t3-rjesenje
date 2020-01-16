package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GradController {
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava;
    public ChoiceBox choiceTipGrada;
    public ObservableList<Drzava> listDrzave;
    //MOJE DODAVANJE CHOICE-A
    //public ObservableList<String> listTipova
    private Grad grad;

    public GradController(Grad grad, ArrayList<Drzava> drzave) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
        //MOJE DDOAVANJE CHOICE-A
//        ArrayList<String> lista = new ArrayList<>();
//        lista.add("Razvijen");
//        lista.add("Srednje razvijen");
//        lista.add("Nerazvijen");
//        listTipova = FXCollections.observableArrayList(lista);
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(listDrzave);
        //MOJE DDOAVANJE CHOICE-A
        //choiceTipGrada.setItems(listTipova);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            // choiceDrzava.getSelectionModel().select(grad.getDrzava());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave
            for (Drzava drzava : listDrzave)
                if (drzava.getId() == grad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
            //MOJE DDOAVANJE CHOICE-A
//            for (String tip : listTipova) {
//                if (grad instanceof RazvijeniGrad && tip.equals("Razvijen")) choiceTipGrada.getSelectionModel().select(tip);
//                else if (grad instanceof SrednjeRazvijeniGrad && tip.equals("Srednje razvijen")) choiceTipGrada.getSelectionModel().select(tip);
//                else if (grad instanceof NerazvijeniGrad && tip.equals("Nerazvijen")) choiceTipGrada.getSelectionModel().select(tip);
//            }
            if (grad instanceof SrednjeRazvijeniGrad)
                choiceTipGrada.setValue("Srednje razvijen");
            else if (grad instanceof NerazvijeniGrad)
                choiceTipGrada.setValue("Nerazvijen");
        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }
    }

    public Grad getGrad() {
        return grad;
    }

    public void clickCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }


        int brojStanovnika = 0;
        try {
            brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (brojStanovnika <= 0) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        // Čuvamo id da bismo mogli kreirati novi Grad
        int oldId = 0;
        if (grad != null) oldId = grad.getId();

        if (choiceTipGrada.getValue().equals("Razvijen"))
            grad = new RazvijeniGrad();
        else if (choiceTipGrada.getValue().equals("Srednje razvijen"))
            grad = new SrednjeRazvijeniGrad();
        else
            grad = new NerazvijeniGrad();
        grad.setId(oldId);
        grad.setNaziv(fieldNaziv.getText());
        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
        grad.setDrzava(choiceDrzava.getValue());
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
}
