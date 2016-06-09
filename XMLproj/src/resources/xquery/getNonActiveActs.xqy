declare namespace p="http://www.parlament.gov.rs/propisi";
for $doc in fn:collection("/akti/doneti")
return ($doc)//p:Akt/@naziv