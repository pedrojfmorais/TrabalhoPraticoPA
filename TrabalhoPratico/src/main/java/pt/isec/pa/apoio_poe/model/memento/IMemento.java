package pt.isec.pa.apoio_poe.model.memento;

import pt.isec.pa.apoio_poe.model.data.ApoioPoE;

public interface IMemento {
    default ApoioPoE getSnapshot(){return null;}
}
