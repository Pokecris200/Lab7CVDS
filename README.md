# José Manuel Gamboa - Cristian Camilo Forero

## LABORATORIO 7 - PERSISTENCIA - 2021-2

## SECCIÓN I. - INTRODUCCIÓN A JDBC

2. Descargue el archivo JDBCExample.java y agreguelo en el paquete "edu.eci.cvds.sampleprj.jdbc.example".

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Ubicacion%20Archivo%20punto%202.png)

6. En la clase JDBCExample juste los parámetros de conexión a la base de datos con los datos reales:
+ Url: jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba
+ Driver: com.mysql.jdbc.Driver
+ Usuario: bdprueba
+ Contraseña: prueba2019

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Parametros%20de%20conexion.png)

7. Implemente las operaciones faltantes:
+ nombresProductosPedido

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Nombres%20productos%20del%20pedido.png)

+ valorTotalPedido - El resultado final lo debe retornar la base de datos, no se deben hacer operaciones en memoria.

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Valor%20del%20pedido.png)

+ registrarNuevoProducto - Use su código de estudiante para evitar colisiones.

8. Verifique por medio de un cliente SQL, que la información retornada por el programa coincide con la que se encuentra almacenada en base de datos.

+ nombresProductosPedido

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Nombres%20productos%20del%20pedido%20Client%20MySQL.png)

+ valorTotalPedido

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Valor%20del%20pedido%20Client%20MySQL.png)

+ registrarNuevoProducto

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Adicion%20nuevo%20Producto%20Client%20MySQL.png)

## Parte I (Para entregar en clase)

1. Ubique los archivos de configuración para producción de MyBATIS (mybatis-config.xml). Éste está en la ruta donde normalmente se ubican los archivos de configuración de aplicaciones montadas en Maven (src/main/resources). Edítelos y agregue en éste, después de la sección &lt;settings&gt; los siguientes 'typeAliases':

	```xml
    <typeAliases>
        <typeAlias type='edu.eci.cvds.samples.entities.Cliente' alias='Cliente'/>
        <typeAlias type='edu.eci.cvds.samples.entities.Item' alias='Item'/>
        <typeAlias type='edu.eci.cvds.samples.entities.ItemRentado' alias='ItemRentado'/>
        <typeAlias type='edu.eci.cvds.samples.entities.TipoItem' alias='TipoItem'/>
    </typeAliases>	
    ```
![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/TypeAliases%20mybatis-config.png)

2. Lo primero que va a hacer es configurar un 'mapper' que permita que el framework reconstruya todos los objetos Cliente con sus detalles (ItemsRentados). Para hacer más eficiente la reconstrucción, la misma se realizará a partir de una sola sentencia SQL que relaciona los Clientes, sus Items Rentados, Los Items asociados a la renta, y el tipo de item. Ejecute esta sentencia en un cliente SQL (en las estaciones Linux está instalado EMMA), y revise qué nombre se le está asignando a cada columna del resultado:

	```sql
		select
        c.nombre,
        c.documento,
        c.telefono,
        c.direccion,
        c.email,
        c.vetado,
        ir.id ,
        ir.fechainiciorenta ,
        ir.fechafinrenta ,
        i.id ,
        i.nombre ,
        i.descripcion ,
        i.fechalanzamiento ,
        i.tarifaxdia ,
        i.formatorenta ,
        i.genero ,        
        ti.id ,
        ti.descripcion 
        FROM VI_CLIENTES as c 
        left join VI_ITEMRENTADO as ir on c.documento=ir.CLIENTES_documento 
        left join VI_ITEMS as i on ir.ITEMS_id=i.id 
        left join VI_TIPOITEM as ti on i.TIPOITEM_id=ti.id 
	```
![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consulta%20SQL%20MyBatis.png)

3. Abra el archivo XML en el cual se definirán los parámetros para que MyBatis genere el 'mapper' de Cliente (ClienteMapper.xml). Ahora, mapee un elemento de tipo \<select> al método 'consultarClientes':

	```xml
   <select parameterType="map" id="consultarClientes" resultMap="ClienteResult">
   			SENTENCIA SQL
	</select>
	```
![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Actualizacion%20ClienteMapper.png)

