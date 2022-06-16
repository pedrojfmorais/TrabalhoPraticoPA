package pt.isec.pa.apoio_poe.model.errorHandling;

/**
 * Classe Enumerável com tipos de exceções e de erros de input do utilizador
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public enum ErrorType {

    /**
     * Ficheiro não encontrado
     */
    FILE_NOT_FOUND,
    /**
     * Clone não suportado
     */
    CLONE_NOT_FOUND,
    /**
     * Exceção de Input/Output
     */
    IO_EXCEPTION,
    /**
     * Class não encontrada
     */
    CLASS_NOT_FOUND,

    /**
     * Número de aluno duplicado
     */
    DUPLICATED_NUMERO_ALUNO,
    /**
     * Email duplicado
     */
    DUPLICATED_EMAIL,
    /**
     * Id proposta duplicado
     */
    DUPLICATED_ID_PROPOSTA,
    /**
     * Id candidatura duplicado
     */
    DUPLICATED_ID_CANDIDATURA,

    /**
     * Numero de aluno inválido
     */
    INVALID_NUMERO_ALUNO,
    /**
     * Curso inválido
     */
    INVALID_CURSO,
    /**
     * Ramo inválido
     */
    INVALID_RAMO,
    /**
     * Classificação inválida
     */
    INVALID_CLASSIFICACAO,
    /**
     * Docente inválido
     */
    INVALID_DOCENTE,
    /**
     * Id proposta inválido
     */
    INVALID_ID_PROPOSTA,
    /**
     * Id candidatura inválido
     */
    INVALID_ID_CANDIDATURA,

    /**
     * Este aluno já tem candidatura
     */
    ALUNO_JA_TEM_CANDIDATURA,
    /**
     * Aluno não tem candidatura
     */
    ALUNO_NAO_TEM_CANDIDATURA,
    /**
     * Aluno já tem proposta atribuída
     */
    ALUNO_JA_TEM_PROPOSTA,

    /**
     * Esta proposta já foi atribuído
     */
    PROPOSTA_JA_FOI_ATRIBUIDA,
    /**
     * Proposta ainda não foi atribuída
     */
    PROPOSTA_AINDA_NAO_ATRIBUIDA,
    /**
     * Esta proposta já tem um aluno associado
     */
    PROPOSTA_JA_TEM_ALUNO_ASSOCIADO,
    /**
     * Não especificou proposta para a candidatura
     */
    SEM_PROPOSTAS_ESPECIFICADAS,
    /**
     * O aluno e a proposta não são para a mesma área
     */
    ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM,

    /**
     * Erro a ler alunos do ficheiro
     */
    PROBLEMS_READING_ALUNOS_FILE,
    /**
     * Erro a ler docentes do ficheiro
     */
    PROBLEMS_READING_DOCENTES_FILE,
    /**
     * Erro a ler propostas do ficheiro
     */
    PROBLEMS_READING_PROPOSTAS_FILE,
    /**
     * Erro a ler candidaturas do ficheiro
     */
    PROBLEMS_READING_CANDIDATURAS_FILE,

    /**
     * Sem erro
     */
    NONE
}
