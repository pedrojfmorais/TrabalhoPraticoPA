package pt.isec.pa.apoio_poe.model.data.pessoas;

public class Docente extends Pessoa implements Comparable<Docente>{
    public Docente(String nome, String email){
        super(nome, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;

        Docente docente = (Docente) o;

        return getEmail().equals(docente.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }

    @Override
    public int compareTo(Docente o) {
        return getEmail().compareTo(o.getEmail());
    }

    @Override
    public Docente clone() {
        return (Docente) super.clone();
    }
}
