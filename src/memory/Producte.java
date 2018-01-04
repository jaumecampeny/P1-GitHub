package memory;


import java.util.Objects;

public class Producte {
    private int id;
    private String name;

    public Producte() {
        this.id = 0;
        this.name = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producte)) return false;
        Producte producte = (Producte) o;
        return id == producte.id &&
                Objects.equals(name, producte.name);
    }

    @Override
    public String toString() {
        return "Producte{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
