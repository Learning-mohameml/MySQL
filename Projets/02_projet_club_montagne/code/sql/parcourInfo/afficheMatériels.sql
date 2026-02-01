DROP VIEW matLouee ; 


CREATE VIEW matLouee AS 
SELECT marque , modele , anneeAchat , SUM(nbrPiecesLouees) as nbLouee FROM Location_MATERIEL , Lot   
WHERE Lot.marque =  Location_MATERIEL.marque 
AND Lot.modele = Location_MATERIEL.modele 
AND Lot.anneeAchat = Location_MATERIEL.anneeAchat
GROUP BY marque , modele , anneeAchat  ;



CREATE VIEW DispoMat AS 

SELECT Lot.marque , Lot.modele , Lot.anneeAchat , Lot.nbPieces - matLouee.nbLouee as nbDispo  FROM  matLouee , Lot   
WHERE Lot.marque =  matLouee.marque 
AND Lot.modele = matLouee.modele 
AND Lot.anneeAchat = matLouee.anneeAchat
GROUP BY marque , modele , anneeAchat ; 



SELECT marque , annee , nbDispo FROM DispoMat WHERE 


