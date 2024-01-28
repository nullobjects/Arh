package nullobjects.arh1.model.filters;

import nullobjects.arh1.model.Filter;

/**
 * A filter implementation to convert input strings to uppercase.
 */
public class UppercaseFilter implements Filter<String> {

    /**
     * Executes the filter by converting the input string to uppercase.
     * @param input The input string to be processed.
     * @return The input string converted to uppercase.
     */
    @Override
    public String execute(String input) {
        return input.toUpperCase();
    }
}
