package nullobjects.arh1.model.filters;

import nullobjects.arh1.model.Filter;

public class UppercaseFilter implements Filter<String> {

    @Override
    public String execute(String input) {
        return input.toUpperCase();
    }
}
