package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String COUNT_LIST =
			"SELECT COUNT(*) FROM todo WHERE completed=false;";
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	static final String DELETED_BY_COMPLETED=
			"DELETE FROM todo WHERE completed=true;";
	//할일 전체 보여주기 
	static final String SELECT_ALL = 
			"SELECT * FROM todo ORDER BY date DESC;";
	
	//완료한 할일 보여주기 
	static final String SELECT_BY_COMPLETED = 
			"SELECT * FROM todo WHERE completed=true ORDER BY date DESC";
	
	//완료하지 못한일 보여주기 
	static final String SELECT_BY_ACTIVE = 
			"SELECT * FROM todo WHERE completed=false ORDER BY date DESC";
	
	static final String UPDATE =
			"UPDATE todo SET\n"
			+ "todo = :todo,"
			+ "completed = :completed,"
			+ "date = :date\n"
			+ "WHERE id = :id";
	
}
