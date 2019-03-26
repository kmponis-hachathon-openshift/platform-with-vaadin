package com.kbonis.platform.ui;

import com.kbonis.platform.config.UrlBuilder;
import com.kbonis.platform.model.Student;
import com.kbonis.platform.ui.components.StudentEditorView;
import com.kbonis.platform.webservices.StudentWebService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import static com.vaadin.server.FontAwesome.PLUS;

@Slf4j
@SpringView(name = UrlBuilder.MAIN_VIEW_PAGE)
public class StudentView extends CustomComponent implements  View {

	private static final long serialVersionUID = 1L;
	
	private final StudentWebService studentWebService;
	private final StudentEditorView studentEditorView;

	private Grid<Student> studentGrid;
	private TextField searchTextField;
	private Button newStudentButton;

	@Autowired
	public StudentView(StudentWebService studentWebService, StudentEditorView studentEditorView) {
		this.studentWebService = studentWebService;
		this.studentEditorView = studentEditorView;

        studentGrid = new Grid<>(Student.class);
		studentGrid.setHeight(300, Unit.PIXELS);
		studentGrid.setWidth(1000, Unit.PIXELS);
		studentGrid.setColumns("firstName", "lastName", "phone", "email");

        searchTextField = new TextField();
        searchTextField.setPlaceholder("Filter by last name");
        newStudentButton = new Button("New student", PLUS);

		HorizontalLayout actions = new HorizontalLayout(searchTextField, newStudentButton);
		VerticalLayout studentLayout = new VerticalLayout(actions, studentGrid, studentEditorView);

        // Window view
        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLayout.addComponent(studentLayout);
        pageLayout.setComponentAlignment(studentLayout, Alignment.MIDDLE_CENTER);
        setCompositionRoot(pageLayout);

        // Initialize listing
        listStudents(null);

        // Listeners
        editStudentsGridClickListener();
        newStudentButtonClickListener();
        saveStudentClickListener();
        deleteStudentClickListener();
        cancelStudentEditorClickListener();
        searchTextFieldValueChangeListener();
	}

	/**
	 * Connect selected Student to studentEditor or hide if none is selected
	 */
	private void editStudentsGridClickListener() {
		studentGrid.asSingleSelect().addValueChangeListener(buttonClickEvent ->
                studentEditorView.editStudent(buttonClickEvent.getValue()));
	}

	/**
	 * Instantiate and edit new Student the new button is clicked
	 */
	private void newStudentButtonClickListener() {
		newStudentButton.addClickListener(buttonClickEvent ->
                studentEditorView.editStudent(new Student("", "", "", "")));
	}

	/**
	 * Save student by calling JpaRepository().save()
	 */
	private void saveStudentClickListener() {
		studentEditorView.save.addClickListener(buttonClickEvent -> {
			studentWebService.create(studentEditorView.student);
			studentEditorView.setVisible(false);
            listStudents(null);
        });
	}

	/**
	 * Delete student by calling CrudRepository().delete()
	 */
	private void deleteStudentClickListener() {
		studentEditorView.delete.addClickListener(buttonClickEvent -> {
			studentWebService.delete(studentEditorView.student.getStudentId());
			studentEditorView.setVisible(false);
			listStudents(null);
		});
	}

	/**
	 * Cancel studentEditor by setting visible to false
	 */
	private void cancelStudentEditorClickListener() {
		studentEditorView.cancel.addClickListener(buttonClickEvent -> {
			studentEditorView.editStudent(studentEditorView.student);
			studentEditorView.setVisible(false);
			listStudents(null);
		});
	}

    /**
     * Listen changes made by the filter textbox, refresh data from backend
     */
	private void searchTextFieldValueChangeListener() {
	    searchTextField.addValueChangeListener(valueChangeEvent -> {
            String filterText = valueChangeEvent.getValue();
	        if (filterText.getBytes().length > 3) {
                listStudents(valueChangeEvent.getValue());
            } else if (filterText.getBytes().length == 0) {
                listStudents(null);
            }
        });
    }

    /**
     * Set studentGrid depending on filter text
     * @param filterText String
     */
    private void listStudents(String filterText) {
        if (StringUtils.isEmpty(searchTextField) || StringUtils.isEmpty(filterText)) {
            studentGrid.setItems(studentWebService.findAll());
        } else {
            studentGrid.setItems(studentWebService.findByLastName(filterText));
        }

	}

	@Override
	public void enter(ViewChangeEvent event) {}
}
