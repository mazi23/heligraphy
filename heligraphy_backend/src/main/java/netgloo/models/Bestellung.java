package netgloo.models;

//import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mazi on 14.01.17.
 */
@Entity
@Table(name = "bestellung")
public class Bestellung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idBestellung;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bestellnummer;
    @OneToOne
    private User user;
    @OneToOne
    private Adresse rechnungsAdresse;
    @OneToOne
    private Adresse lieferAdresse;
    private int bildgruppenID;
    private double summenetto;
    private double summebrutto;
    private double summemwst;
    private Integer mwst;
    private Timestamp auftragsDatum;
    private String versandart;
    private double versankosten;

    @OneToMany
    private Set<BestellElement> bilder = new HashSet<>();


    public Bestellung() {
    }

    public long getIdBestellung() {
        return idBestellung;
    }


    public long getBestellnummer() {
        return bestellnummer;
    }

    public void setBestellnummer(long bestellnummer) {
        this.bestellnummer = bestellnummer;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public Adresse getRechnungsAdresse() {
        return rechnungsAdresse;
    }

    public void setRechnungsAdresse(Adresse rechnungsAdresse) {
        this.rechnungsAdresse = rechnungsAdresse;
    }

    public Adresse getLieferAdresse() {
        return lieferAdresse;
    }

    public void setLieferAdresse(Adresse lieferAdresse) {
        this.lieferAdresse = lieferAdresse;
    }

    public int getBildgruppenID() {
        return bildgruppenID;
    }

    public void setBildgruppenID(int bildgruppenID) {
        this.bildgruppenID = bildgruppenID;
    }

    public double getSummenetto() {
        return summenetto;
    }

    public void setSummenetto(double summenetto) {
        this.summenetto = summenetto;
    }

    public double getSummebrutto() {
        return summebrutto;
    }

    public void setSummebrutto(double summebrutto) {
        this.summebrutto = summebrutto;
    }

    public double getSummemwst() {
        return summemwst;
    }

    public void setSummemwst(double summemwst) {
        this.summemwst = summemwst;
    }

    public Integer getMwst() {
        return mwst;
    }

    public void setMwst(Integer mwst) {
        this.mwst = mwst;
    }

    public Timestamp getAuftragsDatum() {
        return auftragsDatum;
    }

    public void setAuftragsDatum(Timestamp auftragsDatum) {
        this.auftragsDatum = auftragsDatum;
    }

    public Set<BestellElement> getBilder() {
        return bilder;
    }

    public void setBilder(Set<BestellElement> bilder) {
        this.bilder = bilder;
    }

    public void addBestellElement(BestellElement element){
        bilder.add(element);
    }

    public void setIdBestellung(long idBestellung) {
        this.idBestellung = idBestellung;
    }

    public String getVersandart() {
        return versandart;
    }

    public void setVersandart(String versandart) {
        this.versandart = versandart;
    }

    public double getVersankosten() {
        return versankosten;
    }

    public void setVersankosten(double versankosten) {
        this.versankosten = versankosten;
    }
}
