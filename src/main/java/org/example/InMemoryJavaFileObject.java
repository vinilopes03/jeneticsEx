package org.example;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {
    private final String code;

    /**
     * Constructor for creating an in-memory Java source file.
     *
     * @param name The name of the class (e.g., "GeneratedClass").
     * @param code The Java source code.
     */
    public InMemoryJavaFileObject(String name, String code) {
        // Create a URI representing the file path (e.g., "string:///GeneratedClass.java")
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    /**
     * Returns the Java source code as a CharSequence.
     *
     * @param ignoreEncodingErrors Ignored, as encoding is not relevant for in-memory objects.
     * @return The Java source code.
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}
