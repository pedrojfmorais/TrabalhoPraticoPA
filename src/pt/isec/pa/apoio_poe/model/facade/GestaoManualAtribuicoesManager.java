package pt.isec.pa.apoio_poe.model.facade;

import pt.isec.pa.apoio_poe.model.cmd.CommandManager;
import pt.isec.pa.apoio_poe.model.cmd.gestaoManualAtribuicoes.AddAtribuicaoAlunoProposta;
import pt.isec.pa.apoio_poe.model.cmd.gestaoManualAtribuicoes.RemoveAtribuicaoAlunoProposta;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;

public class GestaoManualAtribuicoesManager {

    ApoioPoeManager apoioPoeManager;
    CommandManager cm;

    public GestaoManualAtribuicoesManager(ApoioPoeManager apoioPoeManager) {
        this.apoioPoeManager = apoioPoeManager;
        cm = new CommandManager();
    }

    public boolean addAtribuicaoAlunoProposta(long nAluno, String idProposta){
        return cm.invokeCommand(new AddAtribuicaoAlunoProposta(apoioPoeManager, nAluno, idProposta));
    }

    public boolean removeAtribuicaoAlunoProposta(String idProposta){
        return cm.invokeCommand(new RemoveAtribuicaoAlunoProposta(apoioPoeManager, idProposta));
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