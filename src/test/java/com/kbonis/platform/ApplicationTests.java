//package com.kbonis.platform;
//
//import com.kbonis.platform.repository.UserRepository;
//import com.kbonis.platform.webservices.StudentWebService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class ApplicationTests {
//
//	@Autowired
//	private StudentWebService studentWebService;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Test
//	public void shouldStudentsFillOutComponentsWithDataWhenAppStart(){
//		assertEquals(5, this.studentWebService.findAll().size());
//	}
//
//	@Test
//	public void shouldUsersFillOutComponentsWithDataWhenAppStart(){
//		assertEquals(2, this.userRepository.count());
//	}
//
//	@Test
//	public void shouldFindTwoBaurerStudents(){
//		assertEquals(5, this.studentWebService.findByLastName("Baurer"));
//	}
//
//}
