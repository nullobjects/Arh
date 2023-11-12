package nullobjects.arh1.model;

public interface Filter<T> {
    T execute(T input);
}
