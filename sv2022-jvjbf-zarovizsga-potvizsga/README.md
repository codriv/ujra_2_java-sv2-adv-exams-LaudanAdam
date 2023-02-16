# Záróvizsga Pótvizsga - Kórházak api

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

A meglévő exams repositoryba dolgozz!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek). GroupId: `org.training360`,
artifactId: `sv2022-jvjbf-zarovizsga-potvizsga`. Csomagnév: `org.training360.sv2022jvjbfzarovizsgapotvizsga`.

Először másold át magadhoz a `pom.xml`-t és a teszteseteket, majd commitolj azonnal!
A vizsga végéig bárhányszor commitolhatsz.

Ha letelik az idő, mindenképp pusholj, akkor is, ha nem vagy kész!

## Alkalmazás

Hozz létre egy alkalmazást, amivel kórházakat és betegeket lehet adminisztrálni.

## Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal, Java Spring backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Flyway
* Üzleti logika réteg megvalósítása `@Service` osztályokkal
* Controller réteg megvalósítása, RESTful API implementálására.
* Hibakezelés, validáció
* Hozz létre egy `Dockerfile`-t!


## Általános követelmények (12 pont)

- Alkalmazás szerkezeti felépítése, következetes mappa és package szerkezet - 2 pont
- A három réteg létrehozása megfelelő, indítható, működő Spring Boot alkalmazás - 3 pont
- Dockerfile és migrációs fájlok megléte, helyessége - 2 pont
- Clean code - 5 pont


## Az adatbázisréteg elkészítése  (12 pont)

A feladatban két entitást kell elkészítened, egyiket `Hospital`, a másikat `Patient` néven. Fontos, hogy egy kórháznak
több páciense is lehet, de egy páciens csak egy kórházban tartózkodhat. A kapcsolat kétirányú legyen! <br>

Kórház adatai:

* `id` (`Long`)
* `hospitalName` (`String`)

Páciens adatai:

* `id` (`Long`)
* `name` (`String`)
* `registrationDate` (`LocalDate` - felvétel dátuma)  
* `hospitalWard` (`HospitalWard` enum: `MEDICAL`, `TRAUMATOLOGY`, `RHEUMATOLOGY`, `ORTHOPAEDY`; különböző kórházi osztályokat jelöl)



Adatbázist indíthatsz a következő Docker paranccsal:

```shell
docker run -d -e MYSQL_DATABASE=hospitals -e MYSQL_USER=hospital -e MYSQL_PASSWORD=hospital -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 --name hospitals-mariadb mariadb
```

### Részpontszámok

- Az entitások létrehozása helyes, `JPA` szabványnak megfelelő - 8 pont
- Tábla és oszlopnevek megfelelők - 4 pont

## Kórház és beteg mentése (28 pont)

### `POST /api/hospitals`

A HTTP kérés törzsében egy kórház nevét várjuk. Az azonosítót az adatbázis osztja ki, míg a betegek listája
kezdetben üres.<br>
Validálás:

- A név nem lehet üres és nem tartalmazhat csak whitespace karaktereket

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet (a pontos
hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Sikeres mentés esetén küldd vissza az elmentett kórház összes adatát (id-val és betegekkel együtt), és 201-es
kódot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Az adatok elmentésre kerülnek - 3 pont
* Validálás és hibakezelés - 3 pont
* A válasz tartalmazza a megfelelő adatokat - 3 pont

### `POST api/hospitals/{id}/patients`

A kórház azonosítója az URL-ben érkezik.

A HTTP kérés törzse:

- név (nem lehet üres)
- felvétel dátuma (Csak múltbéli vagy jelen dátumot tartalmazhat)
- kórházi osztály

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet
(a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Ha nem megfelelő a kórház azonosítója, 404-es státuszkóddal térj vissza és megfelelő hibaüzenettel.

Sikeres mentés esetén küldd vissza a beteg adatait (a kórházat nem kell) és 201-es státuszkódot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* Az adatok elmentésre kerülnek, ha megfelelők - 3 pont
* Egyszerű mezők validálása és hibakezelése - 4 pont
* Nem létező kórház hibájának kezelése - 4 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

## Kórházak lekérdezése (17 pont)

### `GET /api/hospitals/{id}/patients`

Lehessen az összes pácienset lekérdezni a végponton, alapértelmezetten a páciens neve alapján ábécé sorrendben!
Opcionálisan query stringként lehessen megadni egy kórházi osztályt, ebben az esetben csak az adott
osztály betegeit kérdezzük le, szintén ábécé sorrendben.

Ha nem megfelelő a kórház azonosítója, 404-es státuszkóddal térj vissza és megfelelő hibaüzenettel.

Figyeljünk arra, hogy lehet, hogy a kórház létezik, csak nincsenek ott betegek, vagy egy adott osztályon nincs beteg. Ekkor
üres listával térjünk vissza. 

Sikeres keresés esetén betegek listájával térj vissza, a kórház továbbra sem kell!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Nem létező kórház hibájának kezelése - 4 pont  
* Query paraméter nélkül az összes beteg lekérdezésre kerül ábécé sorrendben - 4 pont
* A query paraméter esetén megfelelő szűrés történik - 3 pont
* A válasz tartalmazza a megfelelő adatokat - 3 pont

## Beteg meggyógyul (11 pont)

### `DELETE /api/hospitals/{id}/patients/{ptId}`

Ha egy beteg meggyógyul, akkor töröljük a kórház betegei közül. 

Ha valamelyik id nem megfelelő, térjünk vissza 404-es státuszkóddal és hibaüzenettel. Figyeljünk arra, hogy a keresett beteg a keresett kórházban tartózkodjon,
ha nem így lenne, szintén 404-es státuskóddal térjünk vissza. 

A törlés után a HTTP törzsben adatokat nem várunk vissza, a státusz is ennek megfelelő.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- Megfelelő hibakezelés - 4 pont
- Törlés megvalósul - 2 pont
- Megfelelő státusszal tér vissza - 2 pont




