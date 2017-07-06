package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by mazi on 03.05.17.
 */
@Entity(name = "Fotograf")
public class Fotograf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double anteil;

    public Fotograf() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAnteil() {
        return anteil;
    }

    public void setAnteil(double anteil) {
        this.anteil = anteil;
    }
}
