package pt.isec.pa.apoio_poe.model.facade;

import pt.isec.pa.apoio_poe.model.cmd.CommandManager;
import pt.isec.pa.apoio_poe.model.cmd.gestaoManualOrientadores.AddOrientador;
import pt.isec.pa.apoio_poe.model.cmd.gestaoManualOrientadores.ChangeOrientador;
import pt.isec.pa.apoio_poe.model.cmd.gestaoManualOrientadores.RemoveOrientador;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;

public class GestaoManualOrientadoresManager {

    ApoioPoEManager apoioPoeManager;
    CommandManager cm;

    public GestaoManualOrientadoresManager(ApoioPoEManager apoioPoeManager) {
        this.apoioPoeManager = apoioPoeManager;
        cm = new CommandManager();
    }

    public boolean addOrientador(Long nAluno, String novoOrientador){
        return cm.invokeCommand(new AddOrientador(apoioPoeManager, nAluno, novoOrientador));
    }

    public boolean changeOrientador(Long nAluno, String novoOrientador){
        return cm.invokeCommand(new ChangeOrientador(apoioPoeManager, nAluno, novoOrientador));
    }

    public boolean removeOrientador(Long nAluno){
        return cm.invokeCommand(new RemoveOrientador(apoioPoeManager, nAluno));
    }

    public boolean hasUndo(){
        return cm.hasUndo();
    }
    public boolean undo(){
        return cm.undo();
    }

    public boolean hasRedo(){
        return cm.hasRedo();
    }
    public boolean redo(){
        return cm.redo();
    }
}
