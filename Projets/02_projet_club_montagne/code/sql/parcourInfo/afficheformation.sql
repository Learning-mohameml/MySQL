------------------------ Parcoures des formations : nbPlaces && Activités  --------------------------------------------

-- nombre de réservation pour chaque formtion : ----> nbPlaceReserv
--DROP VIEW  nbPlaceReserv ;

CREATE VIEW nbPlaceReserv AS
SELECT Formation.ANNEeFORM , Formation.IdFORM ,nom , COUNT(*) as nombreRéservation 
FROM RESERVATION_FORMATION , Formation 
WHERE 
Formation.ANNEeFORM = RESERVATION_FORMATION.annee  AND Formation.IdFORM= RESERVATION_FORMATION.numero
GROUP BY Formation.ANNEeFORM , Formation.ANNEeFORM ,nom ;

-- SELECT * FROM nbPlaceReserv 
-- nombre de places disponibles pour chaque formation : ---->  nbRestantFormation
-- DROP VIEW  nbRestantFormation ; 

CREATE VIEW nbRestantFormation AS
SELECT Formation.ANNEeFORM , Formation.IdFORM ,Formation.nom , Formation.NBrPLACE - nbPlaceReserv.nombreRéservation AS nbrRestant
FROM Formation , nbPlaceReserv
WHERE Formation.ANNEeFORM = nbPlaceReserv.ANNEeFORM AND Formation.IdFORM =  nbPlaceReserv.IdFORM 
;

-- SELECT * FROM nbRestantFormation 
 
--- 

SELECT  DISTINCT Formation.ANNEeFORM , Formation.IdFORM , Formation.nom , NOmACTIVITE  , DATeDEM , DUREE  , nbrRestant
FROM Formation ,PROPOSE_ACTIVITE_FORM , nbRestantFormation
WHERE 
Formation.IdFORM = PROPOSE_ACTIVITE_FORM.numero AND Formation.ANNEeFORM = PROPOSE_ACTIVITE_FORM.annee AND 
Formation.IdFORM = nbRestantFormation.IdFORM AND Formation.ANNEeFORM = nbRestantFormation.ANNEeFORM AND 
nbRestantFormation.IdFORM = PROPOSE_ACTIVITE_FORM.IdFORM AND nbRestantFormation.ANNEeFORM = PROPOSE_ACTIVITE_FORM.annee 
ORDER BY Formation.ANNEeFORM , Formation.IdFORM , nom;