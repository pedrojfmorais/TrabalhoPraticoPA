package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

public class InicioState extends ApoioPoeAdapter{

    public InicioState(ApoioPoeContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean comecarNovo() {
        context.init(ApoioPoeState.FASE1);
        return true;
    }

    @Override
    public boolean carregarSave(String file) {

        if(file.isBlank())
            return false;

        return data.loadStateInFile(file, context);
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.INICIO;
    }
}
