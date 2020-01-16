package ba.unsa.etf.rpr;

public class NerazvijeniGrad extends Grad  {
    public NerazvijeniGrad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        super(id, naziv, brojStanovnika, drzava);
    }

    public NerazvijeniGrad() {
    }

    @Override
    public int brojBolnica() {
        return (getBrojStanovnika()+99999) / 100000;
    }
}
