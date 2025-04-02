package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xca;

import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.xds.EPDocumentMetaData;

public class SubstitutionMetadata implements EPDocumentMetaData.SubstitutionMetaData{

    private final String code;
    private final String displayName;

    public SubstitutionMetadata(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public String getSubstitutionCode() {
        return this.code;
    }

    @Override
    public String getSubstitutionDisplayName() {
        return this.displayName;
    }
}
