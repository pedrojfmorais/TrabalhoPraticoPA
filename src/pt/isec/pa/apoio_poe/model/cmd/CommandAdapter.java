package pt.isec.pa.apoio_poe.model.cmd;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;

public abstract class CommandAdapter implements ICommand{

    protected ApoioPoEManager apoioPoeManager;

    public CommandAdapter(ApoioPoEManager apoioPoeManager) {
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
