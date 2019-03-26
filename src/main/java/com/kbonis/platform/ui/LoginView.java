package com.kbonis.platform.ui;

import com.kbonis.platform.config.UrlBuilder;
import com.kbonis.platform.model.User;
import com.kbonis.platform.repository.UserRepository;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;

@Slf4j
@SpringView(name = UrlBuilder.LOGIN_VIEW_PAGE)
public class LoginView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	private final UserRepository userRepository;

	private TextField userNameField;
    private PasswordField passwordField;
    private Button loginButton;

    @Autowired
	public LoginView(UserRepository userRepository) {
		this.userRepository = userRepository;

		userNameField = new TextField("username");
		userNameField.setRequiredIndicatorVisible(true);
		passwordField = new PasswordField("password");
		passwordField.setRequiredIndicatorVisible(true);
		loginButton = new Button("Login");

        FormLayout loginFormLayout = new FormLayout();
		loginFormLayout.addComponent(userNameField);
		loginFormLayout.addComponent(passwordField);
		loginFormLayout.addComponent(loginButton);
		loginFormLayout.setMargin(true);

        Panel loginPanel = new Panel("Login");
		loginPanel.setContent(loginFormLayout);

		// Window view
        HorizontalLayout pageLayout = new HorizontalLayout();
		loginPanel.setSizeFull();
        pageLayout.addComponent(loginPanel);
        pageLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
		setCompositionRoot(pageLayout);

		// Listeners
		loginButtonClickListener();
	}

	/**
	 * Login and navigate to UrlBuilder.MAIN_VIEW_PAGE
	 */
	private void loginButtonClickListener() {
		loginButton.addClickListener(buttonClickEvent -> {
			User loggedUser = userRepository.findByUserNameAndPassword(userNameField.getValue(), passwordField.getValue());
			log.info("Username: [{}] - password [{}]", loggedUser.getUserName(), loggedUser.getPassword());
            getUI().getNavigator().navigateTo(UrlBuilder.MAIN_VIEW_PAGE);
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {}
}
