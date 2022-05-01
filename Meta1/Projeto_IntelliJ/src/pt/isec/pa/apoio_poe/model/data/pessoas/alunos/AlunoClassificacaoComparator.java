package pt.isec.pa.apoio_poe.model.data.pessoas.alunos;

import java.util.Comparator;

public class AlunoClassificacaoComparator implements Comparator<Aluno> {
    @Override
    public int compare(Aluno o1, Aluno o2) {
        return Double.compare(o1.getClassificacao(), o2.getClassificacao());
    }
}
