package mx.gapsi.commons.interfaces;

import mx.gapsi.commons.model.Base;

public interface crud {

    public Base findAll(Base base);
    public Base insert(Base base);
    public Base update(Base base);
    public Base delete(Base base);

}
