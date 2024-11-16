# BATALLA NAVAL

<img src= "https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExMnhyYmMxNmthMTc1NDMwcGpraGUzY2toNnI1ZTJkZHF1MDRhcTlhNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/6BYnBV8FcABBcuKqDi/giphy.gif" alt="py" width = "200px">

**Descripción:** En este mini proyecto se desarrollará el juego llamado "Batalla Naval", el cual 
es un juego de estrategia donde dos jugadores (humano y máquina) compiten por hundir la 
flota del oponente. Cada jugador coloca sus barcos en un tablero de coordenadas y luego 
intenta adivinar la ubicación de los barcos del oponente para hundirlos. Teniendo en cuenta 
que se jugará contra la maquina el juego tendrá 2 tableros de 10x10: 

• **Tablero de posición:** Representa el territorio del jugador humano, en él se distribuye 
su flota antes de comenzar la partida y sólo será de observación. Verá la posición de 
sus barcos y los disparos de su oponente en su territorio, pero no podrá realizar ningún 
cambio ni disparo en él. 

• **Tablero principal:** Representa el territorio del jugador máquina, donde tiene 
desplegada su flota. Será aquí donde se desarrollen los movimientos (disparos) del 
jugador humano tratando de hundir los barcos enemigos. Este tablero aparecerá en 
la pantalla del jugador una vez comience la partida y en él quedarán registrados todos 
sus movimientos, reflejando tanto los disparos al agua como los barcos tocados y 
hundidos en tiempo real. 

Cada jugador tiene una flota de 10 barcos de diferente tamaño, ubicados de manera 
horizontal o vertical, por lo que cada uno ocupará un número determinado de casillas en el 
tablero distribuidos de la siguiente manera: 

**• 1 portaaviones:** ocupa 4 casillas. 

**• 2 submarinos:** ocupan 3 casillas cada uno. 

**• 3 destructores:** ocupan 2 casillas cada uno. 

**• 4 fragatas:** ocupan 1 casilla cada uno.

En este juego se tiene la siguientes terminologías y movimientos: 

**• Agua:** cuando se dispara sobre una casilla donde no está colocado ningún barco 
enemigo. En el tablero principal del jugador aparecerá una X. Pasa el turno a su 
oponente. 

**• Tocado:** cuando se dispara en una casilla en la que está ubicado un barco enemigo 
que ocupa 2 o más casillas, se destruirá sólo una parte del barco. En el tablero del 
jugador aparecerá esa parte del barco con una marca indicativa de que ha sido tocado. 
El jugador vuelve a disparar. 

**• Hundido:** si se dispara en una casilla en la que está ubicado una fragata (1 casilla) u 
otro barco con el resto de las casillas tocadas, el barco se hundirá, es decir, se ha 
eliminado ese barco del juego. Aparecerá en el tablero principal del jugador, el barco 
completo con la marca indicativa de que ha sido hundido. El jugador puede volver a 
disparar, siempre y cuando no haya hundido toda la flota de su enemigo, en cuyo caso 
habrá ganado. 

Para este juego, se debe emular el juego del computador (quien será el oponente). Para ello, 
se podrán aleatoriamente los barcos en el tablero del territorio, siguiendo las reglas y e 
selecciona al azar cada casilla de tiro.
