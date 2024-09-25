package dk.nsp.epps.mock.util.cda.model;

public class Identifier {

    private final String root;
    private final String extension;

    public Identifier(String root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    public String getRoot() {
        return root;
    }

    public String getExtension() {
        return extension;
    }
}
