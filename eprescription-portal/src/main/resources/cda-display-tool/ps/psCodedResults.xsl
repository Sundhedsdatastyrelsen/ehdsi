<?xml version="1.0"  ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3">

    <xsl:variable name="codedResultsSectionCode"
                  select="'30954-2'"/>

    <!-- eHDSI Coded Results -->
    <xsl:template name="codedResults" match="/">
        <xsl:apply-templates
                select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$codedResultsSectionCode]]"/>
    </xsl:template>

    <xsl:template
            match="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$codedResultsSectionCode]]">
        <div class="wrap-collapsible">
            <input id="collapsible-coded-results-section" class="toggle" type="checkbox" checked="true"/>
            <label for="collapsible-coded-results-section" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$codedResultsSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-coded-results-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-coded-results-original" class="lbl-toggle">
                            <xsl:value-of select="$originalNarrativeTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <xsl:apply-templates select="n1:text"/>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="wrap-collapsible">
                        <input id="collapsible-coded-results-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-coded-results-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <!-- Loop through all organizers -->
                                <xsl:apply-templates select="n1:entry/n1:organizer" mode="codedResultsOrganizer"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <br/>
    </xsl:template>

    <xsl:template match="n1:organizer" mode="codedResultsOrganizer">
        <h2>
            <xsl:call-template name="show-eHDSIObservationCategory">
                <xsl:with-param name="node" select="n1:code"/>
            </xsl:call-template>
        </h2>

        <!-- Organizer metadata -->
        <div class="organizer-info">
            <xsl:if test="n1:statusCode/@code">
                <p>
                    <strong>
                        <!-- Status -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'176'"/>
                        </xsl:call-template>
                        :
                    </strong>
                    <xsl:value-of select="n1:statusCode/@code"/>
                </p>
            </xsl:if>

            <!-- Effective Time -->
            <xsl:if test="n1:effectiveTime/@value">
                <p>
                    <strong>
                        <!-- Date -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'17'"/>
                        </xsl:call-template>
                        :
                    </strong>
                    <xsl:call-template name="formatDateTime">
                        <xsl:with-param name="date" select="n1:effectiveTime/@value"/>
                    </xsl:call-template>
                </p>
            </xsl:if>

            <!-- Performer -->
            <xsl:if test="n1:performer">
                <p>
                    <strong>
                        <!-- Performer -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'161'"/>
                        </xsl:call-template>
                        :
                    </strong>
                    <xsl:call-template name="show-performer">
                        <xsl:with-param name="node" select="n1:performer"/>
                    </xsl:call-template>
                </p>
            </xsl:if>

            <!-- Author -->
            <xsl:if test="n1:author">
                <p>
                    <strong>
                        <!-- Author (HCP) TODO [KJW] is the (HCP) part correct?-->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'7'"/>
                        </xsl:call-template>
                        :
                    </strong>
                    <xsl:call-template name="show-author">
                        <xsl:with-param name="node" select="n1:author"/>
                    </xsl:call-template>
                </p>
            </xsl:if>
        </div>

        <!-- Process all components -->
        <xsl:apply-templates select="n1:component" mode="organizerComponent"/>
    </xsl:template>

    <xsl:template match="n1:component" mode="organizerComponent">

        <xsl:choose>
            <!-- Laboratory Result Observations -->
            <xsl:when test="n1:observation[n1:templateId[@root='2.16.840.1.113883.3.1937.777.11.10.154']]">
                <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                <h3>Laboratory Result Observations</h3>
                <xsl:call-template name="createResultTable">
                    <xsl:with-param name="observations" select="n1:observation"/>
                    <xsl:with-param name="tableType" select="'lab'"/>
                </xsl:call-template>
            </xsl:when>

            <!-- Result Observations -->
            <xsl:when test="n1:observation[n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.43']]">
                <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                <h3>Result Observations</h3>
                <xsl:call-template name="createResultTable">
                    <xsl:with-param name="observations" select="n1:observation"/>
                    <xsl:with-param name="tableType" select="'observation'"/>
                </xsl:call-template>
            </xsl:when>

            <!-- Specimen Collections -->
            <xsl:when test="n1:procedure[n1:templateId[@root='2.16.840.1.113883.3.1937.777.11.10.153']]">
                <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                <h3>Specimen Collections</h3>
                <xsl:call-template name="createSpecimenTable">
                    <xsl:with-param name="procedures" select="n1:procedure"/>
                </xsl:call-template>
            </xsl:when>

            <!-- Comment Activities -->
            <xsl:when test="n1:act[n1:templateId[@root='2.16.840.1.113883.3.1937.777.11.10.155']]">
                <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                <h3>Comment Activities</h3>
                <xsl:call-template name="createCommentTable">
                    <xsl:with-param name="acts" select="n1:act"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="createResultTable">
        <xsl:param name="observations"/>
        <xsl:param name="tableType"/>

        <table class="translation_table">
            <tbody>
                <tr>
                    <th>
                        <!-- Diagnostic Date -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'118'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!-- Result type Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'159'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!-- Result value Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'160'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                        Reference range
                    </th>
                    <th>
                        <!-- Performer Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'161'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!-- Reporter Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'162'"/>
                        </xsl:call-template>
                    </th>
                </tr>
                <xsl:choose>
                    <xsl:when test="$observations/@nullFlavor">
                        <tr>
                            <td colspan="5">
                                <xsl:call-template name="show-eHDSINullFlavor">
                                    <xsl:with-param name="code" select="$observations/@nullFlavor"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each select="$observations">
                            <tr>
                                <td>
                                    <!-- Date -->
                                    <xsl:choose>
                                        <xsl:when test="n1:effectiveTime/@value">
                                            <xsl:call-template name="formatDateTime">
                                                <xsl:with-param name="date" select="n1:effectiveTime/@value"/>
                                            </xsl:call-template>
                                        </xsl:when>
                                        <xsl:when test="n1:effectiveTime/n1:low/@value">
                                            <xsl:call-template name="formatDateTime">
                                                <xsl:with-param name="date" select="n1:effectiveTime/n1:low/@value"/>
                                            </xsl:call-template>
                                        </xsl:when>
                                    </xsl:choose>
                                </td>
                                <td>
                                    <xsl:variable name="labCodeResult">
                                        <xsl:call-template name="show-eHDSILabCode">
                                            <xsl:with-param name="node" select="n1:code"/>
                                        </xsl:call-template>
                                    </xsl:variable>

                                    <xsl:choose>
                                        <xsl:when test="normalize-space($labCodeResult) != ''">
                                            <xsl:copy-of select="$labCodeResult"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="n1:code/@displayName"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td>
                                    <xsl:call-template name="show-value-by-type">
                                        <xsl:with-param name="node" select="n1:value"/>
                                    </xsl:call-template>
                                    <!-- Result Value -->
                                    <xsl:if test="n1:interpretationCode">
                                        <xsl:call-template name="interpretation">
                                            <xsl:with-param name="observationInterpretation"
                                                            select="n1:interpretationCode"/>
                                        </xsl:call-template>
                                    </xsl:if>
                                </td>
                                <td>
                                    <!-- Reference Range-->
                                    <xsl:if test="n1:referenceRange/n1:observationRange">
                                        <xsl:if test="n1:referenceRange/n1:observationRange/n1:code">
                                            <xsl:call-template name="show-eHDSIReferenceRangeAppliesTo">
                                                <xsl:with-param name="node" select="n1:referenceRange/n1:observationRange/n1:code"/>
                                            </xsl:call-template>
                                            <xsl:call-template name="show-eHDSIReferenceRangeMeaning">
                                                <xsl:with-param name="node" select="n1:referenceRange/n1:observationRange/n1:code"/>
                                            </xsl:call-template>
                                            <xsl:text>: </xsl:text>
                                        </xsl:if>
                                        <xsl:value-of select="n1:referenceRange/n1:observationRange/n1:value/text()"/>
                                    </xsl:if>
                                    <xsl:if test="n1:referenceRange/n1:observationRange/n1:interpretationCode">
                                        <xsl:text> </xsl:text>
                                        <xsl:call-template name="interpretation">
                                            <xsl:with-param name="observationInterpretation"
                                                            select="n1:referenceRange/n1:observationRange/n1:interpretationCode"/>
                                        </xsl:call-template>
                                    </xsl:if>

                                </td>
                                <td>
                                    <!-- Performer -->
                                    <xsl:call-template name="show-performer">
                                        <xsl:with-param name="node" select="n1:performer"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!-- Author -->
                                    <xsl:call-template name="show-author">
                                        <xsl:with-param name="node" select="n1:author"/>
                                    </xsl:call-template>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template name="show-value-by-type">
        <xsl:param name="node"/>
        <xsl:choose>
            <xsl:when test="$node/@*[local-name() = 'type'] = 'CS'">
                <xsl:call-template name="show-eHDSIBloodGroup">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
                <xsl:call-template name="show-eHDSIPresenceAbsence">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
                <xsl:call-template name="show-eHDSIMicroorganism">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$node/$node/@*[local-name() = 'type'] = 'PQ'">
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$node/@*[local-name() = 'type'] = 'IVL_PQ'">
                <xsl:call-template name="show-IVL_PQ">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$node/@*[local-name() = 'type'] = 'ST'">
                <xsl:call-template name="show-ST">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$node/@*[local-name() = 'type'] = 'TS'">
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$node/@*[local-name() = 'type'] = 'RTO_QTY_QTY'">
                <xsl:call-template name="show-RTO_QTY_QTY">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:when>
            <!-- Fall back to display name if it is available-->
            <xsl:when test="$node/@displayName">
                <xsl:value-of select="$node/@displayName"/>
            </xsl:when>
            <!-- Catch all -->
            <xsl:otherwise>
                <xsl:value-of select="$node/text()"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="interpretation">
        <xsl:param name="observationInterpretation"/>
        <xsl:text> (</xsl:text>
        <xsl:call-template name="show-eHDSIObservationInterpretation">
            <xsl:with-param name="node" select="$observationInterpretation"/>
        </xsl:call-template>
        <xsl:text>)</xsl:text>
    </xsl:template>

    <xsl:template name="createSpecimenTable">
        <xsl:param name="procedures"/>

        <table class="translation_table">
            <tbody>
                <tr>
                    <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                    <th>Collection Date</th>
                    <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                    <th>Specimen Type</th>
                    <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                    <th>Collection Method</th>
                    <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                    <th>Collection Site</th>
                </tr>
                <xsl:for-each select="$procedures">
                    <tr>
                        <td>
                            <xsl:choose>
                                <xsl:when test="n1:effectiveTime/n1:low/@value">
                                    <xsl:call-template name="formatDateTime">
                                        <xsl:with-param name="date" select="n1:effectiveTime/n1:low/@value"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>-</xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <td>
                            <xsl:call-template name="show-eHDSILabSpecimenType">
                                <xsl:with-param name="node" select="n1:participant/n1:participantRole/n1:playingEntity/n1:code"/>
                            </xsl:call-template>
                        </td>
                        <td>
                            <xsl:call-template name="show-eHDSILabSpecimenType">
                                <xsl:with-param name="node" select="n1:methodCode"/>
                            </xsl:call-template>
                        </td>
                        <td>
                            <xsl:call-template name="show-eHDSIBodySite">
                                <xsl:with-param name="node" select="n1:targetSiteCode"/>
                            </xsl:call-template>
                            <xsl:call-template name="show-eHDSIMorphologicAbnormality">
                                <xsl:with-param name="node" select="n1:targetSiteCode"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template name="createCommentTable">
        <xsl:param name="acts"/>

        <table class="translation_table">
            <tbody>
                <tr>
                    <th>
                        <!-- Date -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'17'"/>
                        </xsl:call-template>
                    </th>
                    <!--  FIXME [KJW] hardcoded for now but make this translatable-->
                    <th>Comment</th>
                    <th>
                        <!-- Author (HCP) TODO [KJW] is the (HCP) part correct?-->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'7'"/>
                        </xsl:call-template>
                    </th>
                </tr>
                <xsl:for-each select="$acts">
                    <tr>
                        <td>
                            <xsl:choose>
                                <xsl:when test="n1:author/n1:time/@value">
                                    <xsl:call-template name="formatDateTime">
                                        <xsl:with-param name="date" select="n1:author/n1:time/@value"/>
                                    </xsl:call-template>
                                </xsl:when>
                            </xsl:choose>
                        </td>
                        <td>
                            <xsl:choose>
                                <xsl:when test="n1:text/n1:reference/@value">
                                    <xsl:variable name="refId"
                                                  select="substring-after(n1:text/n1:reference/@value, '#')"/>
                                    <!-- Look for the referenced text in the narrative -->
                                    <xsl:value-of select="//n1:text//*[@ID=$refId]"/>
                                </xsl:when>
                                <xsl:when test="n1:text">
                                    <xsl:value-of select="n1:text"/>
                                </xsl:when>
                            </xsl:choose>
                        </td>
                        <td>
                            <xsl:choose>
                                <xsl:when test="n1:author">
                                    <xsl:call-template name="show-author">
                                        <xsl:with-param name="node" select="n1:author"/>
                                    </xsl:call-template>
                                </xsl:when>
                            </xsl:choose>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
</xsl:stylesheet>
