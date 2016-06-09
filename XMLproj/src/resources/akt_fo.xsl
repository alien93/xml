<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0"
	xmlns:p="http://www.parlament.gov.rs/propisi" xmlns:ns1="http://www.parlament.gov.rs/generic_types">
	<xsl:output encoding="UTF-8" />
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="akt"
					page-height="29.7cm" page-width="21cm" margin-top="1cm"
					margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
					<fo:region-body margin-top="1cm" />
					<fo:region-before extent="3cm" />
					<fo:region-after extent="1.5cm" />
					<fo:region-start extent="2cm" />
					<fo:region-end extent="2cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="akt"
				initial-page-number="1">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-family="Arial">
						<fo:block text-align="center" font-size="32px"
							font-weight="bold" padding="30px">
							<xsl:value-of select="p:Akt/@naziv" />
						</fo:block>
						<xsl:apply-templates select="p:Akt/p:Glavni_deo" />
						<xsl:apply-templates select="p:Akt/p:Sporedni_deo" />
						<fo:block text-align="right" font-size="10px" padding-top="20px">
							Status:
							<xsl:value-of select="p:Akt/@status" />
						</fo:block>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="p:Akt/p:Sporedni_deo">
		<fo:block text-align="right" font-size="10px" padding-top="20px">
			<fo:block>
				<xsl:apply-templates select="./p:Akt_u_proceduri" />
				<xsl:apply-templates select="./p:Donet_akt" />
			</fo:block>
			<fo:block>
				Vrsta:
				<xsl:value-of select="./@vrsta" />
			</fo:block>
			<fo:block>
				Datum:
				<xsl:value-of select="./@datum" />
			</fo:block>
			<fo:block>
				Mesto:
				<xsl:value-of select="./@mesto" />
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Donet_akt">
		<fo:block>
			Donet od strane:
			<xsl:value-of select="./p:Donet_od_strane" />
		</fo:block>
		<fo:block>
			Pravni osnov donosenja:
			<xsl:value-of select="./p:Pravni_osnov_donosenja" />
		</fo:block>
		<fo:block>
			Po postupku:
			<xsl:value-of select="./p:Po_postupku" />
		</fo:block>
		<fo:block>
			Po cilju:
			<xsl:value-of select="./p:Po_cilju" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Akt_u_proceduri">
		<fo:block>
			Pregledač:
			<xsl:value-of select="./p:Pregledac" />
		</fo:block>
		<fo:block>
			Upućuje se:
			<xsl:value-of select="./p:Upucuje_se" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Akt/p:Glavni_deo">
		<xsl:apply-templates select="./p:Deo" />
		<xsl:apply-templates select="./p:Glava" />
		<xsl:apply-templates select="./p:Odeljak" />
		<xsl:apply-templates select="./p:Clan" />
		<xsl:apply-templates select="./p:Stav" />
		<xsl:apply-templates select="./p:Tacka" />
		<xsl:apply-templates select="./p:Podtacka" />
		<xsl:apply-templates select="./p:Alineja" />
	</xsl:template>

	<xsl:template match="p:Deo">
		<fo:block text-align="center" font-size="22" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Glava">
		<fo:block text-align="center" font-size="20" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Odeljak">
		<fo:block text-align="center" font-size="18" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Clan">
		<fo:block text-align="center" font-size="16" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Stav">
		<fo:block text-align="center" font-size="15" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Tacka">
		<fo:block text-align="center" font-size="14" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Podtacka">
		<fo:block text-align="center" font-size="13" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Alineja">
		<fo:block text-align="center" font-size="13" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./p:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Sadrzaj/*">
		<xsl:copy>
			<xsl:copy-of select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<!-- GLAVA -->
	<xsl:template match="p:Sadrzaj/ns1:Glava">
		<fo:block text-align="center" font-size="16" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/*">
		<xsl:copy>
			<xsl:copy-of select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<!-- KRAJ GLAVA -->

	<!-- ODELJAK -->

	<xsl:template match="p:Sadrzaj/ns1:Odeljak">
		<fo:block text-align="center" font-size="15" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<!-- KRAJ ODELJAK -->

	<!-- CLAN -->
	<xsl:template match="p:Sadrzaj/ns1:Clan">
		<fo:block text-align="center" font-size="14" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>
	<!-- KRAJ CLAN -->

	<!-- STAV -->
	<xsl:template match="p:Sadrzaj/ns1:Stav">
		<fo:block text-align="center" font-size="13" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>
	<!-- KRAJ STAV -->

	<!-- TACKA -->
	<xsl:template match="p:Sadrzaj/ns1:Tacka">
		<fo:block text-align="center" font-size="12" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>
	<!-- KRAJ TACKA -->

	<!-- PODTACKA -->
	<xsl:template match="p:Sadrzaj/ns1:Podtacka">
		<fo:block text-align="center" font-size="11" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>
	<!-- KRAJ PODTACKA -->

	<!-- ALINEJA -->
	<xsl:template match="p:Sadrzaj/ns1:Alineja">
		<fo:block text-align="center" font-size="10" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>
	<!-- KRAJ ALINEJA -->


	<!-- NS1 TAGOVI -->

	<xsl:template match="ns1:Sadrzaj/ns1:Odeljak">
		<fo:block text-align="center" font-size="15" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Clan">
		<fo:block text-align="center" font-size="14" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Stav">
		<fo:block text-align="center" font-size="13" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Tacka">
		<fo:block text-align="center" font-size="12" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Podtacka">
		<fo:block text-align="center" font-size="11" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Alineja">
		<fo:block text-align="center" font-size="10" font-weight="bold" padding="5px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/text()">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:ref">
		<fo:basic-link external-destination="url({concat('http://localhost:8080/REST',./@id_ref)})"
			color="blue" text-decoration="underline">
			<xsl:value-of select="." />
		</fo:basic-link>
	</xsl:template>

	<!-- NS1 TAGOVI KRAJ -->

	<xsl:template match="text()" />

	<xsl:template match="p:Sadrzaj/text()">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="p:Sadrzaj/ns1:ref">
		<fo:basic-link external-destination="url({concat('http://localhost:8080/REST',./@id_ref)})"
			color="blue" text-decoration="underline">
			<xsl:value-of select="." />
		</fo:basic-link>
	</xsl:template>

</xsl:stylesheet>