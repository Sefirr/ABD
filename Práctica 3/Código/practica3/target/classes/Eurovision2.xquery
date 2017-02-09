xquery version "3.0";

(: 2. A continuación escribe en Eurovision2.xquery una consulta que devuelva la clasi1cación total de los participantes en el festival en un determinado año (por ejemplo, 2014). Tras la declaración escribe la consulta propiamente dicha, que deberá obtener, para cada participante de la edición $anyo: el país correspondiente, el nombre de canción, el nombre de artista y la puntuación total obtenida. Cada resultado de la consulta debe mostrarse en un elemento
<clasificacion> con el siguiente formato:

<clasificacion pais=" nombre del país " cancion=" nombre de la canción "
artista=" nombre del artista " puntos=" puntuación total "/>
Los elementos <clasificacion> del resultado deben estar ordenados en order decreciente de
puntuación.

:)
declare variable $anyo as xs:integer external;

for $edicion in doc("Eurovision.xml")//edicion
where number($edicion/anyo) = $anyo
return for $participante in $edicion/participantes/participante
       let $artista := doc("Eurovision.xml")//artista[@id = $participante/representante/@id]/nombre
       let $puntuacion := sum(for $votos in $edicion/participantes/participante/votos/voto
                              where $votos/receptor/text() = $participante/paisp/text()
                              return data($votos/puntuacion) )
       order by $puntuacion descending
       return <clasificacion pais="{$participante/paisp}" cancion="{$participante/cancion/text()}" artista ="{$artista}" puntos="{$puntuacion}"/>
    