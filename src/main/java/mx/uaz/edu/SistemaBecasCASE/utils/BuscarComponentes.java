package mx.uaz.edu.SistemaBecasCASE.utils;

import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

public class BuscarComponentes{

    public static Component findComponentById(HasComponents root, String id) {
        for (Component child : root) {
            if (id.equals(child.getId())) {
                return child;
            } else if (child instanceof HasComponents) {
                Component result = findComponentById((HasComponents) child, id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}