package nextmethod.base;

public interface Disposable extends AutoCloseable {

	@Override
	void close();

}
