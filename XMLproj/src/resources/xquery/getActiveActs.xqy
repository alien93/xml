declare namespace p="http://www.parlament.gov.rs/propisi";
for $doc in fn:collection("/akti/u_proceduri")
return ($doc)//p:Akt/@naziv