package ba.unsa.etf.rpr;

public class SrednjeRazvijeniGrad extends Grad {
    public SrednjeRazvijeniGrad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        super(id, naziv, brojStanovnika, drzava);
    }

    public SrednjeRazvijeniGrad() {
    }

    @Override
    public int brojBolnica() {
        return (getBrojStanovnika()+24999) / 25000;
    }
}
