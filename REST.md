# Cinema WebService RESTful 


# ENDPOINT
**Considerazioni:**
Ogni endpoint che causa uno status code di fallimento possiede nel proprio responso un oggetto JSON {"message":*status message*}


Gli endpoint sono descritti con la seguente configurazione:

METHOD URL 

DESCRIPTION

BODY: *eventuale body di richiesta*

SUCCESS: *status code* - *descrizione del responso*

FAULTS: *lista di possibili status code e relative cause*

## Documentazione:
1. **GET /movies/**

    *Descrizione:* Restituisce la lista di film esistenti
   
    *Successo:* 200 - lista di JSON object 'Movie' (vedi 2.)

    *Fallimento:* 
    * 404 - Non è presente alcun film memorizzato

3. **GET /movies/{id}**

    *Descrizione:* Restituisce un film esistente che corrisponda a {id}
   
    *Successo:* 200 - { id, title, director, plot, coverImageLink }

    *Fallimento:* 
    * 404 - Non esiste alcun film esistente che corrisponda a {id}
       
5. **GET /moviesessions/{id}**

    *Descrizione:* Restituisce una proiezione che corrisponda a {id}
   
    *Successo:* 200 - { id, movie, room, startTime, endTime, seats }

    *Fallimento:* 
    * 404 - Non esiste alcuna proiezione che corrisponda a {id}

6. **GET /moviesessions?movie={id}**

    *Descrizione:* Restituisce una lista di proiezioni il cui film in proiettato corrisponda a {movie} (dove movie è l'id del film)
   
    *Successo:* 200 - lista di JSON object 'MovieSession' (vedi 5.) 

    *Fallimento:*
    * 400 - non è stato specificato il parametro {movie}
    * 404 - Non esiste alcuna proiezione che corrisponda a {id}

7. **GET /bookings/{code}**

    *Descrizione:* Restituisce una prenotazione che corrisponda a {code}
   
    *Successo:* 200 - { code, moviesession, seats }

    *Fallimento:* 
    * 404 - Non esiste alcuna prenotazione che corrisponda a {code}

8. **POST /bookings/**

    *Descrizione:* Crea una nuova prentazione 

    *Body*: { moviesession, seats }
   
    *Successo:* 201 - { code, moviesession, seats }

    *Fallimento:* 
    * 400 - Il Body non è un oggetto JSON
    * 404 - Non esiste alcuna proiezione che corrisponda a 'moviesession'
    * 409 - I posti che si intendono prenotare 'seats' sono già stati riservati
    * 503 - La porzione di dati che si intende modificare è stata sovrascritta, si consiglia un ricalcolo della richiesta

9. **PUT /bookings/**

    *Descrizione:* Modifica una nuova prentazione 

    *Body*: { code, moviesession, seats }
   
    *Successo:* 204 - *No content*

    *Fallimento:* 
    * 400 - Il Body non è un oggetto JSON
    * 404 - Non esiste alcuna proiezione che corrisponda a 'moviesession' oppure non esiste alcuna prenotazione che corrisponda a 'code' (dove 'code' è il codice della prenotazione)
    * 409 - I nuovi posti che si intendono prenotare 'seats' sono già stati riservati
    * 503 - La porzione di dati che si intende modificare è stata sovrascritta, si consiglia un ricalcolo della richiesta

10. **DELETE /bookings/{code}**

    *Descrizione:* Cancella una nuova prentazione, restituisce la prenotazione cancellata
   
    *Successo:* 200 - { code, moviesession, seats }

    *Fallimento:* 
    * 404 - Nnon esiste alcuna prenotazione che corrisponda a 'code' (dove 'code' è il codice della prenotazione)

