# DeepWrench42 Installatie Handleiding

De opbouw van deze Readme is als volgt:

·         Inleiding

·         Functionaliteiten

·         Overzicht Project Structuur

·         Gebruikte Technieken & FrameWorks

·         StappenPlan

·         Testen van de Applicatie

·         Api-Documentatie

## Inleiding:

Dit is de backend applicatie genaamd DeepWrench42, een Tool-Control systeem. 

Tool control betekend gereedschaps beheer en dat is exact deze web-api moet doen. De gereedschaps database vormt de kern en de gebruikers registreren het gebruik van gereedschappen. Daarnaast worden ook onderhoudsbeurten en gereedschaps-inspecties vastgelegd en kunnen deze gevolgd worden. 

Zodat alle gereedschappen bruikbaar zijn & tijdig onderhoud kunnen krijgen.  

Functionaliteiten:

·         Gebruikers registeren & inloggen

·         Beheren (bekijken, aanmaken, aanpassen en verwijderen) van:

            o   Vliegtuigen

            o   VliegtuigTypes

            o   MotorTypes

            o   OpslagLocaties

            o   Gereedschappen

            o   Gereedschapskits

            o   Inspecties

·         Gereedschap of gereedschapskit in gebruik nemen of inleveren (check-out & -in)

·         Bekijken van het gereedschap gebruikslog

·         Uitvoeren van gereedschap onderhoud of inspectie

·         Opzoeken van verloop data van inspecties of onderhoud

## Overzicht Project Structuur

Root Folder

├───equipment_images

├───external_application_exports

│   └───pictures

├───src

│   ├───main

│   │   ├───java

│   │   │   └───nl

│   │   │       └───novi

│   │   │           └───deepwrench42

│   │   │               ├───config

│   │   │               ├───controllers

│   │   │               ├───dtos

│   │   │               │   ├───aircraft

│   │   │               │   ├───aircraftType

│   │   │               │   ├───engineType

│   │   │               │   ├───equipment

│   │   │               │   ├───inspection

│   │   │               │   ├───storageLocation

│   │   │               │   ├───tool

│   │   │               │   ├───toolKit

│   │   │               │   ├───toolLog

│   │   │               │   └───user

│   │   │               ├───entities

│   │   │               ├───exceptions

│   │   │               ├───helpers

│   │   │               ├───mappers

│   │   │               ├───repository

## Gebruikte Technieken & FrameWorks

De basis van de applicatie is gebouwd op:

·         Java 25

·         Springboot 3.5.10 i.c.m. Maven

·         PostgreSQL

Hierboven is de absolute basis, vervolgens zijn er de volgende uitbreidingen:

·         KeyCloak voor authenticatie

·         JUnit & WebMvc voor testen

·         Swagger voor dynamische API-documentatie

In de applicatie zijn onder andere de volgende technieken toegepast:

·         REST API

·         Data Transfer Objects (DTO)

·         Validatie

·         Exception Handling

Benodigde software:

