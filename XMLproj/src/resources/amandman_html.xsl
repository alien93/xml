<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:p="http://www.parlament.gov.rs/propisi" xmlns:ns1="http://www.parlament.gov.rs/generic_types">
	<xsl:output encoding="UTF-8" />
	<xsl:template match="/">

		<html>
			<head>
				<style media="screen" type="text/css">
					body{
					margin-left: 15%;
					margin-right: 5%;
					font-size: 20px;
					}
					.ref{
					text-align:right;
					}
					h,h1,h2,h3,h4,h5,h6{
					text-align:
					center;
					font-weight: bold;
					padding:
					20;
					}
					h1.naslov{
					font-size: 37px;
					padding: 30px;
					}

					.meta_data{
					font-size:
					20px;
					padding-top: 20px;
					text-align: right;
					}

					h1{
					font-size:32px;
					}

					h2{
					font-size: 30px;
					}

					h3{
					font-size: 28px;
					}

					h4{
					font-size: 26px;
					}

					h5{
					font-size: 24px;
					}

					h6{
					font-size: 22px;
					}
				</style>
			</head>
			<body>
				<h1 class="naslov">
					<xsl:value-of select=".//p:Meta_podaci/ns1:Naziv" />
				</h1>
				<xsl:apply-templates select="p:Amandman/p:Glavni_deo" />
				<xsl:apply-templates select="p:Amandman/p:Sporedni_deo" />
				<p class="meta_data">
					Status:
					<xsl:value-of select="p:Amandman/@status" />
				</p>
			</body>
		</html>

	</xsl:template>
	<xsl:template match="p:Amandman/p:Sporedni_deo">
		<div class="meta_data">
			<div>
				<xsl:apply-templates select="./p:Pravni_osnov_donosenja" />
				<xsl:apply-templates select="./p:Donet_od_strane" />
				<xsl:apply-templates select="./p:Postupak_donosenja" />
				<xsl:apply-templates select="./p:Cilj_donosenja" />
			</div>
			<p>
				Vrsta:
				<xsl:value-of select="./p:Meta_podaci/ns1:Vrsta" />
			</p>
			<p>
				Datum:
				<xsl:value-of select="./p:Meta_podaci/ns1:Datum" />
			</p>
			<p>
				Mesto:
				<xsl:value-of select="./p:Meta_podaci/ns1:Mesto" />
			</p>

			<p>
				Broj pozitivnih glasova:
				<xsl:value-of select="./p:Meta_podaci/ns1:BrPozitivnihGlasova" />
			</p>
			<p>
				Broj ukupnih glasova:
				<xsl:value-of select="./p:Meta_podaci/ns1:BrUkupnihGlasova" />
			</p>
		</div>
	</xsl:template>

	<xsl:template match="p:Pravni_osnov_donosenja">
		<p>
			Pravni osnov donosenja:
			<xsl:value-of select="." />
		</p>
	</xsl:template>

	<xsl:template match="p:Donet_od_strane">
		<p>
			Donet od strane:
			<xsl:value-of select="." />
		</p>
	</xsl:template>

	<xsl:template match="p:Postupak_donosenja">
		<p>
			Po postupku:
			<xsl:value-of select="." />
		</p>
	</xsl:template>

	<xsl:template match="p:Cilj_donosenja">
		<p>
			Po cilju:
			<xsl:value-of select="." />
		</p>
	</xsl:template>

	<xsl:template match="p:Amandman/p:Glavni_deo">
		<div class="main_part">
			<xsl:apply-templates select="./ns1:Deo" />
			<xsl:apply-templates select="./ns1:Glava" />
			<xsl:apply-templates select="./ns1:Odeljak" />
			<xsl:apply-templates select="./ns1:Clan" />
			<xsl:apply-templates select="./ns1:Stav" />
			<xsl:apply-templates select="./ns1:Tacka" />
			<xsl:apply-templates select="./ns1:Podtacka" />
			<xsl:apply-templates select="./ns1:Alineja" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Deo">
		<h1>
			<xsl:value-of select="./@naziv" />
		</h1>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Glava">
		<h2>
			<xsl:value-of select="./@naziv" />
		</h2>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Odeljak">
		<h3>
			<xsl:value-of select="./@naziv" />
		</h3>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Clan">
		<h4>
			<xsl:value-of select="./@naziv" />
		</h4>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Stav">
		<h5>
			<xsl:value-of select="./@naziv" />
		</h5>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Tacka">
		<h6>
			<xsl:value-of select="./@naziv" />
		</h6>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Podtacka">
		<h6>
			<xsl:value-of select="./@naziv" />
		</h6>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Alineja">
		<h6>
			<xsl:value-of select="./@naziv" />
		</h6>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
			<xsl:apply-templates select="./ns1:Status" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Status">
		<div class="ref">
			<p>
				Referenca:
				<a>
					<xsl:attribute name="href">
    					<xsl:value-of
						select="concat('http://localhost:8080/XMLproj/rest/pdfReference/ref',./ns1:ref/@id_ref)" />
  					</xsl:attribute>
					<xsl:value-of select="./ns1:ref" />
				</a>
			</p>
			<p>
				Status izmene:
				<xsl:value-of select="./ns1:status_izmene" />
			</p>
		</div>
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
		<h2>
			<xsl:value-of select="./@naziv" />
		</h2>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<!-- KRAJ GLAVA -->

	<!-- ODELJAK -->



	<!-- NS1 TAGOVI -->

	<xsl:template match="ns1:Sadrzaj/ns1:Odeljak">
		<h3>
			<xsl:value-of select="./@naziv" />
		</h3>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Clan">
		<h4>
			<xsl:value-of select="./@naziv" />
		</h4>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Stav">
		<h5>
			<xsl:value-of select="./@naziv" />
		</h5>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Tacka">
		<h6>
			<xsl:value-of select="./@naziv" />
		</h6>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Podtacka">
		<h6>
			<xsl:value-of select="./@naziv" />
		</h6>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:Alineja">
		<h6>
			<xsl:value-of select="./@naziv" />
		</h6>
		<div>
			<xsl:apply-templates select="./ns1:Sadrzaj" />
		</div>
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/text()">
		<xsl:copy-of select="." />
	</xsl:template>

	<xsl:template match="ns1:Sadrzaj/ns1:ref">
		<a>
			<xsl:attribute name="href">
    			<xsl:value-of
				select="concat('http://localhost:8080/XMLproj/rest/pdfReference/ref',./@id_ref)" />
  			</xsl:attribute>
			<xsl:value-of select="." />
		</a>
	</xsl:template>

	<!-- NS1 TAGOVI KRAJ -->

	<xsl:template match="text()" />

</xsl:stylesheet>