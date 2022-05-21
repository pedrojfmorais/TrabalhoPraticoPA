package pt.isec.pa.apoio_poe.model.errorHandling;

public enum ErrorsTypes {

    //Exceptions
    FILE_NOT_FOUND("FileNotFound"), CLONE_NOT_FOUND("CloneNotFound"), IO_EXCEPTION("IOException"),
    CLASS_NOT_FOUND("ClassNotFound"),

    //Erros input utilizador
    DUPLICATED_NUMERO_ALUNO("NumeroAlunoDuplicado"), DUPLICATED_EMAIL("EmailDuplicado"),
    DUPLICATED_ID_PROPOSTA("IDPropostaDuplicado"), DUPLICATED_ID_CANDIDATURA("IDCandidaturaDuplicado"),

    INVALID_NUMERO_ALUNO("NumeroAlunoInvalido"), INVALID_CURSO("CursoInvalido"),
    INVALID_RAMO("RamoInvalido"), INVALID_CLASSIFICACAO("ClassificacaoInvalido"),
    INVALID_DOCENTE("EmailDocenteInvalido"), INVALID_ID_PROPOSTA("IDPropostaInvalido"),
    INVALID_ID_CANDIDATURA("IDCandidaturaInvalido"),

    ALUNO_JA_TEM_CANDIDATURA("AlunoJaEstaAssociadoAUmaCandidatura"),
    ALUNO_NAO_TEM_CANDIDATURA("AlunoAindaNaoEstaAssociadoAUmaCandidatura"),
    ALUNO_JA_TEM_PROPOSTA("AlunoJaTemPropostaAssociada"),

    PROPOSTA_JA_FOI_ATRIBUIDA("PropostaJaAtribuidaAUmAluno"),
    PROPOSTA_AINDA_NAO_ATRIBUIDA("PropostaAindaNaoAtribuidaAUmAluno"),
    PROPOSTA_JA_TEM_ALUNO_ASSOCIADO("PropostaComAlunoJaAssociado"),
    SEM_PROPOSTAS_ESPECIFICADAS("NaoForamIndicadasPropostasParaACandidatura"),
    ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM("PropostaEAlunoComAreaDiferentes"),

    PROBLEMS_READING_ALUNOS_FILE("NemTodosAlunosInseridosDoFicheiro"),
    PROBLEMS_READING_DOCENTES_FILE("NemTodosDocentesInseridosDoFicheiro"),
    PROBLEMS_READING_PROPOSTAS_FILE("NemTodasPropostasInseridasDoFicheiro"),
    PROBLEMS_READING_CANDIDATURAS_FILE("NemTodasCandidaturasInseridasDoFicheiro"),

    NONE("NaN");

    String sigla;

    ErrorsTypes(String sigla){this.sigla = sigla;}
}
