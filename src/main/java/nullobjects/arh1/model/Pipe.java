package nullobjects.arh1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Pipe class that applies a series of filters to input data of type T.
 * @param <T> The type of data that the pipe operates on.
 */
public class Pipe<T> {
    // List to hold filters to be applied
    private final List<Filter<T>> filters = new ArrayList<Filter<T>>();

    /**
     * @param filter to add.
     */
    public void addFilter(Filter<T> filter){
        filters.add(filter);
    }

    /**
     * Runs all filters on the input data.
     * @param input data to be processed.
     * @return the processed data after applying all filters.
     */
    public T runFilters(T input){
        for (Filter<T> filter: filters) {
            input = filter.execute(input);
        }
        return input;
    }
}
