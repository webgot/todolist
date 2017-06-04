package kr.or.connect.todo.service;

import java.util.Collection;

import kr.or.connect.todo.domain.TodoList;
import kr.or.connect.todo.persistence.TodoDao;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
	
	private TodoDao dao;
	public TodoService(TodoDao dao){
		this.dao = dao;
	}
	public Integer count(){
		return dao.countList();
	}
	// 모든 할일 리스트 
	public Collection<TodoList> findAll(){
		return dao.selectAll();
	}
	// 완료된 할일 
	public Collection<TodoList> findCompltedList(){
		return dao.selectCompleted();
	}
	// 아직 완료하지 못한 할일 
	public Collection<TodoList> findActiveList(){
		return dao.selectActive();
	}
	// 할일 추가;
	public TodoList create(TodoList todo){
		Integer id = dao.insert(todo);
		todo.setId(id);
		return todo;
	}
	// 할일을 완료로 변경 
	public boolean update(TodoList todo){
		Integer affected = dao.update(todo);
		return affected == 1;
	}
	public boolean delete(Integer id){
		Integer affected = dao.deleteById(id);
		return affected == 1;
	}	
	public boolean deleteByCompleted(){
		Integer affected = dao.deletedByCompleted();
		return affected == 1;
	}
}
