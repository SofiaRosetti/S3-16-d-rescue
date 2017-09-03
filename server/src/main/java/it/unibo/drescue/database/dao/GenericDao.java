package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;

public interface GenericDao {

    public void insert(ObjectModel objectModel);

    public void delete(ObjectModel objectModel);
}
