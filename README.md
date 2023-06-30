# Collaborators

879222 - Ferrara Luigi 

879186 - Crespi Alessandro 

# Sistema di Gestione delle Prenotazioni al Cinema

Questo repository di GitHub contiene un progetto universitario sviluppato come parte del corso di Sistemi Distribuiti. Il progetto si concentra sulla realizzazione di un'applicazione di servizio web per un Sistema di Gestione delle Prenotazioni al Cinema. È composto da una pagina frontend in HTML/Javascript, un server REST servlet Java Jersey e uno strato di server secondario che funge da archivio dati in modo non persistente basato su REDIS.

## Componenti

**Frontend**: Il frontend del servizio web è realizzato utilizzando HTML e Javascript. Fornisce un'interfaccia utente intuitiva per consentire ai clienti di sfogliare i film, visualizzare gli orari e effettuare prenotazioni.

**Server REST Java Jersey**: Questo server funge da backend principale del servizio web. Gestisce le richieste HTTP in ingresso provenienti dal frontend e esegue le operazioni necessarie secondo l'architettura REST per gestire le prenotazioni al cinema. Il server è costruito utilizzando il framework Jakarta EE JAX-RS, garantendo scalabilità e affidabilità.

**Server Database**: Questo strato funge da database general-porpuses memorizzando i dati ponendo particolare attenzione sulla sincronizzazione. Sebbene i dati non siano conservati in un sistema di gestione di database tradizionale, l'oggetto JSON svolge la funzione di contenere informazioni relative al cinema, come gli orari dei film, la disponibilità dei posti e le prenotazioni dei clienti.

## Comunicazione tra i Componenti del Backend

La connessione tra il "server DB" e il server REST viene stabilita utilizzando un protocollo TCP/IP personalizzato. Questo protocollo è progettato per facilitare una trasformazione ed uno scambio efficiente dei dati tra i due server, consentendo una comunicazione senza problemi. Il Protocollo è basato su REDIS (https://redis.io)

## Compilazione ed esecuzione

Il server Web e il database sono dei progetti Java che utilizano Maven per gestire le dipendenze, la compilazione e l'esecuzione. È necessario eseguire in seguente i seguenti obiettivi per compilare ed eseguire: `clean`, che rimuove la cartella `target`, `compile` per compilare e `exec:java` per avviare il
componente.

I tre obiettivi possono essere eseguiti insieme in una sola riga di comando da terminale tramite `./mvnw clean compile exec:java` per Linux/Mac e `mvnw.cmd clean compile exec:java` per Windows. L'unico requisito è un'istallazione di Java (almeno la versione 11), verificando che la variabile `JAVA_PATH` sia correttamente configurata.

Il client Web è composto da più file HTML, tra i quali: `index.html`,`movie.html`,`movieSession.html`,`bookingManagement.html`. È importante disabilitare CORS, come mostrato nel laboratorio 8 su JavaScript (AJAX).

## Porte e indirizzi

Il server Web si pone in ascolto all'indirizzo `localhost` alla porta `8080`. Il database si pone in ascolto allo stesso indirizzo del server Web ma alla porta `3030`.