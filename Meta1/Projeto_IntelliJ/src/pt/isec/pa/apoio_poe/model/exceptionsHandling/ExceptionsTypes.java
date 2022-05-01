package pt.isec.pa.apoio_poe.model.exceptionsHandling;

public enum ExceptionsTypes {
    FileNotFound("FileNotFound"), CloneNotFound("CloneNotFound"), IOException("IOException"),
    ClassNotFound("ClassNotFound"), NONE("NaN");

    String sigla;

    ExceptionsTypes(String sigla){this.sigla = sigla;}
}
