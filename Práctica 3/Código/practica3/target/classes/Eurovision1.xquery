xquery version "3.0";

(: 1.En primer lugar, escribe una consulta que obtenga los años de las ediciones del festival almacenadas en la BD. Guarda esta consulta en un fichero Eurovision1.xquery. :)

for $b in doc("Eurovision.xml")//edicion
return data($b/anyo)