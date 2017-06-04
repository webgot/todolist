package kr.or.connect.todo.domain;

import java.util.Date;

public class TodoList {

	private Integer id;
	private String todo;
	private boolean completed;
	private Date date;
	
	public TodoList(){
		
	}
	public TodoList(String todo, boolean completed, Date date){
		
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}
	public TodoList(Integer id,String todo, boolean completed, Date date){
		
		this.id = id;
		this.todo = todo;
		this.completed = completed;
		this.date = date;
	}
	public TodoList(String todo){
		this.todo = todo;
		this.completed = false;
		this.date = new Date();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
