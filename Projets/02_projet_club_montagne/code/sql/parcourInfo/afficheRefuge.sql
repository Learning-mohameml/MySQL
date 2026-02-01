
---- Réponse : Parcour des Refuges :
DROP VIEW Reserve ;

CREATE VIEW  Reserve AS 
SELECT email , SUM(reserverefuge.nbrnuits) AS nbDormirReserv ,  SUM(ReserveRefuge.nbrRepas) AS nbRepasReserv
FROM Refuge  , ReserveRefuge 
WHERE Refuge.email = ReserveRefuge.idRefuge 
GROUP BY email;

SELECT  nom , dateOuv , dateFerme , refuge.nbrrepas - Reserve.nbRepasReserv AS nbRepasDispo , refuge.nbrdormir - Reserve.nbDormirReserv AS nbDormirDispo 
FROM Refuge , Reserve
WHERE Refuge.email = Reserve.email ; 