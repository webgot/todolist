package kr.or.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import kr.or.connect.todo.domain.TodoList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoDaoTest {

	@Autowired
	private TodoDao dao;
	
	@Test
	public void shouldCount() {
		int count = dao.countList();
	}
	
	@Test
	public void shouldInsertAndSelect() {
		// given
		Date now = new Date();
		TodoList todo = new TodoList("boost-camp 6.6(화) 과제 제출", false, now);

		// when
		Integer id = dao.insert(todo);
		Integer count = dao.countList();
		
		// then
		List<TodoList> selectedTodo = dao.selectActive();
		assertThat(selectedTodo.size(), is(count));
	}
	
	@Test
	public void shouldDelete() {
		// given
		Date now = new Date();
		TodoList todo = new TodoList("boost-camp 6.6(화) 과제 제출", false, now);
		
		Integer id = dao.insert(todo);

		// when
		int affected = dao.deleteById(id);

		// Then
		assertThat(affected, is(1));
	}
	@Test
	public void shouldUpdate() {
		// Given
		Date now = new Date();
		TodoList todo = new TodoList("boost-camp 6.6(화)제출", false, now);
		Integer id = dao.insert(todo);

		// When
		todo.setCompleted(true);
		todo.setId(id);
		int affected = dao.update(todo);

		// Then
		assertThat(affected, is(1));
	}
	@Test
	public void shouldSelectAll() {
		List<TodoList> list = dao.selectAll();
		assertThat(list, is(notNullValue()));
	}
}