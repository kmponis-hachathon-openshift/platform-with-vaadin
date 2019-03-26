package com.kbonis.platform.ui;

import com.kbonis.platform.config.UrlBuilder;
import com.kbonis.platform.repository.UserRepository;
import com.kbonis.platform.ui.components.StudentEditorView;
import com.kbonis.platform.webservices.StudentWebService;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path=UrlBuilder.INITIAL_UI_PAGE)
@SpringViewDisplay
public class InitialUI extends UI {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final StudentWebService studentWebService;

	@Autowired
	public InitialUI(UserRepository userRepository, StudentWebService studentWebService) {
		this.userRepository = userRepository;
		this.studentWebService = studentWebService;
	}

	@Override
	protected void init(VaadinRequest request) {
		WelcomeView welcomeView = new WelcomeView();
		welcomeView.setSizeFull();

		LoginView loginView = new LoginView(userRepository);

		StudentEditorView studentEditorView = new StudentEditorView(studentWebService);
		StudentView studentView = new StudentView(studentWebService, studentEditorView);

		// UI navigator
		Navigator navigator = new Navigator(this, this);
		navigator.addView(UrlBuilder.WELCOME_VIEW_PAGE, welcomeView);
		navigator.addView(UrlBuilder.LOGIN_VIEW_PAGE, loginView);
		navigator.addView(UrlBuilder.MAIN_VIEW_PAGE, studentView);
		navigator.navigateTo(UrlBuilder.WELCOME_VIEW_PAGE);
	}
}
