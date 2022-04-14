import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;


/* interfata pentru Operatiuni */
interface Operatiuni {
    public float CalculeazaDobanda();

    public float ActualizareSuma();

    public void Depunere(float suma);

    public void Extragere(float suma);
}

/* clasa banca */
class Banca {
    private String nume_banca;
    private List<Client> clienti;

    /* constructorul pentru Banca (se poate instantia lista in constructor fara a declara-o in argumentele constructorului) */
    public Banca(String nume_banca) {
        this.nume_banca = nume_banca;
        clienti = new ArrayList<Client>();
    }

    /* getter pentru nume_banca */
    public String getNume_banca() {
        return nume_banca;
    }

    /* getter pentru lista de clienti */
    public List<Client> getListaClienti() {
        return clienti;
    }

    /* adaugare in lista a unui Client in Banca */
    public void adaugareClient(Client client) throws IOException {
        /* se parcurge lista si se verifica daca numele si adresa exista in lista */
        for (int i = 0; i < getListaClienti().size(); i++)
            if (getListaClienti().get(i).getNume().equals(client.getNume())
                    && getListaClienti().get(i).getAdresa().equals(client.getAdresa())) {
                System.out.println("Clientul este deja inscris in aceasta banca!");
            }

        /* se citesc datele despre client si se adauga in lista */
        getListaClienti().add(client);
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        /* citire informatii client si contBancar */
        System.out.println("Numarul contului: ");
        String numarCont = read.readLine();

        System.out.println("Suma: ");
        float suma = Float.parseFloat(read.readLine());

        System.out.println("Moneda: ");
        String moneda = read.readLine();

        /* citire calendar dataDeschiderii si dataUltimeiOperatiuni */
        int zi, luna, an;
        Calendar dataDeschidere = Calendar.getInstance();
        System.out.println("Data deschiderii: ");

        System.out.println("Ziua: ");
        zi = Integer.parseInt(read.readLine());

        System.out.println("Luna: ");
        luna = Integer.parseInt(read.readLine());

        System.out.println("An: ");
        an = Integer.parseInt(read.readLine());

        /* setare dataDeschidere Calendar */
        dataDeschidere.set(an, luna - 1, zi);

        System.out.println("Data ultimei operatiuni: ");
        Calendar dataUltimaOperatiune = Calendar.getInstance();

        System.out.println("Ziua: ");
        zi = Integer.parseInt(read.readLine());

        System.out.println("Luna: ");
        luna = Integer.parseInt(read.readLine());

        System.out.println("An: ");
        an = Integer.parseInt(read.readLine());

        /* setare dataUltimaOperatiune Calendar */
        dataUltimaOperatiune.set(an, luna - 1, zi);

        /* Adaugare cont */
        ContBancar cont = new ContBancar(numarCont, suma, moneda, dataDeschidere, dataUltimaOperatiune);
        getListaClienti().get(getListaClienti().size() - 1).adaugareCont(cont);
    }
    
    /* functia toString() */
    public String toString() {
        return "Banca: " + getNume_banca() + "\n" + getListaClienti().toString() + " ";
    }

}

class Client {
    private String nume, adresa;
    private Set<ContBancar> conturi;

    /* constructorul pentru Client */
    public Client(String nume, String adresa) {
        this.nume = nume;
        this.adresa = adresa;
        conturi = new HashSet<ContBancar>();
    }

    /* getter pentru nume */
    public String getNume() {
        return nume;
    }

    /* getter pentru adresa */
    public String getAdresa() {
        return adresa;
    }

    /* getter pentru Set-ul de contBancar */
    public Set<ContBancar> getConturi() {
        return conturi;
    }

    /* functie pentru adaugare cont la un client */
    public void adaugareCont(ContBancar cont) {
        if (getConturi().size() == 5) {
            System.out.println("Clientul cu numele: " + getNume() + " a atins numarul maxim de conturi!");
            return;
        }
        getConturi().add(cont);
    }

