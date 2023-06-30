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

## Obiettivo del Progetto

L'obiettivo principale di questo progetto è sviluppare un sistema di gestione delle prenotazioni al cinema semplice ma funzionale. Gli utenti possono sfogliare i film disponibili, controllare gli orari, selezionare i posti e effettuare prenotazioni. Il progetto mira a dimostrare conoscenza e comprensione dei sistemi distribuiti, nonché competenza nello sviluppo web, nella programmazione lato server e nei protocolli di rete personalizzati.

## Informazioni sul Repository

Questo repository funge da base di codice completo per l'intero progetto. Fornisce una base per ulteriori miglioramenti e aggiornamenti, consentendo agli sviluppatori e ai contributori di ampliare la funzionalità e la scalabilità del sistema di gestione delle prenotazioni al cinema.

**Scadenza**: 30 giugno 2023

**Nota**: Poiché questo progetto è ancora in corso e il codice è in fase di sviluppo attivo, si prega di considerare questo repository come un lavoro in corso.

**Licenza**: Dopo la scadenza del periodo, il progetto verrà chiuso e distribuito secondo i termini della licenza Apache 2.0.

**Valutazione**: --non ancora

 valutato--

