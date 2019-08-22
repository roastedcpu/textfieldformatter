package org.vaadin.textfieldformatter;

import java.lang.ref.WeakReference;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;

@Tag("jh-textfield-formatter")
@JsModule("./jh-textfield-formatter/jh-textfield-formatter.js")
public abstract class CleaveExtension extends Component {

	private WeakReference<Component> extended;
	private CleaveConfiguration configuration;

	protected void extend(Component component) {
		if (!component.getUI().isPresent()) {
			component.addAttachListener(event -> {
				extend(component, event.getUI());
			});

		} else {
			extend(component, component.getUI().get());
		}
	}

	private void extend(Component component, UI ui) {
		extended = new WeakReference<Component>(component);
		component.getElement().appendChild(getElement());
		getElement().setPropertyJson("conf", getConfiguration().toJson());
	}

	public void remove() {
		getElement().removeFromParent();
		extended.clear();
	}

	protected CleaveConfiguration getConfiguration() {
		if (configuration == null) {
			configuration = new CleaveConfiguration();
		}
		return configuration;
	}
}