    /* functia toString() Client */
    public String toString() {
        return "Client: " + getNume() + ", " + "Adresa: " + getAdresa() + " " + "Conturi: " + "\n" + getConturi().toString() + " ";
    }

}

class ContBancar implements Operatiuni {
    private String numarCont, moneda;
    private float suma;
    private Calendar dataDeschiderii, dataUltimeoOperatiuni;

    /* constructorul pentru ContBancar */
    public ContBancar(String numarCont, float suma, String moneda, Calendar dataDeschiderii,
            Calendar dataUltimeiOperatiuni) {
        this.numarCont = numarCont;
        this.suma = suma;
        this.moneda = moneda;
        this.dataDeschiderii = dataDeschiderii;
        this.dataUltimeoOperatiuni = dataUltimeiOperatiuni;
    }

    /* getter pentru numarCont */
    public String getNumarCont() {
        return numarCont;
    }

    /* getter pentru suma */
    public float getSuma() {
        return suma;
    }

    /* getter pentru moneda */
    public String getMoneda() {
        return moneda;
    }

    /* getter pentru dataDeschiderii */
    public Calendar getDataDeschiderii() {
        return dataDeschiderii;
    }

    /* getter pentru dataUltimeiOperatiuni */
    public Calendar getDataUltimeiOperatiuni() {
        return dataUltimeoOperatiuni;
    }

    /* functie pentru comparareDataDeschidere */
    public int comparaDataDeschiderii(Calendar o) {
        if (this.dataDeschiderii.get(Calendar.DAY_OF_MONTH) == o.get(Calendar.DAY_OF_MONTH)
                && this.dataDeschiderii.get(Calendar.MONTH) == o.get(Calendar.MONTH)
                && this.dataDeschiderii.get(Calendar.YEAR) == o.get(Calendar.YEAR))
            return 0;
        return 1;
    }

    /* functie pentru comparareDataUltimaOperatiune */
    public int comparaDataUltimeiOperatiuni(Calendar o) {
        if (this.dataUltimeoOperatiuni.get(Calendar.DAY_OF_MONTH) == o.get(Calendar.DAY_OF_MONTH)
                && this.dataUltimeoOperatiuni.get(Calendar.MONTH) == o.get(Calendar.MONTH)
                && this.dataUltimeoOperatiuni.get(Calendar.YEAR) == o.get(Calendar.YEAR))
            return 0;
        return 1;
    }

    /* functia CalculeazaDobanda() din interfata Operatiuni */
    @Override
    public float CalculeazaDobanda() {
        /* variabila Calendar pentru dataCurenta */
        Calendar dataCurenta = Calendar.getInstance();
        /* functia getTimeInMillis() returneaza numarul in milisecunde pentru ziua actuala,
        -> se ia numarul de milisecunde pentru ziua curenta si pentru dataUltimeiOperatiuni si se va face diferenta dintre cele doua pentru a obtine numarul de zile dintre cele doua zile */
        long today = dataCurenta.getTimeInMillis();
        long last_day = dataUltimeoOperatiuni.getTimeInMillis();
        /* 86.400.000 milisecunde intr-o zi */
        return (today - last_day) / 86400000;
    }

    /* functia ActualizareSuma() din interfata Operatiuni */
    @Override
    public float ActualizareSuma() {
        /* se verifica cerinta din enunt daca moneda este RON si daca suma este < 500 RON */
        if (getMoneda().equals("RON")) {

            /* se adauga cu 0.3 * numarul de zile */
            if (getSuma() < 500)
                this.suma = (float) (this.suma + (0.3 * CalculeazaDobanda()));

            /* daca suma este > 500, atunci se adauga cu 0.8 * numarul de zile */
            else
                this.suma = (float) (this.suma + (0.8 * CalculeazaDobanda()));
        }

        /* se verifica cerinta din enunt daca moneda este EUR, si se adauga 0.1 * numarul de zile, fara conditii in functie de suma */
        else if (getMoneda().equals("EUR"))
            this.suma = (float) (this.suma + (0.1 * CalculeazaDobanda()));

        /* dataUltimeiOperatiuni devine data curenta */
        dataUltimeoOperatiuni = Calendar.getInstance();
        return this.suma;
    }

