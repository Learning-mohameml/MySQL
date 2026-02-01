-- Réinitialisation des tables
DROP VIEW Reserve;
DROP VIEW Dispo;

-- On cherche la totalité des réservations de repas et de nuités
CREATE VIEW  Reserve AS 
SELECT emailref, SUM(Reservation_refuge.nbrNuitsReserv) AS nbrNuitsReservTotal,  SUM(Reservation_refuge.nbrRepasReserv) AS nbrRepasReservTotal
FROM Refuge, Reservation_refuge 
WHERE Refuge.emailref = Reservation_refuge.emailref 
GROUP BY emailref;

-- On conserve le nombre de disponiblités pour les repas et les nuités
CREATE VIEW  Dispo AS 
SELECT  Refuge.emailRef, refuge.nbrPlacesRepas - Reserve.nbrRepasReservTotal AS nbRepasDispo, refuge.nbrPlacesDormir - Reserve.nbrNuitsReservTotal AS nbDormirDispo 
FROM Refuge, Reserve
WHERE Refuge.email = Reserve.email; 

-- On récupère le nombre de disponiblités pour les repas
SELECT nbRepasDispo FROM Dispo WHERE Dispo.email=?;

-- On récupère le nombre de disponiblités pour les nuités
SELECT nbDormirDispo FROM Dispo WHERE Dispo.email=?;

-- On vérifie si l'utilisateur a un compte utilisateur
SELECT IDUSER FROM COMPTE_UTILISATEUR WHERE IDUSER=?;

-- On réserve
-- > ajout de la réservation
-- > potentielle réservation de repas
-- > maj du compte utilisateur (calcul du coût total de la réservation nécessaire)
INSERT INTO RESERVATION_REFUGE VALUES (?, ?, ?, ?, ?, ?, ?, ?);
-- ID
-- DATE_RESERVATION
-- HEURE
-- NBRNUITS
-- NBRREPASRESERVE
-- PRIXTOTAL
-- EMAILREF
-- IDUSER
INSERT INTO RESERVE_REPAS(?, ?, ?);
-- IDRESERVREF
-- TYPEREPAS
-- NBREPASRESERVE

SELECT PRIXNUITE FROM REFUGE WHERE EMAILREF=?;
SELECT PRIXREPAS FROM PROPOSE_REPAS WHERE TYPEREPAS=?;

UPDATE COMPTE_UTILISATEUR SET COUTRESERV = COUTRESERV + ? WHERE IDUSER=?;

