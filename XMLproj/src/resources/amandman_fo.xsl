<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0"
	xmlns:p="http://www.parlament.gov.rs/propisi" xmlns:ns1="http://www.parlament.gov.rs/generic_types">
	<xsl:output encoding="UTF-8" />
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="amandman"
					page-height="29.7cm" page-width="21cm" margin-top="1cm"
					margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
					<fo:region-body margin-top="1cm" />
					<fo:region-before extent="3cm" />
					<fo:region-after extent="1.5cm" />
					<fo:region-start extent="2cm" />
					<fo:region-end extent="2cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="amandman"
				initial-page-number="1">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-family="Arial">
						<fo:block text-align="center" font-size="25px"
							font-weight="bold" padding="30px">
							<xsl:value-of select="p:Amandman//p:Meta_podaci/ns1:Naziv" />
						</fo:block>
						<xsl:apply-templates select="p:Amandman/p:Glavni_deo" />
						<xsl:apply-templates select="p:Amandman/p:Sporedni_deo" />
						<fo:block text-align="right" font-size="10px"
							padding-top="20px">
							Status:
							<xsl:value-of select="p:Amandman/@status" />
						</fo:block>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="p:Amandman/p:Sporedni_deo">
		<fo:block text-align="right" font-size="10px" padding-top="20px">
			<fo:block>
				<xsl:apply-templates select="./p:Pravni_osnov_donosenja" />
				<xsl:apply-templates select="./p:Donet_od_strane" />
				<xsl:apply-templates select="./p:Postupak_donosenja" />
				<xsl:apply-templates select="./p:Cilj_donosenja" />
			</fo:block>
			<fo:block>
				Vrsta:
				<xsl:value-of select="./p:Meta_podaci/ns1:Vrsta" />
			</fo:block>
			<fo:block>
				Datum:
				<xsl:value-of select="./p:Meta_podaci/ns1:Datum" />
			</fo:block>
			<fo:block>
				Mesto:
				<xsl:value-of select="./p:Meta_podaci/ns1:Mesto" />
			</fo:block>

			<fo:block>
				Broj pozitivnih glasova:
				<xsl:value-of select="./p:Meta_podaci/ns1:BrPozitivnihGlasova" />
			</fo:block>
			<fo:block>
				Broj ukupnih glasova:
				<xsl:value-of select="./p:Meta_podaci/ns1:BrUkupnihGlasova" />
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Pravni_osnov_donosenja">
		<fo:block>
			Pravni osnov donosenja:
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Donet_od_strane">
		<fo:block>
			Donet od strane:
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Postupak_donosenja">
		<fo:block>
			Po postupku:
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Cilj_donosenja">
		<fo:block>
			Po cilju:
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="p:Amandman/p:Glavni_deo">
		<xsl:apply-templates select="./ns1:Deo" />
		<xsl:apply-templates select="./ns1:Glava" />
		<xsl:apply-templates select="./ns1:Odeljak" />
		<xsl:apply-templates select="./ns1:Clan" />
		<xsl:apply-templates select="./ns1:Stav" />
		<xsl:apply-templates select="./ns1:Tacka" />
		<xsl:apply-templates select="./ns1:Podtacka" />
		<xsl:apply-templates select="./ns1:Alineja" />
	</xsl:template>

	<xsl:template match="ns1:Deo">
		<fo:block text-align="center" font-size="22" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Glava">
		<fo:block text-align="center" font-size="20" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Odeljak">
		<fo:block text-align="center" font-size="18" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Clan">
		<fo:block text-align="center" font-size="16" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Stav">
		<fo:block text-align="center" font-size="15" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Tacka">
		<fo:block text-align="center" font-size="14" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Podtacka">
		<fo:block text-align="center" font-size="13" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Alineja">
		<fo:block text-align="center" font-size="13" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Status">
		<fo:block text-align="right" padding="5px" font-size="10px">
			<fo:block>
				Referenca:
				<fo:basic-link
					external-destination="url({concat('http://localhost:8080/REST',./ns1:ref/@id_ref)})"
					color="blue" text-decoration="underline">
					<xsl:value-of select="./ns1:ref" />
				</fo:basic-link>
			</fo:block>
			<fo:block>
				Status izmene:
				<xsl:value-of select="./ns1:status_izmene" />
			</fo:block>
		</fo:block>
		<xsl:apply-templates select="./ns1:status_izmene" />
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/*">
		<xsl:copy>
			<xsl:copy-of select="@*" />
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<!-- GLAVA -->
	<xsl:template match="ns1:Sadrzaj/ns1:Glava">
		<fo:block text-align="center" font-size="16" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<!-- KRAJ GLAVA -->

	<!-- ODELJAK -->


	<!-- KRAJ ODELJAK -->

	<!-- CLAN -->

	<!-- KRAJ CLAN -->

	<!-- STAV -->

	<!-- KRAJ STAV -->

	<!-- TACKA -->

	<!-- KRAJ TACKA -->

	<!-- PODTACKA -->

	<!-- KRAJ PODTACKA -->

	<!-- ALINEJA -->

	<!-- KRAJ ALINEJA -->


	<!-- NS1 TAGOVI -->

	<xsl:template match="ns1:Sadrzaj/ns1:Odeljak">
		<fo:block text-align="center" font-size="15" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Clan">
		<fo:block text-align="center" font-size="14" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Stav">
		<fo:block text-align="center" font-size="13" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Tacka">
		<fo:block text-align="center" font-size="12" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Podtacka">
		<fo:block text-align="center" font-size="11" font-weight="bold"
			padding="10px">
			<xsl:value-of select="./@naziv" />
		</fo:block>
		<fo:block>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</fo:block>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Alineja">
		<fo:block text-align="center" font-size="10" font-weight="bold"
			padding="10px">
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