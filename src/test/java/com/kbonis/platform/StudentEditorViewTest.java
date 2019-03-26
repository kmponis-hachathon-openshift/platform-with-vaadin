package com.kbonis.platform;

import com.kbonis.platform.model.Student;
import com.kbonis.platform.ui.components.StudentEditorView;
import com.kbonis.platform.webservices.StudentWebService;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.argThat;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class StudentEditorViewTest {

	private static final String FIRST_NAME = "fiskra";
	private static final String LAST_NAME = "lastFiskra";
	private static final String PHONE = "99";
	private static final String EMAIL = "fiskra@";

	@Mock
	StudentWebService studentWebService;
	@InjectMocks
    StudentEditorView editor;

	@Test
	public void shouldStoreStudentInRepoWhenEditorSaveClicked() {
		this.editor.firstName.setValue(FIRST_NAME);
		this.editor.lastName.setValue(LAST_NAME);
		studentDataWasFilled();

		this.editor.save.click();

		then(this.studentWebService).should().create(argThat(studentMatchesEditorFields()));
	}

	private void studentDataWasFilled() {
		this.editor.editStudent(new Student(FIRST_NAME, LAST_NAME, PHONE, EMAIL));
	}

	private TypeSafeMatcher<Student> studentMatchesEditorFields() {
		return new TypeSafeMatcher<Student>() {
			@Override
			public void describeTo(Description description) {
			}

			@Override
			protected boolean matchesSafely(Student item) {
				return FIRST_NAME.equals(item.getFirstName()) && LAST_NAME.equals(item.getLastName());
			}
		};
	}

}
