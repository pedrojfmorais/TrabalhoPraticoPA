package pt.isec.pa.apoio_poe.model.cmd;

import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;

public abstract class CommandAdapter implements ICommand{

    protected ApoioPoeManager apoioPoeManager;

    public CommandAdapter(ApoioPoeManager apoioPoeManager) {
        this.apoioPoeManager = apoioPoeManager;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean undo() {
        return false;
    }
}
