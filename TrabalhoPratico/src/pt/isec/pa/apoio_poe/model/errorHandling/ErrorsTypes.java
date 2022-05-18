package pt.isec.pa.apoio_poe.model.errorHandling;

public enum ErrorsTypes {

    //Exceptions
    FILE_NOT_FOUND("FileNotFound"), CLONE_NOT_FOUND("CloneNotFound"), IO_EXCEPTION("IOException"), CLASS_NOT_FOUND("ClassNotFound"),

    //Erros input utilizador
    DUPLICATED_NUMERO_ALUNO("ASD"), DUPLICATED_EMAIL(""), DUPLICATED_ID_PROPOSTA(""), DUPLICATED_ID_CANDIDATURA(""),
    INVALID_CLASSIFICACAO(""), INVALID_NUMERO_ALUNO(""), INVALID_CURSO(""), INVALID_RAMO(""),
    ALUNO_JA_TEM_PROPOSTA(""), INVALID_DOCENTE(""), PROPOSTA_JA_TEM_ALUNO_ASSOCIADO(""),
    PROPOSTA_JA_FOI_ATRIBUIDA(""), INVALID_ID_PROPOSTA(""), ALUNO_JA_TEM_CANDIDATURA(""),
    PROPOSTA_AINDA_NAO_ATRIBUIDA(""), INVALID_ID_CANDIDATURA(""), ALUNO_NAO_TEM_CANDIDATURA(""),



    NONE("NaN");

    String sigla;

    ErrorsTypes(String sigla){this.sigla = sigla;}
}