    /* functia Depunere() din interfata Operatiuni */
    @Override
    public void Depunere(float suma) {
        /* avem nevoie de o variabila auxiliara */
        float suma_depusa = ActualizareSuma();
        /* se adauga suma depusa la variabila suma din clasa */
        this.suma = suma_depusa + suma;
    }

    /* functia Extragere() din intefata Operatiuni */
    @Override
    public void Extragere(float suma) {
        float suma_extrasa = ActualizareSuma();
        /* se scade suma_extrasa */
        this.suma = suma_extrasa - suma;
    }

    /* functia toString() ContBancar */
    public String toString() {
        return "Cont: " + getNumarCont() + ", " + getSuma() + ", " + getMoneda() + ", "
                + getDataDeschiderii().get(Calendar.DAY_OF_MONTH) + "/" + (getDataDeschiderii().get(Calendar.MONTH) + 1)
                + "/" + getDataDeschiderii().get(Calendar.YEAR) + " -- "
                + getDataUltimeiOperatiuni().get(Calendar.DAY_OF_MONTH) + "/"
                + (getDataUltimeiOperatiuni().get(Calendar.MONTH) + 1) + "/"
                + getDataUltimeiOperatiuni().get(Calendar.YEAR) + "\n";
    }
}

public class Activitate_banca {

    public static void afisareMeniu() {
        System.out.println("0. Iesire");
        System.out.println("1. Adaugare banca");
        System.out.println("2. Adaugare client");
        System.out.println("3. Adaugare cont");
        System.out.println("4. Afisare date introduse");
        System.out.println("5. Depunerea unei sume intr-un cont");
        System.out.println("6. Extragerea unei sume dintr-un cont");
        System.out.println("7 Transfer de bani intre doua conturi");
        System.out.println("Optiunea dvs: ");
    }
    
