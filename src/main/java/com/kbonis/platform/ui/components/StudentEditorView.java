package com.kbonis.platform.ui.components;

import com.kbonis.platform.model.Student;
import com.kbonis.platform.webservices.StudentWebService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class StudentEditorView extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;

	private final StudentWebService studentWebService;
	
	public Student student;
	
	public TextField firstName;
	public TextField lastName;
    public TextField phone;
    public TextField eMail;
    public Button save;
    public Button cancel;
    public Button delete;

    private Binder<Student> binder = new Binder<>(Student.class);
	
	@Autowired
	public StudentEditorView(StudentWebService studentWebService) {
		this.studentWebService = studentWebService;

		firstName = new TextField("First name");
		lastName = new TextField("Last name");
        phone = new TextField("Phone");
        eMail = new TextField("E mail");
		save = new Button("Save", FontAwesome.SAVE);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel = new Button("Cancel", FontAwesome.TREE);
        delete = new Button("Delete", FontAwesome.TRASH_O);
        CssLayout actions = new CssLayout(save, cancel, delete);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		addComponents(firstName, lastName, phone, eMail, actions);

		// bind using naming convention
        binder.bindInstanceFields(this);

        setSpacing(true);
        setVisible(false);
	}

	public final void editStudent(Student student) {
		if (student == null) {
			setVisible(false);
			return;
		}

		final boolean persisted = student.getStudentId() != null;
		if (persisted) {
			this.student = studentWebService.findByStudentId(student.getStudentId());
		} else {
			this.student = student;
		}
		cancel.setVisible(persisted);

		// Bind student properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(this.student);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		firstName.selectAll();
	}

	@Override
	public void enter(ViewChangeEvent event) {}
}
