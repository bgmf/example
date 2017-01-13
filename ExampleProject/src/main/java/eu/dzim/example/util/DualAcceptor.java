package eu.dzim.example.util;

public interface DualAcceptor<T, U> {
	void accept(T t, U u);
}
