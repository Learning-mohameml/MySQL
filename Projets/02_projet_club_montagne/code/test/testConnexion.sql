-- Test de l'étape 1 : Connexion && Inscription : 

CREATE TABLE Membre 
(

    email VARCHAR(50) ,
    password VARCHAR(50) , 
    idUser INT ,
    nom VARCHAR(50) ,
    prenom VARCHAR(50) , 
    
    
    PRIMARY KEY(email , password , idUser ) 
    
);


INSERT INTO Membre(email , password , idUser , nom , prenom) VALUES ('nebil@gmail.com' , '1234567' , 1 ,'nebil' , 'Mohamed' );
INSERT INTO Membre(email , password , idUser , nom , prenom) VALUES ('sidi@gmail.com' , '000000' , 2 ,'sidi' , 'Oumar' );
INSERT INTO Membre(email , password , idUser , nom , prenom) VALUES ('khaled@gmail.com' , '0123' , 3 ,'khaled' , 'Ahmed' );
INSERT INTO Membre(email , password , idUser , nom , prenom) VALUES ('victor@gmail.com' , 'h1234' , 4 ,'victor' , 'Hugo' );

INSERT INTO Membre(email , password , idUser , nom , prenom) VALUES ('root' , 'root' , 0 ,'root' , 'root' );




