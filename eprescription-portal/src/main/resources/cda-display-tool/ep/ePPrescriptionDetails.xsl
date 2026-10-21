<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                version="2.0">

    <xsl:template name="prescriptionDetailsBlock">

        <!-- Prescription details block -->
        <fieldset style="min-height:100px;">
            <legend>
                <!-- Prescription Details -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'129'"/>
                </xsl:call-template>
            </legend>
            <table class="ep_table">
                <tr>
                    <th>
                        <!-- Prescription Item ID -->
                        <!-- TODO add concept to eHDSIDisplayLabel value set -->
                        Prescription Item ID
                    </th>
                    <td>
                        <xsl:value-of select="n1:id/@extension"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Duration of treatment -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'150'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-IVL_TS">
                            <xsl:with-param name="node"
                                            select="n1:effectiveTime[1][@xsi:type='IVL_TS' or substring-after(@xsi:type, ':')='IVL_TS']"/>
                        </xsl:call-template>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Units per intake -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'78'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-IVL_PQ">
                            <xsl:with-param name="node" select="n1:doseQuantity"/>
                        </xsl:call-template>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Frequency of Intakes -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'32'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:variable name="hasFrequency">
                            <xsl:call-template name="check-Parameter">
                                <xsl:with-param name="parameter" select="n1:effectiveTime[2]/n1:period/@nullFlavor"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:variable name="intakesFrequency">
                            <xsl:call-template name="check-Parameter">
                                <xsl:with-param name="parameter" select="n1:effectiveTime[2]/@nullFlavor"/>
                            </xsl:call-template>
                        </xsl:variable>

                        <xsl:choose>
                            <xsl:when test="$intakesFrequency = 'false'">
                                <xsl:call-template name="show-eHDSINullFlavor">
                                    <xsl:with-param name="code" select="n1:effectiveTime[2]/@nullFlavor"/>
                                </xsl:call-template>

                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:choose>
                                    <xsl:when test="$hasFrequency = 'false'">
                                        <xsl:call-template name="show-eHDSINullFlavor">
                                            <xsl:with-param name="code"
                                                            select="n1:effectiveTime[2]/n1:period/@nullFlavor"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="frequency"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!--   Instructions to patient:-->
                        <span class="td_label">
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'37'"/>
                            </xsl:call-template>
                        </span>
                    </th>
                    <td>
                        <xsl:call-template name="show-text">
                            <xsl:with-param name="txt"
                                            select="n1:entryRelationship[@typeCode='SUBJ']/n1:act[n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.3'] ]/n1:text"/>
                        </xsl:call-template>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!--  Advise to the dispenser:-->
                        <span class="td_label">
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'4'"/>
                            </xsl:call-template>
                        </span>
                    </th>
                    <td>
                        <xsl:call-template name="show-text">
                            <xsl:with-param name="txt"
                                            select="n1:entryRelationship[@typeCode='SUBJ']/n1:act[n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.3.1'] ]/n1:text"/>
                        </xsl:call-template>
                    </td>
                </tr>
                <tr>
                    <th>
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'38'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="substitution-code"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </xsl:template>
</xsl:stylesheet>