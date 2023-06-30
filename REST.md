# Cinema WebService RESTful 2022 - 2023

Documentazione delle API REST

**Considerazioni:**
Ogni endpoint che causa uno status code di fallimento possiede nel proprio responso un oggetto JSON {"message":*status message*}

## DOCUMENTAZIONE:

## `/movies`

### GET

**Descrizione**: Restituisce la lista di film esistenti

**Parametri**: in questo caso non sono previsti

**Body richiesta**: vuoto

**Risposta**: in caso di successo un oggetto JSON con i campi `id`, `title`, `director`, `plot`, `coverImageLink`

**Codici di stato restituiti**: 
* 200 OK
* 404 Not Found


## `/movies/{id}`

### GET

**Descrizione**: restituisce un film esistente che corrisponda a {id}

**Parametri**: un parametro nel percorso `id` che rappresenta l'identificativo del film da restituire

**Body richiesta**: vuoto

**Risposta**: in caso di successo un oggetto JSON che rappresenta tutte le proiezioni disponibili di quel film. Ogni proiezione ha i seguenti campi: `id`, `movie`, `startTime`, `endTime`, `room`, `seats`

**Codici di stato restituiti**: 
* 200 OK
* 404 Not Found


## `/moviesessions/{id}`  

## GET

**Descrizione**: restituisce una proiezione che corrisponda a {id}

**Parametri**: un parametro nel percorso `id` che restituisce che rappresenta l'identificativo di una proiezione

**Body richiesta**: vuoto

**Risposta**: In caso di successo un oggetto json che rappresenta una proiezione. La proiezione ha i seguenti campi: `id`, `movie`, `startTime`, `endTime`, `room`, `seats`

**codici di stato restituiti**: 
* 200 OK
* 404 Not Found


## `/moviessessions?movie={id}`

## GET

**Descrizione**: restituisce una lista di proiezioni il cui film proiettato corrisponda a {movie} (dove movie è l'id del film) 

**Parametri**: un parametro `id` che rappresenta un film.

**Body richiesta**: vuoto

**Risposta**: in caso di successo un oggetto JSON che rappresenta tutte le proiezioni disponibili di quel film. Ogni proiezione ha i seguenti campi: `id`, `movie`, `startTime`, `endTime`, `room`, `seats`

**Codici di stato restituiti**:
* 200 OK
* 400 Bad Request
* 404 Not Found


## `/bookings`

## POST 

**Descrizione**: crea una nuova prenotazione

**Parametri**: ci deve essere l'header `Content-Type: application/json`.

**Body richiesta**: rappresentazione in formato JSON della prenotazione con i campi `moviesession` e `seats`.

**Risposta**: In caso di successo la risorsa è stata creata 

**Codici di stato restituiti**:
* 201 Created
* 400 Bad Request
* 404 Not Found
* 409 Conflict
* 503 Unprocessable Entity

## PUT

**Descrizione**: Modifica una nuova presentazione

**Body richiesta**: rappresentazione in formato JSON della prenotazione con i campi `moviesession` e `seats` e `code`.

**Risposta**: in caso di successo la risorsa è atata aggiornata

**Codici di stato restituiti**:
* 201 Created
* 400 Bad Request
* 404 Not Found
* 409 Conflict
* 503 Unprocessable Entity


## `/bookings/{code}`

### GET 

**Descrizione**: Restituisce una prenotazione che corrisponda a {code}

**Parametri**: un parametro `code` che rappresenta il codice di una prenotazine

**Body richiesta**: vuoto

**Risposta**: in caso di successo un oggetto JSON che rappresenta una prenotazione

**codici di stato restituiti**:
* 200 OK
* 404 not found

### DELETE

**Descrizione**: Cancella una nuova prentazione, restituisce la prenotazione cancellata

**parametri**: un parametro `code` che rappresenta il codice di una prenotazine

**Body richiesta**: rappresentazione in formato JSON della prenotazione con i campi `moviesession` e `seats` e `code`.

**Risposta**: in caso di successo la prenotazione viene cancellata

**codici di stato restituiti**:
* 200 OK
* 404 not found

