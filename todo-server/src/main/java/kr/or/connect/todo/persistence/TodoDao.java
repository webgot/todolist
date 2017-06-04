package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.or.connect.todo.domain.TodoList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository

public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<TodoList> rowMapper = BeanPropertyRowMapper.newInstance(TodoList.class);

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	public int countList() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(TodoSqls.COUNT_LIST, params, Integer.class);
	}
	public Integer insert(TodoList todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	public List<TodoList> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ALL, params, rowMapper);
	}
	
	public List<TodoList> selectCompleted(){
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_BY_COMPLETED, params, rowMapper);
	}
	public List<TodoList> selectActive(){
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_BY_ACTIVE, params, rowMapper);
	}
	public Integer update(TodoList todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(TodoSqls.UPDATE, params);
	}
	public Integer deleteById(Integer id){
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(TodoSqls.DELETE_BY_ID, params);
	}
	public Integer deletedByCompleted(){
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.update(TodoSqls.DELETED_BY_COMPLETED, params);
	}
	
	
}
