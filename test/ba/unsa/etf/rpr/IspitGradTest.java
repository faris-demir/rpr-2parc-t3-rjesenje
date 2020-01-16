package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IspitGradTest {
    @Test
    void testRazvijeniGrad() {
        Grad grad = new RazvijeniGrad();
        grad.setNaziv("Berlin");
        grad.setBrojStanovnika(1000000);
        assertEquals(100, grad.brojBolnica());
        // Rodio se novi građanin
        grad.setBrojStanovnika(1000001);
        assertEquals(101, grad.brojBolnica());
    }

    @Test
    void testNerazvijeniGrad() {
        Grad grad = new NerazvijeniGrad();
        grad.setNaziv("Mogadishu");
        grad.setBrojStanovnika(700000);
        assertEquals(7, grad.brojBolnica());
        // Rodio se novi građanin
        grad.setBrojStanovnika(700001);
        assertEquals(8, grad.brojBolnica());
    }

    @Test
    void testSrednjeRazvijeniGrad() {
        Grad grad = new SrednjeRazvijeniGrad();
        grad.setNaziv("Zenica");
        grad.setBrojStanovnika(100000);
        assertEquals(4, grad.brojBolnica());
        // Rodio se novi građanin
        grad.setBrojStanovnika(100001);
        assertEquals(5, grad.brojBolnica());
    }

}