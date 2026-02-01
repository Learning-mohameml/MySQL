-- Test Table Activte  :

CREATE TABLE Activite
(
    nomActivité VARCHAR(50) PRIMARY KEY 
);


INSERT INTO Activite (nomActivité) VALUES ('Ac1');
INSERT INTO Activite (nomActivité) VALUES ('Ac2');
INSERT INTO Activite (nomActivité) VALUES ('Ac3');
INSERT INTO Activite (nomActivité) VALUES ('Ac4');

SELECT * FROM activite;



--  Test Table Formation :

CREATE TABLE Formation
(
    numero INT  ,
    annee INT ,
    nom VARCHAR(50) ,
    dateDem DATE , 
    duree INT ,
    nbrPlace INT , 
    description VARCHAR(100) , 
    prix INT , 
    nbActivite INT ,
    
    PRIMARY KEY(numero , annee) 
);



INSERT INTO Formation (numero , annee , nom , dateDem , duree , nbrPlace , description , prix ,nbActivite) VALUES (1,2023,'for1' , TO_DATE('11-01-2023','DD-MM-YYYY') , 15  , 20 , 'rien ' , 200 , 2 );
INSERT INTO Formation (numero , annee , nom , dateDem , duree , nbrPlace , description , prix ,nbActivite) VALUES (2,2023,'for2' , TO_DATE('20-02-2023','DD-MM-YYYY') , 20  , 50 , 'rien ' , 205 , 3 );
INSERT INTO Formation (numero , annee , nom , dateDem , duree , nbrPlace , description , prix ,nbActivite) VALUES (3,2023,'for3' , TO_DATE('09-02-2023','DD-MM-YYYY') , 10  , 30 , 'rien ' , 300 , 4 );




SELECT * FROM  Formation ;



-- 
DROP TABLE proposeActivitéFrom;

CREATE TABLE  proposeActivitéForm
( 

    nomActivité VARCHAR(50) ,
    annee INT ,
    numero INT , 
    
    PRIMARY KEY(nomActivité , numero , annee) ,  

    FOREIGN KEY(nomActivité) REFERENCES  Activite(nomActivité) , 
    FOREIGN KEY(numero , annee) REFERENCES  Formation(numero , annee) 


);


INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac1', 2023 , 1);
INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac2', 2023 , 1);

INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac1', 2023 , 2);
INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac2', 2023 , 2);
INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac3', 2023 , 2);


INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac1', 2023 , 3);
INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac2', 2023 , 3);
INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac3', 2023 , 3);
INSERT INTO proposeActivitéForm(nomActivité , annee , numero)  VALUES ('Ac4', 2023 , 3);



SELECT * FROM proposeActivitéForm ORDER BY numero ;




-- Table RésérvationFormation 
DROP TABLE concerneFormation;
CREATE TABLE concerneFormation
(
    id INT PRIMARY KEY  ,
    annee INT  ,
    numero INT  , 
    
    FOREIGN KEY(numero , annee ) REFERENCES Formation(numero , annee) 
    
    
);

INSERT INTO concerneFormation(id , annee , numero) VALUES (1 , 2023 , 1);
INSERT INTO concerneFormation(id , annee , numero) VALUES (2 , 2023 , 1);
INSERT INTO concerneFormation(id , annee , numero) VALUES (3 , 2023 , 1);
INSERT INTO concerneFormation(id , annee , numero) VALUES (4 , 2023 , 2);
INSERT INTO concerneFormation(id , annee , numero) VALUES (5 , 2023 , 2);
INSERT INTO concerneFormation(id , annee , numero) VALUES (6 , 2023 , 2);
INSERT INTO concerneFormation(id , annee , numero) VALUES (7 , 2023 , 2);
INSERT INTO concerneFormation(id , annee , numero) VALUES (8 , 2023 , 3);
INSERT INTO concerneFormation(id , annee , numero) VALUES (9 , 2023 , 3);
INSERT INTO concerneFormation(id , annee , numero) VALUES (10 , 2023 , 3);
INSERT INTO concerneFormation(id , annee , numero) VALUES (11 , 2023 , 3);
INSERT INTO concerneFormation(id , annee , numero) VALUES (12 , 2023 , 3);

SELECT * FROM concerneFormation;









