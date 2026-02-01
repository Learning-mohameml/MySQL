-- Test pour le parcour de matériels  :

DROP TABLE LOT ;

CREATE TABLE  Lot
(
    marque VARCHAR(50),
    modele VARCHAR(50) ,
    anneeAchat VARCHAR(50) ,
    nbPieces INT , 
    Text VARCHAR(100) ,
    prixPerte INT , 
    anneePeremption INT , 
    sousCategorie VARCHAR(50) , 
    
    
    

    PRIMARY KEY(marque , modele , anneeAchat) , 
    
    FOREIGN KEY(sousCategorie) REFERENCES Arbre(sousCategorie) 
    
);

INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption , sousCategorie) VALUES ('Marque1' , 'Modéle1' , 2022 , 20 , 'rien' , 120 , 2025  ,'AB');
INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) VALUES ('Marque2' , 'Modéle1' , 2022 , 20 , 'rien' , 120 , 2025 , 'mousqueton symétrique');
INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) VALUES ('Marque3' , 'Modéle2' , 2023 , 20 , 'rien' , 120 , 2025 , 'mousqueton HMS');
INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) VALUES ('Marque4' , 'Modéle2' , 2024 , 20 , 'rien' , 120 , 2025 , 'mousqueton HMS');
INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) VALUES ('Marque1' , 'Modéle3' , 2020 , 20 , 'rien' , 120 , 2025 , 'AB');
INSERT INTO Lot(marque , modele , anneeAchat , nbPieces , Text , prixPerte , anneePeremption, sousCategorie) VALUES ('Marque5' , 'Modéle2' , 2019 , 20 , 'rien' , 120 , 2025 , 'mousqueton symétrique');




COMMIT ;

SELECT * FROM Lot ;



-- DROP TABLE AUneCategorie;

--CREATE TABLE AUneCategorie 
--(
--    marque VARCHAR(50),
--    modele VARCHAR(50) ,
--    anneeAchat VARCHAR(50) ,
    
--    souscategorie VARCHAR(50) ,
    
--    FOREIGN KEY(marque , modele , anneeAchat) REFERENCES Lot(marque , modele , anneeAchat) ,
--    FOREIGN KEY(souscategorie) REFERENCES Arbre(sousCategorie),
    
--    PRIMARY KEY(marque , modele , anneeAchat , souscategorie)
--);

DROP TABLE  Arbre ;

CREATE  TABLE Arbre
(
    sousCategorie VARCHAR(50)  , 
    
    PRIMARY KEY(sousCategorie)
);

INSERT INTO Arbre(souscategorie) VALUES ('EPI');  
INSERT INTO Arbre(souscategorie) VALUES ('mousqueton');  
INSERT INTO Arbre(souscategorie) VALUES ('AB');  
INSERT INTO Arbre(souscategorie) VALUES ('mousqueton symétrique');  
INSERT INTO Arbre(souscategorie) VALUES ('mousqueton HMS');  

SELECT * FROM Arbre ;


-- table estParentDe : 

DROP TABLE EstParentDe ; 

CREATE TABLE EstParentDe 
(
    sousCategorie1 VARCHAR(50) ,
    sousCategorie2 VARCHAR(50) ,
    PRIMARY KEY(sousCategorie1 , sousCategorie2) ,
    
    FOREIGN KEY(sousCategorie1) REFERENCES Arbre(sousCategorie), 
    FOREIGN KEY(sousCategorie2) REFERENCES Arbre(sousCategorie)

);

INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('mousqueton HMS' , 'mousqueton') ;
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('mousqueton symétrique' , 'mousqueton') ;
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('mousqueton' , 'EPI') ;
INSERT INTO EstParentDe(sousCategorie1 , sousCategorie2) VALUES ('AB' , 'EPI') ;

COMMIT ;

SELECT * FROM EstParentDe ;




-- Table réservation Matériels : 



CREATE TABLE LOCATION_MATERIEL 
(
   IdLoc INT PRIMARY KEY,
   IdADHERENT INT,
   NbPIECEsRESERV INT,
   DATeRECUP DATE,
   DATeRETOUR DATE, 
   NbPIECEsPERDUES INT , 
   marque VARCHAR(50) , 
   modele VARCHAR(50) , 
   anneeachat VARCHAR(50) , 
   -- MARQUE REFERENCES LOT(MARQUE), -- Cardinalité 1..1
   -- MODELE REFERENCES LOT(MODELE), -- Cardinalité 1..1
   --   ANNEeACHAT REFERENCES LOT(ANNEeACHAT), -- Cardinalité 1..1
   IdUSER REFERENCES COMPTE_UTILISATEUR(IdUSER) ,  -- Cardinalité 1..1
    
    FOREIGN KEY(marque , modele , Anneeachat) REFERENCES Lot(marque , modele , anneeachat)
);


