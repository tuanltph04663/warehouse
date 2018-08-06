package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import util.DBConn;

public abstract class DAO<Entity> {

	static final Connection CONN = DBConn.getconnection();

	abstract public List<Entity> getAll() throws SQLException;

	abstract public Entity findByName(List<Entity> entities,String name);

	abstract public Entity findById(List<Entity> entities,int id);

	abstract public List<Entity> findBy(Serializable by);

}
