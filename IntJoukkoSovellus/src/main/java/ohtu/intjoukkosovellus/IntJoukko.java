package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        ljono = new int[KAPASITEETTI];
        alustaLjono();
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {

        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");//heitin vaan jotain :D
        }

        this.kasvatuskoko = kasvatuskoko;
        ljono = new int[KAPASITEETTI];
        alustaLjono();

    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);

    }

//   
    private void alustaLjono() {
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }

    }

    private void alustaTaulukkoUudelleen() {
        int[] taulukkoOld = new int[ljono.length];
        taulukkoOld = ljono;
        kopioiTaulukko(ljono, taulukkoOld);
        ljono = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(taulukkoOld, ljono);
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm % ljono.length == 0) {
                alustaTaulukkoUudelleen();
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;

    }

    public boolean poista(int luku) {
        int kohta;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                kohta = i; //siis luku löytyy tuosta kohdasta :D
                ljono[kohta] = 0;
                laskeUusiArvo(kohta);
                return true;

            }
        }
        return false;
    }
    private void laskeUusiArvo(int kohta){
        int tamaKohta = kohta;
        ljono[tamaKohta] = 0;
        korjaaIndeksitJonosta(kohta);
        
    }

    private void korjaaIndeksitJonosta(int kohta) {
        int apu;
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            apu = ljono[j];
            ljono[j] = ljono[j + 1];
            ljono[j + 1] = apu;
        }
        alkioidenLkm--;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String tuotos = "{";
        for (int i = 0; i < ljono.length; i++) {
            if (ljono[i] != 0) {
                tuotos += ljono[i];
            }
            if (i < alkioidenLkm - 1 && ljono[i] != 0) {
                tuotos += ", ";
            }
        }
        return tuotos + "}";

    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste2(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();

        for (int alkioAssa : a.toIntArray()) {
            x.lisaa(alkioAssa);
        }

        for (int alkioBssa : b.toIntArray()) {
            x.lisaa(alkioBssa);
        }

        return x;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            int lukuAssa = aTaulu[i];
            if (b.kuuluu(lukuAssa)) {
                y.lisaa(lukuAssa);
            }

        }
        return y;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
            z.poista(i);
        }

        return z;
    }

}