·         Java JDK (minimaal versie 25, [link](https://www.oracle.com/java/technologies/downloads/#java26))

·         IntelliJ of andere IDE (deze handleiding is gebaseerd op IntelliJ)

·         PostgreSQL

·         KeyCloak

·         Postman

·         pgAdmin4  

# StappenPlan

Laten we beginnen met een stappenplan voor installatie & gebruik van met PostMan.  Een aantal stappen zullen algemeen zijn, als nodig zijn hier instructies of handleidingen online te vinden.  
Zorg ervoor dat Java JDK, IntelliJ, PostgreSQL, pgAdmin & Postman geinstalleerd zijn.  

1.      Het begint met het downloaden of clonen van de [Repository](https://github.com/JappieSoft/DeepWrench42) volgens jou eigen favoriete methode.

2.      Creëer een nieuwe database in pgAdmin / PostgreSQL.  Onthoud de database naam, PostgreSQL owner/gebruikersnaam & wachtwoord, deze hebben we nodig bij stap 13.

3.      Download [KeyCloak](https://www.keycloak.org/downloads) & pak het bestand uit en zet de root folder vervolgens op een handige locatie staat zoals bijvoorbeeld C:\KeyCloak

4.      Open Powershell & navigeer naar de root map van keycloak. 

Bijvoorbeeld met: cd C:\keycloak

5.      Start KeyCloak door het volgende commando in PowerShell:  
            `bin\kc.bat start-dev --http-port 9090`

Mocht PowerShell een probleem melden met JAVA_HOME, klik dan op deze [link](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/index.html) & volg dan de stappen.  

Als Keycloak is gestart, laat dan vervolgens de PowerShell terminal met rust.

6.      Open een browser en navigeer naar: [http://localhost:9090/](http://localhost:9090/)

7.      Als nodig maak een gebruiksnaam & wachtwoord aan en/of log meteen in bij KeyCloak

8.      Klik het hamburger menu (links boven) & vervolgens op “Manage Realms”.

9.      Klik op “Create Realm” en upload het Realm bestand uit de DeepWrench42 sourcemap / external_application_exports.  
bestandsnaam: `DeepWrench42-realm.json`  
Wacht kort en vervolgens is de rest voor de informatie automatisch ingevuld,  
zorg dat “Enabled” aan staat & druk vervolgens op “Create”

10. Klik weer op het hamburger menu & selecteer clients.

11. Klik op DeepWrenchBackend & vervolgens op Credentials.  
    Laat de webpagina open staan voor de volgende stap.

12. Open het project / de Source Folder in IntelliJ

13. Nu kunnen we de environment variables maken, in de DeepWrench42 root folder  staat “.env.dist”. Kopieer dit bestand en verander de bestandsnaam naar “.env”. Open vervolgens het gecreerde bestand.  

PostgreSQL informatie: onthouden van bij stap 2.  
KeyCloak informatie: Ga terug naar de browser, hier vind je nu alle informatie die je nodig hebt.  
Als het hamburger menu nog open staat zie je dat de Realm naam “DeepWrench42” is.  
Je zit nog op de Client Details pagina en bovenaan zie je het ClientId staan, in ons geval “DeepWrenchBackend”.  
Verder naar beneden op de Credentials pagina, zie je het “Client Secret”.  
Kopier deze door op het kopier icoon te drukken.  
Zet vervolgens al deze informatie op de juiste plek in de .env  
Als het goed is zijn nu alle variabelen ingevuld en sluit/sla de .env op

14. In het project overzicht  open de volgende mappen: DeepWrench42 / src / main / java / nl.novi.deepwrench42 en dubbelklik op DeepWrench42Application & open het bestand.

15. Het kan zijn de IntelliJ de java SDK nog niet heeft gevonden, klik op de SDK melding
    & selecteer de geinstalleerde versie van Java.

16. Als het goed is staan er grone driehoekjes op regel 7 & 9.  
    Klik vervolgens op het driehoekje bij regel 7, nu start de applicatie.  

Mocht de applicatie niet starten, controleer dan de gegevens in de .env file & dat keycloak actief is / aan staat.

Maar we gaan ervanuit dat de applicatie start en snel gereed is voor gebruik.  
(note: er zijn meer manieren om dit te doen.)

17. Open PostMan, zorg dat je ingelogd bent & druk op Ctrl+O.

18. Importeer `DeepWrench42.postman_collection.json`
    uit de DeepWrench42 sourcemap / external_application_exports.

19. Selecteer de DeepWrench42 collection.

20. Voer de GET request “KeyCloak Config Check” uit, deze staat in de KeyCloak folder in Postman.

21. Klik op DeepWrench42 en selecteer de variables tab.

22. Gebruik de ontvangen response samen met de informatie uit de .env file om de variabelen op de variables tab in te vullen.  

baseUrl: [http://localhost:8080](http://localhost:8080)  
auth-url: authorization_endpoint uit de GET Response  
token-url: token_endpoint uit de GET Response  
client-id: CLIENT_ID uit de .env file  
client-secret: CLIENT_SECRET uit de .env file

23. Nu dit allemaal compleet is, klik op de authorization tab & scroll naar beneden

24. Druk op “Get new access token”

25. Gebruik de gebruikers informatie verderop uit deze handleiding.

26. Voltooi de login/authorizatie

27. Test away met alle provided voorbeelden in PostMan.  

# 

# Testen van de Applicatie

## Postman:

De handleiding hierboven is een mooie start om de
applicatie werkend te krijgen. En eindigd met de PostMan applicatie.  
Deze applicatie is nu gereed voor gebruik en deze heeft voor elk endpoint een
test beschikbaar.

Het gebruik hiervan is relatief simpel, open de folder. Selecteer de test en voer hem uit.  

Het gebruik van de verschillende gebruikers geeft verschillende resultaten.

Zie Test Gebruikers sectie voor meer informatie.  

Tools & Tool Kits hebben beide een “Post Picture “ endpoint, voor deze endpoint zijn er een paar foto’s beschikbaar in de “external_application_exports” folder.

Het verdere gebruik van Postman laat ik buiten beschouwing van deze handleiding.  

## IntelliJ:

Een andere manier van testen is met IntelliJ.  
Zorg dat na installatie je KeyCloak aan hebt staan, mocht alles gesloten zijn volg dan stap 4 & 5 van de installatie handleiding.

Start IntelliJ en open het project zoals eerder.

Selecteer de “test” map & dan klik dan met de rechtermuisknop op de “java” folder.

Klik op “Run ‘All Tests’”  
Nu draaien alle geprogrammeerde testen automatisch, dit zijn Unit & Integratie testen.  
De automatische test gebruiken alleen geen autorisaties, daarvoor is PostMan de beste plek.

# 

# Test Gebruikers

| UserName | Password | Role  |
|:-------- | -------- | ----- |
| 516543   | admin    | admin |
| 509704   | bakker   | lead  |
| 523043   | english  | user  |

## Verschil van de Rollen:

De Admin mag alles.

De Leads mogen alles, behalve verwijderen & een
andere users details zoeken of aanpassen.

De User is het meest beperkt & mag voornamelijk GET requests doen op alle endpoints behalve het Tool Log & alle inspecties. Specifieke inspecties mag de user wel opzoeken.

Tot slot heeft iedereen heeft toegang tot de Check-Out & Check-In endpoints.

# 

# API-Documentatie

Voor de api-documentatie is er gebruik gemaakt van Swagger.  
Om Swagger te benaderen moeten KeyCloak en de DeepWrench42 applicatie draaien.  

Als dat niet zo is, voer dan stap 4,5,12, 14 & 16 uit.  
Nu zou de applicatie weer moeten draaien.  

Open een webbrowser en ga naar:  
[Swagger UI](http://localhost:8080/api-documentation.html)  

Recht staat er een “Authorize” knop, gebruik deze om in te loggen met KeyCloak.

Recht staat er een “Authorize” knop, gebruik deze om in te loggen met KeyCloak.  
De specifieke inlog informatie staat al automatisch ingevuld.  
Zodra je op de KeyCloak web pagina beland, moet je inloggen met de gebruiker naar
wens. Maar bedenk wel dat de Authorizaties/Gebruikers Rol effect heeft op de
resultaten.  

Ieder endpoint heeft een beknopte beschrijving en in de details staan mogelijke
user rol restricties. De endpoint zijn uit te proberen door op de “try it out”
knop te drukken en vervolgens de benodigde informatie in te vullen. Door op “execute
te drukken voor je de taak uit en word er een api-call gemaakt.
