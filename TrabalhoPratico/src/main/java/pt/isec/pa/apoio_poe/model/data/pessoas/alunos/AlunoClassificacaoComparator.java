package pt.isec.pa.apoio_poe.model.data.pessoas.alunos;

import java.util.Comparator;
/**
 * Classe AlunoClassificacaoComparator utilizada para comparar alunos pela sua classificação
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 *
 */
public class AlunoClassificacaoComparator implements Comparator<Aluno> {
    /**
     * Compara dois objetos alunos através ca classificação
     * @param o1 primeiro objeto aluno a comparar
     * @param o2 segundo objeto aluno a comparar
     * @return boolean - retorna 0 se forem iguais, inferior a 0 se o2 for superior a o1 ou superior a 0 se o1 for superior a o2
     */
    @Override
    public int compare(Aluno o1, Aluno o2) {
        return Double.compare(o1.getClassificacao(), o2.getClassificacao());
    }
}
