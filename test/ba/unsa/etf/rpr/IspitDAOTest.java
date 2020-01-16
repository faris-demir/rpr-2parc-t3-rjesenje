package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IspitDAOTest {

    @Test
    void testIzmijeniGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad bech = dao.glavniGrad("Austrija");
        NerazvijeniGrad b2 = new NerazvijeniGrad(bech.getId(), bech.getNaziv(), 800001, bech.getDrzava());
        dao.izmijeniGrad(b2);

        Grad b3 = dao.glavniGrad("Austrija");
        assertTrue(b3 instanceof NerazvijeniGrad);
        assertEquals(9, b3.brojBolnica());
    }

   @Test
    void testDodajGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        SrednjeRazvijeniGrad leeds = new SrednjeRazvijeniGrad(0, "Leeds", 350000, vb);

        dao.dodajGrad(leeds);

        Grad s2 = null;
        for(Grad grad : dao.gradovi()) {
            if (grad.getNaziv().equals("Leeds"))
                s2 = grad;
        }
        assertNotNull(s2);

        assertTrue(s2 instanceof SrednjeRazvijeniGrad);
        assertEquals(14, s2.brojBolnica());
    }


    @Test
    void testNadjiGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        SrednjeRazvijeniGrad leeds = new SrednjeRazvijeniGrad(0, "Leeds", 350000, vb);

        dao.dodajGrad(leeds);

        Grad s2 = dao.nadjiGrad("Leeds");
        assertNotNull(s2);

        assertTrue(s2 instanceof SrednjeRazvijeniGrad);
        assertEquals(14, s2.brojBolnica());
    }


    @Test
    void testNadjiDrzavu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        NerazvijeniGrad sarajevo = new NerazvijeniGrad(0, "Sarajevo", 350000, vb);

        dao.dodajGrad(sarajevo);

        Grad s2 = dao.nadjiGrad("Sarajevo");
        assertNotNull(s2);

        Drzava bih = new Drzava(0, "Bosna i Hercegovina", s2);
        dao.dodajDrzavu(bih);

        Drzava d2 = dao.nadjiDrzavu("Bosna i Hercegovina");
        assertNotNull(d2);

        Grad s3 = d2.getGlavniGrad();
        assertTrue(s3 instanceof NerazvijeniGrad);
        assertEquals(4, s3.brojBolnica());
    }


    @Test
    void testPromjenaTipa() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        SrednjeRazvijeniGrad leeds = new SrednjeRazvijeniGrad(0, "Leeds", 350000, vb);

        dao.dodajGrad(leeds);

        Grad s2 = dao.nadjiGrad("Leeds");
        assertNotNull(s2);

        assertTrue(s2 instanceof SrednjeRazvijeniGrad);

        RazvijeniGrad l2 = new RazvijeniGrad(s2.getId(), "Leeds", 350000, vb);
        dao.izmijeniGrad(l2);

        Grad s3 = dao.nadjiGrad("Leeds");
        assertNotNull(s3);

        assertTrue(s3 instanceof RazvijeniGrad);

    }
}
