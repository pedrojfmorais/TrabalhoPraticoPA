package pt.isec.pa.apoio_poe.model.cmd;

public interface ICommand {
    boolean execute();
    boolean undo();
}
