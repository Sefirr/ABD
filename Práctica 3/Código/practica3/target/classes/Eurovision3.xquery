xquery version "3.0";

(: 3. Por último, escribe una consulta que devuelva un fragmento XHTML con las actuaciones de una
determinada edición. En primer lugar debe mostrarse el nombre de la ciudad y el país donde tuvo
lugar el concurso. Tras esto debe mostrarse, para cada actuación: el país, el nombre del artista,
el nombre de la canción, la descripción del artista, la imagen del artista, y la lista de países que
votaron a esa actuación.
Para realizar esta consulta define, en un nuevo fichero Eurovision3.xquery una variable $anyo
del mismo modo que en el apartado anterior. A continuación escribe la consulta XQuery, que
muestre la información requerida con el siguiente formato:
<body>
<h1> Nombre de ciudad ( Nombre del país )</h1>
<ol>
Para cada actuación debe mostrarse lo siguiente:
<li>
<p> Nombre del país - Nombre del artista - <i> Nombre de la canción </i></p>
<p> Descripción del artista </p>
<img src=" URL de la imagen del artista "/>
<p><b>Recibió votos de: </b> Lista de los países que le han votado, separados por comas </p>
</li>
</ol>
</body>  :)


declare variable $anyo as xs:integer external;

for $edicion in doc("Eurovision.xml")//edicion
where number($edicion/anyo) = $anyo
return <body>
    <h1> {data($edicion/ciudad/text())} ( {data($edicion/paise/text())} )</h1> 
    <ol>{for $actuacion in $edicion/participantes/participante
        let $artista_nombre      := doc("Eurovision.xml")//artista[@id = $actuacion/representante/@id]/nombre
        let $artista_descripcion := doc("Eurovision.xml")//artista[@id = $actuacion/representante/@id]/descripcion
        let $artista_imagen      := doc("Eurovision.xml")//artista[@id = $actuacion/representante/@id]/foto
        order by $actuacion/orden ascending
        return
        <li>
            <p> {data($actuacion/paisp)} - {data($artista_nombre)} - <i> {data($actuacion/cancion)}</i> </p>
            <p> {data($artista_descripcion)} </p>
            <img src="{$artista_imagen}"/>
            <p><b>Recibió votos de: </b>
               {string-join(for $votantes in $edicion/participantes/participante
                where $votantes/votos/voto/receptor/text() = $actuacion/paisp/text()
                return data($votantes/paisp/text()),',')} 
            </p>
         </li>}
    </ol>
</body>