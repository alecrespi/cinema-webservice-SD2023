# Documentazione del protocollo TCP 
Il protocollo segue una struttura a richiesta e risposta, in cui il client invia una richiesta al server e il server risponde con una risposta appropriata. Sia le richieste che le risposte possono essere formattate come stringhe semplici, a seconda delle esigenze specifiche dell'applicazione.

# Comandi supportati:

# SET 

Il comando SET viene utilizzato per memorizzare una coppia chiave-valore nel database. Per inviare una richiesta SET al database, il server deve inviare una stringa con i seguenti campi:

"command": "SET"
"key": la chiave desiderata
"value": il valore associato alla chiave

Se la richiesta viene elaborata con successo, si restituisce una risposta con il campo "result" impostato su "#result". In caso di errore, possono essere restituiti i seguenti codici di stato HTTP:

400 Bad Request
422 Unprocessable Entity

# GET

Il comando GET viene utilizzato per ottenere il valore associato a una determinata chiave nel database. Per inviare una richiesta GET al database, il server deve inviare una stringa con i seguenti campi:

"command": "GET"
"key": la chiave desiderata


Se la richiesta viene elaborata con successo, si restituisce una risposta con il campo "result" impostato sul valore associato alla chiave. Se la chiave non viene trovata, si deve restituire il codice di stato HTTP 404 Not Found.

# REMOVE

Il comando REMOVE viene utilizzato per rimuovere una chiave e il suo valore associato dal database. Per inviare una richiesta REMOVE al database, il server deve inviare una stringa con i seguenti campi:

"command": "REMOVE"
"key": la chiave da rimuovere

Se la richiesta viene elaborata con successo, si restituisce una risposta con il campo "result" impostato su "#result". Se la chiave non viene trovata, si deve restituire il codice di stato HTTP 404 Not Found.

# BIND 

Il comando BIND viene utilizzato per associare una o più chiavi a un'entità specifica nel database. Per inviare una richiesta BIND al database, il server deve inviare una stringa con i seguenti campi:

"command": "BIND"
"keys": un array di chiavi da associare

Se la richiesta viene elaborata con successo, si restituisce una risposta con il campo "result" impostato su "#result". Se una o tutte le chiavi specificate sono già associate a un'altra entità nel database, si deve restituire il codice di stato HTTP 503 Service Unavailable.

# RELEASE

Il comando RELEASE viene utilizzato per disassociare una o più chiavi da un'entità nel database. Per inviare una richiesta RELEASE al database, il server deve inviare una stringa con i seguenti campi:

"command": "RELEASE"
"keys": un array di chiavi da disassociare

Se la richiesta viene elaborata con successo, si restituisce una risposta con il campo "result" impostato su "#result". Se una o tutte le chiavi specificate non sono associate all'entità nel database, si deve restituire il codice di stato HTTP 404 Not Found o il codice di stato HTTP 503 Service Unavailable se le chiavi sono già state rilasciate.

# Codici di stato HTTP 
200 OK: la richiesta è stata elaborata con successo.
400 Bad Request: la richiesta è malformata o contiene dati non validi.
404 Not Found: la risorsa richiesta non è stata trovata.
422 Unprocessable Entity: la richiesta contiene dati non validi o non conformi alle specifiche del protocollo.
503 Service Unavailable: il server non è in grado di elaborare la richiesta al momento a causa di una condizione temporanea o di sovraccarico.