CREATE TABLE COMPTE_UTILISATEUR (
   IdUSER INT PRIMARY KEY,
   COUtRESERV DECIMAL,
   SOMMeMATERIElABIME DECIMAL,
   SOMMeREMBOURSEE DECIMAL
);













-- parcour des matérieles :

SELECT * FROM estParentDe ;

SELECT * FROM estparentDe WHERE sousCategorie2='EPI';


SELECT * FROM estparentDe WHERE sousCategorie2='mousqueton';

SELECT * FROM estparentDe WHERE sousCategorie2='mousqueton HMS';


SELECT * FROM Lot WHERE souscategorie='AB';




-- 



DROP VIEW matLouee ; 


CREATE VIEW matLouee AS 
SELECT marque , modele , anneeAchat , SUM(nbrPiecesLouees) as nbLouee FROM Location_MATERIEL , Lot   
WHERE Lot.marque =  Location_MATERIEL.marque 
AND Lot.modele = Location_MATERIEL.modele 
AND Lot.anneeAchat = Location_MATERIEL.anneeAchat
GROUP BY marque , modele , anneeAchat  ;



SELECT Lot.marque , Lot.modele , Lot.anneeAchat , Lot.nbPieces - matLouee.nbLouee FROM  matLouee , Lot   
WHERE Lot.marque =  matLouee.marque 
AND Lot.modele = matLouee.modele 
AND Lot.anneeAchat = matLouee.anneeAchat
GROUP BY marque , modele , anneeAchat ; 





-- parcour paar Activités : 

SELECT * FROM Activite;


DROP TABLE CompatibleAvecActivité ; 

CREATE TABLE CompatibleAvecActivité
(
    nomActivite VARCHAR(50) , 
    marque VARCHAR(50),
    modele VARCHAR(50) ,
    anneeAchat VARCHAR(50) , 
    
    PRIMARY KEY(nomActivite , marque , modele , anneeAchat) , 
    
    FOREIGN KEY(nomActivite) REFERENCES Activite(nomActivité) , 
    
    FOREIGN KEY(marque , modele , anneeAchat) REFERENCES Lot(marque , modele , anneeAchat) 
    
);


 -- Lot(Marque1 , modele 1 , 2022 )  ---> est compatible avce AC1 , AC2 , AC3 :
 INSERT INTO  CompatibleAvecActivité(nomActivite , marque , modele , anneeAchat) VALUES ('Ac1' ,'Marque1' , 'Modéle1' , 2022  ) ; 
INSERT INTO  CompatibleAvecActivité(nomActivite , marque , modele , anneeAchat) VALUES ('Ac2' ,'Marque1' , 'Modéle1' , 2022  ) ; 
INSERT INTO  CompatibleAvecActivité(nomActivite , marque , modele , anneeAchat) VALUES ('Ac3' ,'Marque1' , 'Modéle1' , 2022  ) ; 


 -- Lot(Marque2 , modele 1 , 2022 )  ---> est compatible avce AC1 , AC2 ,
INSERT INTO  CompatibleAvecActivité(nomActivite , marque , modele , anneeAchat) VALUES ('Ac1' , 'Marque2' , 'Modéle1' , 2022 ) ; 




SELECT  * FROM CompatibleAvecActivité; 



-- Question : afficher le lot par activte :


DROP VIEW matLouee ; 


CREATE VIEW matLouee AS 
SELECT marque , modele , anneeAchat , SUM(nbrPiecesLouees) as nbLouee FROM Location_MATERIEL , Lot   
WHERE Lot.marque =  Location_MATERIEL.marque 
AND Lot.modele = Location_MATERIEL.modele 
AND Lot.anneeAchat = Location_MATERIEL.anneeAchat
GROUP BY marque , modele , anneeAchat  ;



SELECT Lot.marque , Lot.modele , Lot.anneeAchat , Lot.nbPieces - matLouee.nbLouee FROM  matLouee , Lot   
WHERE Lot.marque =  matLouee.marque 
AND Lot.modele = matLouee.modele 
AND Lot.anneeAchat = matLouee.anneeAchat
GROUP BY marque , modele , anneeAchat ;



SELECT Lot.marque , Lot.modele , Lot.anneeAchat , lot.nbpieces  , nomActivite 
FROM Lot , CompatibleAvecActivité 
WHERE Lot.marque = CompatibleAvecActivité.marque 
AND Lot.modele = CompatibleAvecActivité.modele 
AND Lot.anneeAchat = CompatibleAvecActivité.anneeAchat 
;

 

















