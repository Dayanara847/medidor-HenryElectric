<p align='left'>
    <img src='https://static.wixstatic.com/media/85087f_0d84cbeaeb824fca8f7ff18d7c9eaafd~mv2.png/v1/fill/w_160,h_30,al_c,q_85,usm_0.66_1.00_0.01/Logo_completo_Color_1PNG.webp' </img>
</p>

# Java Henry electric light company.
![alt text](two-men-in-uniform-working.jpg "Title")

Henry Luz es una compañia de distribucion de energia electrica con sede en las siguientes ciudades, Yugoslavia y
Checoslovaquia

Nuestra empresa se dedica a la facturación y mantenimiento de los sistemas eléctricos. Con este fin ha instalado un
aparato que nos va enviando información del consumo eléctrico de cada domicilio cada 5 minutos mediante la tecnología
4G, a un servicio web que debemos desarrollar.

¿Cómo funciona esto?

Nuestro medidor arranca en 0 cuando se instala (Tomemos este momento como 01/01/2021 00:00:00). En los próximos cinco
minutos, en ese domicilio se van a consumir 0.5 Kw/h, por lo tanto nuestro medidor nos enviara la siguiente
información:

| Fecha y Hora | Medición |
| :---: | :---: | 
| 01/01/2021 00:00:00 | 0.0 |
| 01/01/2021 00:05:00 | 0.5 |

Si nuestro domicilio consume 1.0 Kw/h entre 01/01/2021 00:05:00 y 01/01/2021 00:10:00 , a está última hora enviará la
siguiente información:

| Fecha y Hora | Medición |
| :---: | :---: |
| 01/01/2021 00:10:00 | 1.5 |

La medición se va incrementando conforme el domicilio va consumiendo energía. Cada uno de los medidores va enviando
información cada 5 minutos del número de la medición, pero no el consumo . El consumo debe ser calculado por nuestro Web
Service en base a las distintas mediciones enviadas por cada medidor. Cada domicilio puede tener un solo medidor.

El medidor se identifica en el sistema con un número de serie que no puede repetirse. A su vez de cada medidor debemos
tener que marca y que modelo es.

Cada domicilio tiene un solo medidor y tiene asociada una tarifa por cada Kwh consumidos . Estas tarifas deben ser
configuradas por nuestro servicio Web.

Cada primer día de mes a las 00:00 se corre automáticamente un proceso automático que nos genera la facturación de cada
uno de los domicilios. Para saber cuánto debe pagar el cliente, se toman en cuenta todas aquellas mediciones que no han
sido facturadas hasta el momento, es decir que debemos de alguna manera saber qué mediciones han sido tomadas en cuenta
a la hora de generar una factura de consumo.

Estas facturas deben tener la siguiente información:

* Cliente
* Domicilio
* Numero de medidor
* Medición inicial
* Medición final
* Consumo total en Kwh
* Fecha y hora medición inicial
* Fecha y hora medición final
* Tipo de tarifa
* Total a pagar (Consumo * Tarifa)

Henry Electric Light Company ha contratado los servicios de dos empresas expertas en la realización de
plataformas web. Con el fin de distribuir la carga de trabajo entre las dos empresas, una
de ellas se encargará del desarrollo Web y Aplicación Celular, mientras a nuestra
compañía se encargará del desarrollo de una API REST para el mantenimiento de la información de consumos y mediciones (llamado a partir de ahora como Back Office) y también para dar soporte a la Aplicación Web y Aplicación Android desde donde cada cliente podrá ingresar y consultar información.

El portal de usuarios y aplicacion Android deberá permitir :

* Consulta de facturas
* Consulta de consumo por rango de fechas (el usuario va a ingresar un rango de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero)
* Consulta de mediciones por rango de fechas 

Desde el sistema de Backoffice, se debe permitir:

* Alta, baja y modificación de domicilios, medidores y clientes.
* Consulta de facturas impagas por cliente.
* Consulta 10 clientes más consumidores
* Consulta de mediciones de un domicilio en particular



Recuerden cada medidor nos enviará Número de medidor, medición, fecha y hora de la medición. 

Para la completitud del TP se considera :
* Seguir los fundamentos de API REST.
* División en capas mostradas en clase (Controller, Service, Persistence/Dao).
* Completar la funcionalidad del mismo.
* Recuerden aplicar los conceptos aprendidos durante el curso, los buenos usos de la herencia,polimorfismo, sobrecarga de los metodos,
utilizar el circuit breaker en caso que el medidor no pueda enviar la información al servidor establecer un fallback que almacene momentaneamente el consumo hasta que se pueda comunicar con el servicio.
  


