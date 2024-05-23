<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:uuid="java:java.util.UUID" xmlns:xslo="http://www.w3.org/1999/XSL/TransformTemp" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:proxy="com:spirit:SpiritProxy" xmlns:cda="urn:hl7-org:v3" xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
	<!-- -->
	<xsl:preserve-space elements="xsl:text"/>
	<!-- -->
	<xsl:namespace-alias stylesheet-prefix="xslo" result-prefix="xsl"/>
	<!-- -->
	<xsl:variable name="processedDocumentCode" select="insertedDynamically"/>
	<xsl:variable name="processedDocumentCodeSystem" select="insertedDynamically"/>
	<!-- -->
	<xsl:variable name="statementDelimiterCharacter" select="if(/root/@statementDelimiterCharacter) then xs:string(/root/@statementDelimiterCharacter) else ';' "/>
	<xsl:variable name="insertNewlinesAfterSqlStatements" select="if(/root/@insertNewlinesAfterSqlStatements) then xs:string(/root/@insertNewlinesAfterSqlStatements) else 'true' "/>
	<xsl:variable name="columnDelimiterCharacter" select="if(/root/@columnDelimiterCharacter) then xs:string(/root/@columnDelimiterCharacter) else ',' "/>
	<xsl:variable name="insertNewlinesAfterSqlColumns" select="if(/root/@insertNewlinesAfterSqlColumns) then xs:string(/root/@insertNewlinesAfterSqlColumns) else 'false' "/>
	<xsl:variable name="quoteCharacterForValues" select="if(/root/@quoteCharacterForValues) then xs:string(/root/@quoteCharacterForValues) else '''' "/>

	<!-- ATTENTION: Please keep an indentation of 0 for all occurrences of <xsl:text>, </xsl:text>, <xslo:text> and </xslo:text> for the following block. -->
	<!-- ##################################################################################################################### -->
	<!-- ##################################################################################################################### -->
	<xsl:template name="sqlStatementDelimiterTemplate">
		<xslo:text><xsl:value-of select="string($statementDelimiterCharacter)"/></xslo:text>
	</xsl:template>
	<xsl:template name="sqlStatementNewlineTemplate">
		<xsl:if test="xs:boolean($insertNewlinesAfterSqlStatements)">
<xslo:text>
<xsl:text>
</xsl:text>
</xslo:text>
		</xsl:if>
	</xsl:template>
	<!-- -->
	<xsl:template name="sqlColumnDelimiterTemplate">
		<xslo:text><xsl:value-of select="string($columnDelimiterCharacter)"/></xslo:text>
	</xsl:template>
	<!-- -->
	<xsl:template name="sqlColumnNewlineTemplate">
		<xsl:if test="xs:boolean($insertNewlinesAfterSqlColumns)">
