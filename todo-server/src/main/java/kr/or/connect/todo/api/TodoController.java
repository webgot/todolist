package kr.or.connect.todo.api;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;

import kr.or.connect.todo.domain.TodoList;
import kr.or.connect.todo.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")

public class TodoController {

	private final TodoService service;
	
	@Autowired
	public TodoController(TodoService service){
		this.service = service;
	}

	// todolist 전체 보여주지(할일 + 완료한일)
	@GetMapping
	Collection<TodoList> readList(){
		return service.findAll();
	}
	
	//완료된 할일 보여주(완료한일)
	@GetMapping
	@RequestMapping("/completed")
	Collection<TodoList> readCompleted(){
		return service.findCompltedList();
	}
	
	//앞으로 해야할 일 보여주기(할일)
	@GetMapping
	@RequestMapping("/active")
	Collection<TodoList> readActive(){
		return service.findActiveList();
	}
	
	//아직 완료 하지 못한 할일 갯수정보 반환 
	@GetMapping
	@RequestMapping("/count")
	Integer count(){
		return service.count();
	}
	
	//할일 추가
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	TodoList create(@RequestBody String todo) {
		Date now = new Date();
		TodoList newTodo = new TodoList(todo, false, now );
		TodoList retTodo = service.create(newTodo);
		return newTodo;	
	}
	
	//할일 완료로 변경
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody TodoList todo) {
		todo.setId(id);
		service.update(todo);
	}

	//완료한 할일 모두 삭제
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete() {
		service.deleteByCompleted();
	}
	
	//선택한 할일 삭제
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		service.delete(id);
	}
	
	

}
