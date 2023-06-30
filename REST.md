# Cinema WebService RESTful 


# ENDPOINT
**N.B.:**
ogni endpoint che causa uno status code di fallimento possiede nel proprio responso un oggetto JSON {"message":*status message*}


Gli endpoint sono descritti con la seguente configurazione:

METHOD URL 

DESCRIPTION

SUCCESS: *status code* - *descrizione del responso*

FAULTS: *lista di possibili status code e relative cause*

## Documentazione:
1. **GET /movies/**

    *Descrizione:* Restituisce la lista di film esistenti
   
    *Successo:* 200 - lista di JSON object 'Movie' (vedi 2.)

    *Fallimento:* 404 - Non è presente nessun film memorizzato

3. **GET /movies/{id}**

    *Descrizione:* Restituisce un film esistente che corrisponda a {id}
   
    *Successo:* 200 - { id, title, director, plot coverImageLink }

    *Fallimento:* 404 - Non è presente nessun film memorizzato

   
5. **GET /moviesessions/{id}**
6. **GET /moviesessions?movie={id}**
7. **GET /bookings/{id}**
8. **POST /bookings/**
9. **PUT /bookings/**
10. **DELETE /bookings/{id}**
