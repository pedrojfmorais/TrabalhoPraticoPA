package pt.isec.pa.apoio_poe.model.data.pessoas;

/**
 * Classe Docente com as informações dos Docentes, derivada da classe Pessoa
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 *
 */
public class Docente extends Pessoa implements Comparable<Docente>{
    /**
     * Construtor público
     *
     * @param nome nome do Docente
     * @param email email do Docente
     */
    public Docente(String nome, String email){
        super(nome, email);
    }

    /**
     * Comparar Docentes pelo email
     * @param o objeto a comparar
     * @return boolean - retorna se os dois docentes tem o email igual
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;

        Docente docente = (Docente) o;

        return getEmail().equals(docente.getEmail());
    }

    /**
     * Gera o hashCode para os objetos Docente
     * @return hashCode - retorna um inteiro único que identifica este objeto
     */
    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }

    /**
     * Compara este objeto Docente a outro através do email
     * @param o objeto a comparar
     * @return boolean - retorna qual deles é lexicograficamente superior ao outro
     */
    @Override
    public int compareTo(Docente o) {
        return getEmail().compareTo(o.getEmail());
    }

    /**
     * Obter clone do objeto Docente
     * @return Docente - clone deste objeto Docente
     */
    @Override
    public Docente clone() {
        return (Docente) super.clone();
    }
}
