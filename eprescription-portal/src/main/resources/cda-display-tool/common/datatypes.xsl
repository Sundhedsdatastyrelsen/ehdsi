<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                version="2.0">

    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <xsl:template name="show-CS">
        <xsl:param name="node"/>
        <xsl:value-of select="$node/@displayName"/>
    </xsl:template>

    <xsl:template name="show-RTO_QTY_QTY">
        <xsl:param name="node"/>
        <xsl:value-of select="$node/n1:numerator/@value"/>
        <xsl:text>/</xsl:text>
        <xsl:value-of select="$node/n1:denominator/@value"/>
    </xsl:template>

    <xsl:template name="show-ST">
        <xsl:param name="node"/>
        <xsl:value-of select="$node/text()"/>
    </xsl:template>

    <!-- IVL_PQ datatype -->
    <xsl:template name="show-IVL_PQ">
        <xsl:param name="node"/>
        <xsl:variable name="low" select="$node/n1:low"/>
        <xsl:variable name="high" select="$node/n1:high"/>
        <xsl:variable name="width" select="$node/n1:width"/>
        <xsl:variable name="center" select="$node/n1:center"/>
        <xsl:variable name="nullFlavor" select="$node/@nullFlavor"/>
        <xsl:choose>
            <xsl:when test="$width">
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$width"/>
                </xsl:call-template>&#160;
            </xsl:when>
            <xsl:when test="$low and $high">
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$low"/>
                </xsl:call-template>
                -
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$high"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$low">
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$low"/>
                </xsl:call-template>&#160;
            </xsl:when>
            <xsl:when test="$high">
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$high"/>
                </xsl:call-template>&#160;
            </xsl:when>
            <xsl:when test="$nullFlavor">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$nullFlavor"/>
                </xsl:call-template>&#160;
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>&#160;
            </xsl:otherwise>
        </xsl:choose>

    </xsl:template>

    <!-- PQ datatype -->
    <xsl:template name="show-PQ">
        <xsl:param name="node"/>
        <xsl:variable name="unit">
            <xsl:call-template name="supportUCUMAnnotations">
                <xsl:with-param name="value" select="$node/@unit"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="not ($node/@nullFlavor)">
                <xsl:choose>
                    <xsl:when test="$node/@value">
                        <xsl:value-of select="$node/@value"/>
                        <xsl:text> </xsl:text>
                        <xsl:choose>
                            <xsl:when test="$unit='1'">
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'77'"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$unit"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <!-- uncoded element -->
                        <xsl:if test="$node/n1:originalText/n1:reference/@value">
                            <xsl:call-template name="show-uncodedElement">
                                <xsl:with-param name="code"
                                                select="$node/n1:originalText/n1:reference/@value"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$node/@nullFlavor"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- IVL_TS datatype -->
    <xsl:template name="show-IVL_TS">
        <xsl:param name="node"/>
        <xsl:variable name="low" select="$node/n1:low"/>
        <xsl:variable name="high" select="$node/n1:high"/>
        <xsl:variable name="width" select="$node/n1:width"/>
        <xsl:variable name="center" select="$node/n1:center"/>
        <xsl:variable name="nullFlavor" select="$node/@nullFlavor"/>
        <xsl:choose>
            <xsl:when test="$width">
                <xsl:call-template name="show-PQ">
                    <xsl:with-param name="node" select="$width"/>
                </xsl:call-template>
                <xsl:choose>
                    <xsl:when test="$low">
                        <xsl:text> </xsl:text>
                        <!-- From -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'147'"/>
                        </xsl:call-template>
                        <xsl:text> </xsl:text>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$low"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$high">
                        <xsl:text> </xsl:text>
                        <!-- Until -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'148'"/>
                        </xsl:call-template>
                        <xsl:text> </xsl:text>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$high"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$center">
                        <xsl:text> </xsl:text>
                        <!-- Around -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'149'"/>
                        </xsl:call-template>
                        <xsl:text> </xsl:text>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$center"/>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$low and $high">
                <!-- From -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'147'"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$low"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <!-- Until -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'148'"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$high"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$low">
                <!-- From -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'147'"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$low"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$high">
                <!-- Until -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'148'"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$high"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$nullFlavor">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- TS Datatype -->
    <xsl:template name="show-TS">
        <xsl:param name="node"/>
        <xsl:choose>
            <xsl:when test="not($node)">
                <xsl:text> </xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="not($node/@nullFlavor)and $node/@value">
                        <xsl:call-template name="formatDateTime">
                            <xsl:with-param name="date" select="$node/@value"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="$node/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- PIVL_TS datatype -->
    <xsl:template name="show-PIVL_TS">
        <xsl:param name="node"/>
        <xsl:variable name="medPhase" select="$node/n1:phase"/>
        <xsl:variable name="medPeriod" select="$node/n1:period"/>
        <xsl:variable name="medPhaseWidth"
                      select="$medPhase/n1:width"/>
        <xsl:variable name="medPhaseLow"
                      select="$medPhase/n1:low"/>
        <!-- Every -->
        <xsl:choose>
            <xsl:when test="$node/@institutionSpecified='true'">
                <xsl:choose>
                    <xsl:when test="(1 div $medPeriod/@value) &gt;= 1">
                        <xsl:value-of select="round(1 div $medPeriod/@value)"/>
                        <xsl:text> </xsl:text>
                        <!-- Time(s) per -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'190'"/>
                        </xsl:call-template>
                        <xsl:text> </xsl:text>
                        <xsl:if test="$medPeriod/@unit">
                            <xsl:call-template name="show-eHDSIUnit">
                                <xsl:with-param name="code" select="$medPeriod/@unit"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>1 </xsl:text>
                        <!-- Time(s) per -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'190'"/>
                        </xsl:call-template>
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="$medPeriod/@value"/>
                        <xsl:text> </xsl:text>
                        <xsl:if test="$medPeriod/@unit">
                            <xsl:call-template name="show-eHDSIUnit">
                                <xsl:with-param name="code" select="$medPeriod/@unit"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <!-- Every -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'27'"/>
                </xsl:call-template>
                <xsl:text>&#160;</xsl:text>
                <xsl:value-of select="$medPeriod/@value"/>
                <xsl:text>&#160;</xsl:text>
                <xsl:if test="$medPeriod/@unit">
                    <xsl:call-template name="show-eHDSIUnit">
                        <xsl:with-param name="code" select="$medPeriod/@unit"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        <!-- if phase.width exists -->
        <xsl:if test="$medPhaseWidth">
            <xsl:text>&#160;</xsl:text>
            <!--for -->
            <xsl:call-template name="show-eHDSIDisplayLabel">
                <xsl:with-param name="code" select="'31'"/>
            </xsl:call-template>
            <xsl:text>&#160;</xsl:text>
            <xsl:value-of select="$medPhaseWidth/@value"/>
            &#160;
            <xsl:call-template name="show-eHDSIUnit">
                <xsl:with-param name="code" select="$medPhaseWidth/@unit"/>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="$medPhaseLow">
            <!-- xsl:text> at </xsl:text -->
            <xsl:call-template name="show-eHDSIDisplayLabel">
                <xsl:with-param name="code" select="'6'"/>
            </xsl:call-template>
            <xsl:call-template name="show-TS">
                <xsl:with-param name="node" select="$medPhaseLow"/>
            </xsl:call-template>
            &#160;
        </xsl:if>
    </xsl:template>

    <!-- EIVL_TS datatype -->
    <xsl:template name="show-EIVL_TS">
        <xsl:param name="node"/>
        <xsl:variable name="medEvent" select="$node/n1:event"/>
        <xsl:variable name="medOffset" select="$node/n1:offset"/>
        <xsl:variable name="medOffsetWidth"
                      select="$medOffset/n1:width"/>
        <xsl:variable name="medOffsetLow"
                      select="$medOffset/n1:low"/>
        <xsl:if test="$medOffsetLow">
            <xsl:value-of select="$medOffsetLow/@value"/>
            <xsl:text> </xsl:text>
            <xsl:value-of select="$medOffsetLow/@unit"/>
            <xsl:text> </xsl:text>
        </xsl:if>
        <xsl:call-template name="show-eHDSITimingEvent">
            <xsl:with-param name="node" select="$medEvent"/>
        </xsl:call-template>
        <xsl:if test="$medOffsetWidth">
            <xsl:text> </xsl:text>
            <xsl:call-template name="show-eHDSIDisplayLabel">
                <xsl:with-param name="code" select="'31'"/>
            </xsl:call-template>
            <xsl:text> </xsl:text>
            <xsl:value-of select="$medOffsetWidth/@value"/>
            <xsl:text> </xsl:text>
            <xsl:call-template name="show-eHDSIUnit">
                <xsl:with-param name="code" select="$medOffsetWidth/@unit"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
