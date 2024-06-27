package eu.europa.ec.sante.ehdsi.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class LivingSubjectId {

    private String root;

    private String extension;

    public LivingSubjectId() {
        //  Empty Default Constructor
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("root", root)
                .append("extension", extension)
                .toString();
    }
}
