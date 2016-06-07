declare namespace p="http://www.parlament.gov.rs/propisi";
for $doc in fn:collection("/tim3/test")
return ($doc)//p:Akt/@naziv