4. Note que el mapeo hecho anteriormente, se indica que los detalles de a qué atributo corresponde cada columna del resultado de la consulta están en un 'resultMap' llamado "ClienteResult". En el XML del mapeo agregue un elemento de tipo &lt;resultMap&gt;, en el cual se defina, para una entidad(clase) en particular, a qué columnas estarán asociadas cada una de sus propiedades (recuerde que propiedad != atributo). La siguiente es un ejemplo del uso de la sintaxis de &lt;resultMap&gt; para la clase Maestro, la cual tiene una relación 'uno a muchos' con la clase DetalleUno y una relación 'uno a uno' con la clase DetalleDos, y donde -a la vez-, DetalleUno tiene una relación 'uno-a-uno- con DetalleDos:

	```xml
    <resultMap type='Maestro' id='MaestroResult'>
        <id property='propiedad1' column='COLUMNA1'/>
        <result property='propiedad2' column='COLUMNA2'/>
        <result property='propiedad3' column='COLUMNA3'/>  
        <association property="propiedad5" javaType="DetalleDos"></association>      
        <collection property='propiedad4' ofType='DetalleUno'></collection>
    </resultMap>

    <resultMap type='DetalleUno' id='DetalleUnoResult'>
        <id property='propiedadx' column='COLUMNAX'/>
        <result property='propiedady' column='COLUMNAY'/>
        <result property='propiedadz' column='COLUMNAZ'/> 
	<association property="propiedadw" javaType="DetalleDos"></association>      
    </resultMap>
    
    <resultMap type='DetalleDos' id='DetalleDosResult'>
        <id property='propiedadr' column='COLUMNAR'/>
        <result property='propiedads' column='COLUMNAS'/>
        <result property='propiedadt' column='COLUMNAT'/>        
    </resultMap>

	```

	Como observa, Para cada propiedad de la clase se agregará un elemento de tipo &lt;result&gt;, el cual, en la propiedad 'property' indicará el nombre de la propiedad, y en la columna 'column' indicará el nombre de la columna de su tabla correspondiente (en la que se hará persistente). En caso de que la columna sea una llave primaria, en lugar de 'result' se usará un elemento de tipo 'id'. Cuando la clase tiene una relación de composición con otra, se agrega un elemento de tipo &lt;association&gt;.Finalmente, observe que si la clase tiene un atributo de tipo colección (List, Set, etc), se agregará un elemento de tipo &lt;collection&gt;, indicando (en la propiedad 'ofType') de qué tipo son los elementos de la colección. En cuanto al indentificador del 'resultMap', como convención se suele utilizar el nombre del tipo de dato concatenado con 'Result' como sufijo.
	
	Teniendo en cuenta lo anterior, haga cuatro 'resultMap': uno para la clase Cliente, otro para la clase ItemRentado, otro para la clase Item, y otro para la clase TipoItem. 

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/ResultMaps.png)

5. Una vez haya hecho lo anterior, es necesario que en el elemento &lt;collection&gt; del maestro se agregue una propiedad que indique cual es el 'resultMap' a través del cual se podrá 'mapear' los elementos contenidos en dicha colección. Para el ejemplo anterior, como la colección contiene elementos de tipo 'Detalle', se agregará el elemento __resultMap__ con el identificador del 'resultMap' de Detalle:

	```xml
	<collection property='propiedad3' ofType='Detalle' resultMap='DetalleResult'></collection>
	```

	Teniendo en cuenta lo anterior, haga los ajustes correspondientes en la configuración para el caso del modelo de Alquiler de películas.

	
6. Si intenta utilizar el 'mapper' tal como está hasta ahora, se puede presentar un problema: qué pasa si las tablas a las que se les hace JOIN tienen nombres de columnas iguales?... Con esto MyBatis no tendría manera de saber a qué atributos corresponde cada una de las columnas. Para resolver esto, si usted hace un query que haga JOIN entre dos o más tablas, siempre ponga un 'alias' con un prefijo el query. Por ejemplo, si se tiene

	```sql	
	select ma.propiedad1, det.propiedad1 ....
	```	

	Se debería cambiar a:

	```sql		
	select ma.propiedad1, det.propiedad1 as detalle_propiedad1
	```

	Y posteriormente, en la 'colección' o en la 'asociación' correspondiente en el 'resultMap', indicar que las propiedades asociadas a ésta serán aquellas que tengan un determinado prefijo:


	```xml
    <resultMap type='Maestro' id='MaestroResult'>
        <id property='propiedad1' column='COLUMNA1'/>
        <result property='propiedad2' column='COLUMNA2'/>
        <result property='propiedad3' column='COLUMNA3'/>        
        <collection property='propiedad4' ofType='Detalle' columnPrefix='detalle_'></collection>
    </resultMap>
	```
	Haga los ajustes necesarios en la consulta y en los 'resultMap' para que no haya inconsistencias de nombres.

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Adicion%20resultMap%20a%20las%20collections.png)

