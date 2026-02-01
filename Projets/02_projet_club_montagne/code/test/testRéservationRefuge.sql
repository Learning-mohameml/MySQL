-- Test réservation Réfuge :


-- 

CREATE TABLE TestReserveRefuge
(
    id INT PRIMARY KEY ,
    dateReserv DATE ,
    idMembre VARCHAR(50) ,
    heure VARCHAR(50),
    idRefuge VARCHAR(50) , 
    nbrRepas INT , 
    nbrNuits  INT ,
    FOREIGN KEY(idMembre) REFERENCES Membre(email) , 
    FOREIGN KEY(idRefuge) REFERENCES Refuge(email) 

    
);

SELECT * FROM Membre ;


DROP VIEW Reserve ;

CREATE VIEW  Reserve AS 
SELECT email , SUM(reserverefuge.nbrnuits) AS nbDormirReserv ,  SUM(ReserveRefuge.nbrRepas) AS nbRepasReserv
FROM Refuge  , ReserveRefuge 
WHERE Refuge.email = ReserveRefuge.idRefuge 
GROUP BY email;

CREATE VIEW  Dispo AS 
SELECT  Refuge.email , refuge.nbrrepas - Reserve.nbRepasReserv AS nbRepasDispo , refuge.nbrdormir - Reserve.nbDormirReserv AS nbDormirDispo 
FROM Refuge , Reserve
WHERE Refuge.email = Reserve.email ; 