<xslo:text>
<xsl:text>
</xsl:text>
</xslo:text>
		</xsl:if>
	</xsl:template>
	<!-- -->
	<!-- ##################################################################################################################### -->
	<!-- ##################################################################################################################### -->
	<!-- -->
	<!-- Function for handling special-characters within the content to be inserted -->
	<xsl:function name="proxy:encodeForSql">
		<xsl:param name="input" as="xs:string"/>
		<xsl:value-of select="replace($input,'''','''''')"/>
	</xsl:function>
	<!-- -->
	<xsl:template match="/">
		<xslo:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:cda="urn:hl7-org:v3" xmlns:proxy="com:spirit:SpiritProxy" xmlns:epsos="urn:epsos-org:ep:medication" xmlns:fn="http://www.w3.org/2005/xpath-functions">
			<xslo:output method="text"/>
			<!-- -->
			<!-- Globally used Constants -->
			<!-- ATTENTION: Please keep an indentation of 0 for the following four lines. This is used for the newline-character -->
			<!-- -->
			<!-- Function for handling special-characters within the content to be inserted -->
			<xslo:function name="proxy:encodeForSql">
				<xslo:param name="input" as="xs:string"/>
				<xslo:value-of select="replace($input,'''','''''')"/>
			</xslo:function>
			<!-- -->
			<!-- generate the root node -->
			<xslo:template match="/">
				<root>
					<xslo:apply-templates select="./*"/>
				</root>
			</xslo:template>
			<!-- -->
			<!-- process all Transaction extraction-rules for generating the INSERTS into the table "TransactionInfo" -->
			<xsl:for-each select="/root/mainTable/extractionRule[1]">
				<xslo:template match="{@matchXPath}">
					<xsl:text>INSERT INTO </xsl:text>
					<xsl:value-of select="/root/mainTable/@tableName"/>
					<!-- -->
					<!-- Definition of the Columns -->
					<xsl:text>(</xsl:text>
					<xsl:call-template name="sqlColumnNewlineTemplate"/>
					<xsl:text>Transaction_PK</xsl:text>
					<xsl:for-each select="mapping">
						<xsl:call-template name="sqlColumnDelimiterTemplate"/>
						<xsl:call-template name="sqlColumnNewlineTemplate"/>
						<xslo:text>
							<xsl:value-of select="@targetColumn"/>
						</xslo:text>
					</xsl:for-each>
					<xsl:call-template name="sqlColumnNewlineTemplate"/>
					<xsl:text>)</xsl:text>
					<xsl:call-template name="sqlColumnNewlineTemplate"/>
					<!-- -->
					<!-- Inserting the values -->
					<xsl:text>VALUES</xsl:text>
					<xsl:call-template name="sqlColumnNewlineTemplate"/>
					<xsl:text>(</xsl:text>
					<xsl:call-template name="sqlColumnNewlineTemplate"/>
					<xsl:text>'</xsl:text>
					<xslo:value-of select="/proxy:Transaction/proxy:TransactionInfo/@Transaction_PK"/>
					<xsl:text>'</xsl:text>
					<xsl:for-each select="./*">
						<xsl:call-template name="sqlColumnDelimiterTemplate"/>
						<xsl:call-template name="sqlColumnNewlineTemplate"/>
						<xsl:text>'</xsl:text>
						<xslo:value-of select="proxy:encodeForSql(string({@sourceXPath}))"/>
						<xsl:text>'</xsl:text>
					</xsl:for-each>
					<xsl:call-template name="sqlColumnNewlineTemplate"/>
					<xsl:text>)</xsl:text>
					<xsl:call-template name="sqlStatementDelimiterTemplate"/>
					<xsl:call-template name="sqlStatementNewlineTemplate"/>
				</xslo:template>
			</xsl:for-each>
			<!-- process all CDA extraction-rules for generating the INSERTS into the table "TransactionInfo" -->
			<xsl:for-each select="/root/childTable">
				<xsl:apply-templates select="./*"/>
			</xsl:for-each>
			
			<!-- Removing all non-matched text-content -->
			<xslo:template match="text()"/>
		</xslo:stylesheet>
	</xsl:template>

	<xsl:template match="group">
		<xsl:choose>
			<xsl:when test="count(control/skip/code[@codeSystem=$processedDocumentCodeSystem][@code=$processedDocumentCode])!=0">
				<!-- SKIP -->
			</xsl:when>
			<xsl:when test="count(control/use/code[@codeSystem=$processedDocumentCodeSystem][@code=$processedDocumentCode])!=0">
				<!-- USE -->
				<xsl:apply-templates select="content/*"/>
			</xsl:when>
			<xsl:when test="count(control/use)=0">
				<!-- USE -->
				<xsl:apply-templates select="content/*"/>
			</xsl:when>
			<xsl:otherwise>
				<!-- SKIP -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="extractionRule">
		<xslo:template match="{@matchXPath}">
			<xsl:text>INSERT INTO </xsl:text>
			<xsl:value-of select="/root/childTable/@tableName"/>
			<!-- -->
			<!-- Definition of the Columns -->
			<xsl:text>(</xsl:text>
			<xsl:call-template name="sqlColumnNewlineTemplate"/>
			<xsl:text>Transaction_FK</xsl:text>
			<xsl:for-each select="mapping">
				<xsl:call-template name="sqlColumnDelimiterTemplate"/>
				<xsl:call-template name="sqlColumnNewlineTemplate"/>
				<xslo:text>
					<xsl:value-of select="@targetColumn"/>
				</xslo:text>
			</xsl:for-each>
			<xsl:call-template name="sqlColumnNewlineTemplate"/>
			<xsl:text>) </xsl:text>
			<xsl:call-template name="sqlColumnNewlineTemplate"/>
			<!-- -->
			<!-- Inserting the values -->
			<xsl:text>VALUES</xsl:text>
			<xsl:call-template name="sqlColumnNewlineTemplate"/>
			<xsl:text>(</xsl:text>
			<xsl:call-template name="sqlColumnNewlineTemplate"/>
			<xsl:text>'</xsl:text>
			<xslo:value-of select="/proxy:Transaction/proxy:TransactionInfo/@Transaction_PK"/>
			<xsl:text>'</xsl:text>
			<xsl:for-each select="./*">
				<xsl:text>,</xsl:text>
				<xsl:call-template name="sqlColumnNewlineTemplate"/>
				<xsl:text>'</xsl:text>
				<xslo:variable name="currentValue" select="string({@sourceXPath})"/>
				<xslo:choose>
					<xslo:when test="starts-with($currentValue,'#')">
						<xslo:value-of select="proxy:encodeForSql(string(/proxy:Transaction/cda:ClinicalDocument//*[@ID=substring-after($currentValue,'#')]))"/>
					</xslo:when>
					<xslo:otherwise>
						<xslo:value-of select="proxy:encodeForSql(string($currentValue))"/>
					</xslo:otherwise>
				</xslo:choose>
				<xsl:text>'</xsl:text>
			</xsl:for-each>
			<xsl:call-template name="sqlColumnNewlineTemplate"/>
			<xsl:text>)</xsl:text>
			<xsl:call-template name="sqlStatementDelimiterTemplate"/>
			<xsl:call-template name="sqlStatementNewlineTemplate"/>
		</xslo:template>	
	</xsl:template>
	
</xsl:stylesheet>
