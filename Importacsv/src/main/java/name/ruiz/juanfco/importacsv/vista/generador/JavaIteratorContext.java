package name.ruiz.juanfco.importacsv.vista.generador;


public class JavaIteratorContext {

	private final IObjectIterator iterator;
	private final int depth;
	private final int child;
    private final String name;
    private final Object value;

	public JavaIteratorContext(IObjectIterator iterator, int depth, int child, String name, Object value) {
		this.iterator = iterator;
		this.depth = depth;
        this.child = child;
        this.name = name;
        this.value = value;
	}
	
	public IObjectIterator getIterator() {
		return iterator;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getChild() {
		return child;
	}
	
	public String getName() {
        return name;
    }
	
	public Object getValue() {
        return value;
    }

}
