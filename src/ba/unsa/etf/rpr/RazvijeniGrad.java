package ba.unsa.etf.rpr;

public class RazvijeniGrad extends Grad {
    public RazvijeniGrad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        super(id, naziv, brojStanovnika, drzava);
    }

    public RazvijeniGrad() {
    }

    @Override
    public int brojBolnica() {
        return (getBrojStanovnika()+9999) / 10000;
    }
}