7. Use el programa de prueba suministrado (MyBatisExample) para probar cómo a través del 'mapper' generado por MyBatis, se puede consultar un Cliente. 

	```java	
	...
	SqlSessionFactory sessionfact = getSqlSessionFactory();
	SqlSession sqlss = sessionfact.openSession();
	ClientMapper cm=sqlss.getMapper(ClienteMapper.class);
	System.out.println(cm.consultarClientes()));
	...
	```

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Ejecucion%20MyBatis.png)

+ Comparacion con MySQL

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consulta%20SQL%20MyBatis.png)

## Parte II (para el Miércoles)

2. Verifique el funcionamiento haciendo una consulta a través del 'mapper' desde MyBatisExample.

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/consultarCliente.png)

3. Configure en el XML correspondiente, la operación agregarItemRentadoACliente. Verifique el funcionamiento haciendo una consulta a través del 'mapper' desde MyBatisExample.

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Adicion%20nuevo%20Producto%20Client%20MyBatis.png)

4. Configure en el XML correspondiente (en este caso ItemMapper.xml) la operación 'insertarItem(Item it). Para este tenga en cuenta:
	* Al igual que en en los dos casos anteriores, el query estará basado en los parámetros ingresados (en este caso, un objeto Item). En este caso, al hacer uso de la anotación @Param, la consulta SQL se podrá componer con los atributos de dicho objeto. Por ejemplo, si al paramétro se le da como nombre ("item"): __insertarItem(@Param("item")Item it)__, en el query se podría usar #{item.id}, #{item.nombre}, #{item.descripcion}, etc. Verifique el funcionamiento haciendo una consulta a través del 'mapper' desde MyBatisExample.

+ Modificacion de la interface ItemMapper.java

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/mod%20interface%20ItemMapper.png)

+ Sentencia insertarItem

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/insert%20Item.png)

+ Ejecucion junto a la consulta

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Ejecucion%20MyBatis%20insert%20item.png)
	
5. 	Configure en el XML correspondiente (de nuevo en ItemMapper.xml) las operaciones 'consultarItem(int it) y 'consultarItems()' de ItemMapper. En este caso, tenga adicionalmente en cuenta:
	* Para poder configurar dichas operaciones, se necesita el 'resultMap' definido en ClientMapper. Para evitar tener CODIGO REPETIDO, mueva el resultMap _ItemResult_ de ClienteMapper.xml a ItemMapper.xml. Luego, como dentro de ClienteMapper el resultMap _ItemRentadoResult_ requiere del resultMap antes movido, haga referencia al mismo usando como referencia absoluta en 'namespace' de ItemMapper.xml:

	```xml	
	<resultMap type='ItemRentado' id="ItemRentadoResult">            
		<association ... resultMap='edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper.ItemResult'></association> 
	</resultMap>
	```
+ Cambio de los resultMaps para ambos .xml

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Actualizacion%20resultMaps%20ClienteMapper.png)

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/ResultMaps%20ItemMapper.png)

+ Implementacion de las consultas

+ consultarItem(int it)

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/consultarItem%20sentencia%20ItemMapper.png)

+ consultarItems()

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consultar%20Items%20sentencia.png)
	
	Verifique el funcionamiento haciendo una consulta a través del 'mapper' desde MyBatisExample.

+ Ejecucion consultarItem(int it) mapper

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consultar%20Item.png)

+ Ejecucion consultarItem(int it) MySQL

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consultar%20Item%20MySQL.png)

+ Ejecucion consultarItems() mapper

![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consultar%20Items.png)

+ Ejecucion consultarItems() MySQL
  
![](https://github.com/Pokecris200/Lab7CVDS/blob/master/Recursos/Consultar%20Items%20MySQL.png)

## Bibliografia

+ <https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html>
+ <https://howtodoinjava.com/java/jdbc/jdbc-select-query-example/>
+ <https://www.anerbarrena.com/mysql-sum-9919/>
+ <https://www.w3schools.com/sql/sql_distinct.asp>
+ <http://www.edu4java.com/es/sql/sql5.html>
+ <https://stackoverflow.com/questions/6810375/resultset-convert-to-int-from-query>
+ <https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/>
+ <https://www.anerbarrena.com/mysql-insert-5169/>
+ <https://mybatis.org/mybatis-3/es/>