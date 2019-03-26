package com.kbonis.platform.ui;

import com.kbonis.platform.config.UrlBuilder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = UrlBuilder.WELCOME_VIEW_PAGE)
public class WelcomeView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

    private Button viewStudentsButton;

    @Autowired
	public WelcomeView() {
        Label welcomeMessage = new Label("Welcome to my page, to view students please click bellow");
        viewStudentsButton = new Button("View Students");

        VerticalLayout welcomeLayout = new VerticalLayout(welcomeMessage, viewStudentsButton);

        // Window view
        HorizontalLayout pageLayout = new HorizontalLayout();
        pageLayout.addComponent(welcomeLayout);
        pageLayout.setComponentAlignment(welcomeLayout, Alignment.MIDDLE_CENTER);
        setCompositionRoot(pageLayout);

		// Listeners
		viewStudentsButtonClickListener();
	}

    /**
     * Navigate to UrlBuilder.LOGIN_VIEW_PAGE
     */
	private void  viewStudentsButtonClickListener() {
		viewStudentsButton.addClickListener(buttonClickEvent -> getUI().getNavigator().navigateTo(UrlBuilder.LOGIN_VIEW_PAGE));
	}

	@Override
	public void enter(ViewChangeEvent event) {}
}