    /* Main */
    public static void main(String[] args) throws NumberFormatException, IOException {
        Vector<Banca> bank = new Vector<Banca>();
        int option, ok = 0, i, j, zi, luna, an;
        List<Client> clienti = null;
        Set<ContBancar> conturi = null;
        Iterator<ContBancar> it;
        ContBancar cont;
        do {
            afisareMeniu();
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            option = Integer.valueOf(read.readLine());
            switch (option) {

            /* Iesire */
            case 0:
                System.exit(0);
                break;
            
            /* Adaugare banca */
            case 1:
                System.out.println("Denumirea bancii: "); 
                String denumire_banca = read.readLine();

               /* Verificare existenta banca */
                for (i = 0; i < bank.size(); i++) {
                    if (bank.get(i).getNume_banca().equals(denumire_banca)) {
                        ok = 1;
                        System.out.println("Banca este deja existenta in sistem");
                        break;
                    }
                }
                if (ok == 0)
                    bank.add(new Banca(denumire_banca));
                break;
            
            /* Adaugare client */
            case 2:
                ok = 0;

                System.out.println("Numele clientului: ");
                String nume = read.readLine();

                System.out.println("Adresa clientului: ");
                String adresa = read.readLine();

                System.out.println("Denumirea bancii: ");
                denumire_banca = read.readLine();
                
                /* Verificare existenta banca */
                for (i = 0; i < bank.size(); i++) {
                    if (bank.get(i).getNume_banca().equals(denumire_banca)) {
                        ok = 1;
                        break;
                    }
                }
                if (ok == 0)
                    System.out.println("Banca nu exista!");
                else
                    bank.get(i).adaugareClient(new Client(nume, adresa));
                break;
            
            /* Adaugare cont */
            case 3:
                ok = 0; i = 0; j = 0;
                
                System.out.println("Numarul contului: ");
                String numarCont = read.readLine();
                
                System.out.println("Suma: ");
                float suma = Float.parseFloat(read.readLine());

                System.out.println("Moneda: ");
                String moneda = read.readLine();

                System.out.println("Data deschiderii: ");
                Calendar dataDeschidere = Calendar.getInstance();

                System.out.println("Ziua: ");
                zi = Integer.parseInt(read.readLine());

                System.out.println("Luna: ");
                luna = Integer.parseInt(read.readLine());

                System.out.println("An: ");
                an = Integer.parseInt(read.readLine());

                dataDeschidere.set(an, luna - 1, zi);

                System.out.println("Data ultimei operatiuni: ");
                Calendar dataUltimaOperatiune = Calendar.getInstance();

                System.out.println("Ziua: ");
                zi = Integer.parseInt(read.readLine());

                System.out.println("Luna: ");
                luna = Integer.parseInt(read.readLine());

                System.out.println("An: ");
                an = Integer.parseInt(read.readLine());

                dataUltimaOperatiune.set(an, luna - 1, zi);

                System.out.println("Nume: ");
                nume = read.readLine();

                System.out.println("Adresa: ");
                adresa = read.readLine();

                System.out.println("Denumirea bancii: ");
                denumire_banca = read.readLine();

                /* Verificare existenta banca si client */
                for (i = 0; i < bank.size() && ok == 0; i++)
                    if (bank.get(i).getNume_banca().equals(denumire_banca)) {
                        clienti = bank.get(i).getListaClienti();
                        for (j = 0; j < clienti.size(); j++)
                            if (clienti.get(j).getNume().equals(nume) && clienti.get(j).getAdresa().equals(adresa)) {
                                ok = 1;
                                break;
                            }
                    }
                if (ok == 0)
                    System.out.println("Datele bancii sau ale clientului sunt invalide");
                else
                    clienti.get(j).adaugareCont(
                            new ContBancar(numarCont, suma, moneda, dataDeschidere, dataUltimaOperatiune));
                break;
            
            /* Afisare */
            case 4:
                for (i = 0; i < bank.size(); i++)
                    System.out.println(bank.get(i).toString());
                break;
            
            /* Depunere suma cont */
            case 5:
                ok = 0; j = 0;
                
                System.out.println("Denumire banca: ");
                denumire_banca = read.readLine();

                System.out.println("Numele clientului: ");
                nume = read.readLine();

                System.out.println("Numar cont: ");
                numarCont = read.readLine();

                System.out.println("Moneda: ");
                moneda = read.readLine();

                /* Verificare existenta detalii cont si adaugare suma */
                for (i = 0; i < bank.size() && ok == 0; i++) {
                    if (bank.get(i).getNume_banca().equals(denumire_banca)) {
                        clienti = bank.get(i).getListaClienti();
                        for (j = 0; j < clienti.size() && ok == 0; j++) {
                            if (clienti.get(j).getNume().equals(nume)) {
                                conturi = clienti.get(j).getConturi();
                                it = conturi.iterator();
                                while (it.hasNext()) {
                                    cont = it.next();
                                    if (cont.getNumarCont().equals(numarCont) && cont.getMoneda().equals(moneda)) {
                                        System.out.println("Suma depusa: ");
                                        float suma_depusa = Float.parseFloat(read.readLine());
                                        cont.Depunere(suma_depusa);
                                        ok = 1;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (ok == 0)
                    System.out.println("Datele nu se potrivesc");
                break;
                
            /* Extragere suma */
            case 6:
                ok = 0; j = 0;
                
                System.out.println("Denumirea bancii: ");
                denumire_banca = read.readLine();

                System.out.println("Client: ");
                nume = read.readLine();

                System.out.println("Numar cont: ");
                numarCont = read.readLine();

                System.out.println("Moneda: ");
                moneda = read.readLine();
                
                for (i = 0; i < bank.size() && ok == 0; i++) {
                    if (bank.get(i).getNume_banca().equals(denumire_banca)) {
                        clienti = bank.get(i).getListaClienti();
                        for (j = 0; j < clienti.size() && ok == 0; j++) {
                            if (clienti.get(j).getNume().equals(nume)) {
                                conturi = clienti.get(j).getConturi();
                                it = conturi.iterator();
                                while (it.hasNext()) {
                                    cont = it.next();
                                    if (cont.getNumarCont().equals(numarCont) && cont.getMoneda().equals(moneda)) {
                                        System.out.println("Suma extrasa: ");
                                        float suma_extrasa = Float.parseFloat(read.readLine());
                                        if (suma_extrasa > cont.getSuma()) {
                                            System.out
                                                    .println("Suma extrasa este mai mare decat suma existenta in cont");
                                            return;
                                        } else {
                                            cont.Extragere(suma_extrasa);
                                            ok = 1;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (ok == 0)
                    System.out.println("Datele nu se potrivesc");
                break;
            
            /* Transfer suma intre conturi */
            case 7:
                ok = 0; j = 0;
                
                System.out.println("Date client expeditor: ");
                System.out.println("Denumire banca: ");
                denumire_banca = read.readLine();

                System.out.println("Nume client: ");
                nume = read.readLine();

                System.out.println("Numar cont: ");
                numarCont = read.readLine();

                System.out.println("Moneda: ");
                moneda = read.readLine();

                clienti = null;
                conturi = null;
                cont = null;

                /* Verificare existenta detalii cont */
                for (i = 0; i < bank.size() && ok == 0; i++) {
                    if (bank.get(i).getNume_banca().equals(denumire_banca)) {
                        clienti = bank.get(i).getListaClienti();
                        for (j = 0; j < clienti.size() && ok == 0; j++) {
                            if (clienti.get(j).getNume().equals(nume)) {
                                conturi = clienti.get(j).getConturi();
                                it = conturi.iterator();
                                while (it.hasNext()) {
                                    cont = it.next();
                                    if (cont.getNumarCont().equals(numarCont) && cont.getMoneda().equals(moneda)) {
                                        ok = 1;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (ok == 0)
                    System.out.println("Datele nu se potrivesc");
                
                /* Citire informatii cont destinatar */
                int ok_destinatar = 0;
                System.out.println("Date client destinatar: ");
                System.out.println("Denumire banca: ");
                String denumire_banca_destinatar = read.readLine();

                System.out.println("Nume client: ");
                String nume_destinatar = read.readLine();

                System.out.println("Numar cont: ");
                String numarCont_destinatar = read.readLine();

                System.out.println("Moneda: ");
                String moneda_destinatar = read.readLine();

                List<Client> client_destinatar = null;
                Set<ContBancar> conturi_destinatar = null;
                ContBancar cont_destinatar = null;

                /* Verificare informatii cont destinatar */
                for (i = 0; i < bank.size() && ok_destinatar == 0; i++)
                    if (bank.get(i).getNume_banca().equals(denumire_banca_destinatar)) {
                        client_destinatar = bank.get(i).getListaClienti();
                        for (j = 0; j < client_destinatar.size() && ok_destinatar == 0; j++) {
                            if (client_destinatar.get(j).getNume().equals(nume_destinatar)) {
                                conturi_destinatar = client_destinatar.get(j).getConturi();
                                it = conturi_destinatar.iterator();
                                while (it.hasNext()) {
                                    cont_destinatar = it.next();
                                    if (cont_destinatar.getNumarCont().equals(numarCont_destinatar)
                                            && cont_destinatar.getMoneda().equals(moneda_destinatar)) {
                                        ok_destinatar = 1;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                if (ok_destinatar == 0)
                    System.out.println("Datele sunt invalide");
                if (ok == 1 && ok_destinatar == 1) {
                    if (!cont.getMoneda().equals(cont_destinatar.getMoneda())) {
                        System.out.println("Cele doua conturi au valuta diferita!");
                    } else {
                        System.out.println("Suma de transfer: ");
                        float suma_transfer = Float.parseFloat(read.readLine());
                        if (suma_transfer > cont.getSuma()) {
                            System.out.println("Clientul nu dispune de suma necesara pentru transfer!");
                        } else {
                            cont.Extragere(suma_transfer);
                            cont_destinatar.Depunere(suma_transfer);
                        }
                    }
                }
                break;
            
            default:
                System.out.println("Optiune gresita!");
                break;
            }
        } while (option != 8);
    }
}
