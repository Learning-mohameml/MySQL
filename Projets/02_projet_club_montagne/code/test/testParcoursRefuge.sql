-- Test de parcour de Refuges : 
DROP TABLE Refuge ;

CREATE TABLE  Refuge
(
email VARCHAR(50) PRIMARY KEY  , 
nom VARCHAR(50) ,
secGeo VARCHAR(50) , 
dateOuv DATE , 
dateFerme DATE , 
nbrRepas INT , 
nbrDormir INT 

);

INSERT INTO Refuge(email , nom , secGeo , dateOuv , dateFerme , nbrRepas , nbrDormir) 
VALUES ('R1@gmail.com' , 'R1' , 'Grenoble' , TO_DATE('01-01-2023','DD-MM-YYYY') ,  TO_DATE('15-01-2023','DD-MM-YYYY') , 50 , 15 );


INSERT INTO Refuge(email , nom , secGeo , dateOuv , dateFerme , nbrRepas , nbrDormir) 
VALUES ('R2@gmail.com' , 'R2' , 'Grenoble' , TO_DATE('01-02-2023','DD-MM-YYYY') ,  TO_DATE('20-02-2023','DD-MM-YYYY') , 60 , 40 );



INSERT INTO Refuge(email , nom , secGeo , dateOuv , dateFerme , nbrRepas , nbrDormir) 
VALUES ('R3@gmail.com' , 'R3' , 'Grenoble' , TO_DATE('02-03-2023','DD-MM-YYYY') ,  TO_DATE('26-03-2023','DD-MM-YYYY') , 30 , 20 );


COMMIT ;

SELECT * FROM Refuge ;


-- TABLE 

DROP TABLE ReserveRefuge ; 
CREATE TABLE ReserveRefuge
(
    id INT PRIMARY KEY ,
    dateReserv DATE ,
    heure VARCHAR(50),
    idRefuge VARCHAR(50) , 
    nbrRepas INT , 
    nbrNuits  INT ,
    
    FOREIGN KEY(idRefuge) REFERENCES Refuge(email) , 

    
);

SELECT * FROM Membre ;

INSERT INTO ReserveRefuge(id , dateReserv , heure , idRefuge , nbrRepas , nbrNuits) 
VALUES
(1 ,  TO_DATE('01-01-2023','DD-MM-YYYY') , '5h' , 'R1@gmail.com' , 10 , 4 );  

INSERT INTO ReserveRefuge(id , dateReserv , heure , idRefuge , nbrRepas , nbrNuits) 
VALUES
(2 ,  TO_DATE('02-01-2023','DD-MM-YYYY') , '4h' , 'R1@gmail.com' , 20 , 10 );   


INSERT INTO ReserveRefuge(id , dateReserv , heure , idRefuge , nbrRepas , nbrNuits) 
VALUES
(3 ,  TO_DATE('01-02-2023','DD-MM-YYYY') , '1h' , 'R2@gmail.com' , 30 , 20 );  


INSERT INTO ReserveRefuge(id , dateReserv , heure , idRefuge , nbrRepas , nbrNuits) 
VALUES
(4 ,  TO_DATE('02-01-2023','DD-MM-YYYY') , '12h' , 'R3@gmail.com' , 10 , 5 );

COMMIT ;


SELECT * FROM ReserveRefuge;

SELECT 0  FROM Membre ;


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











