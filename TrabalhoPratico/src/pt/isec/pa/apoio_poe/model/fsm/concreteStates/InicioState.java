package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class InicioState extends ApoioPoEAdapter {

    public InicioState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean comecarNovo() {
        context.init(ApoioPoEState.FASE1);
        return true;
    }

    @Override
    public boolean carregarSave(String file) {

        if(file.isBlank())
            return false;

        return context.loadStateInFile(file, context);
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.INICIO;
    }
}
