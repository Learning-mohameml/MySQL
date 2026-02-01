package packages;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.*;
import java.util.Scanner;

import java.util.Date;
import java.util.HashMap;




public class requettes 
{
    public requettes()
    {
        
    }



/*
 * 
 *
 * --------------------------------------------- Connexion && Inscription : ----------------------------------------- 
 * 
 * 
*/
    
    /*
     * ------------------- Connexion : ----------------- 
    */
    public static int connexionMembre(Connection connection , Scanner sc )
    {
        int idUser = -1;

        // System.out.println("vous êtes membre ou adhérent:\n\t tapez 1 --> membre , \n\t tapez 2 --> adhérent");
        
        // int choix = sc.nextInt();
        // sc.nextLine();

        // String nomTable = "";



        // if(choix==1)
        // {
        //     nomTable = "membre";
        // }
        // else if(choix==2)
        // {
        //     nomTable = "ADHERENT";
        // }
        // else
        // {
        //     System.err.println("choix invalide ");
        //     System.exit(0);
        // }



        try 
        {
        
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) as count , IdUSER  FROM membre WHERE EMAIlMEMBRE=? AND MDP=? GROUP BY IdUSER");

            System.out.print("Entrez votre emial svp : ");
            String emialAvecRetour = sc.nextLine();
            String email = emialAvecRetour.replace("\n", "");

            System.out.print("Entrez votre mot de passe svp : ");
            String passwordAvecRetour = sc.nextLine();
            String password = passwordAvecRetour.replace("\n", "");


            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet res = stmt.executeQuery();
            int verif = -1;


            while (res.next()) 
            {
                verif = res.getInt("COUNT");  
                idUser =  res.getInt("IdUSER");
                
            }

            if(verif==1)
            {
                System.out.println("Connexion réussie ");


            }
            else
            {
                System.out.println("Invalid Email ou mot de passe .");
                System.exit(0);

            }





        } 
        catch (SQLException e) 
        {

            e.printStackTrace();

        }
        
        


        return idUser ;
    }


    /*
     * 
     * ------------  Inscription : ------------ 
    */

    public static int inscriptionMembre(Connection connection , Scanner sc )
    {
        int idUser = -1 ;

        System.out.println("vous voulez inscrit en tant que  membre ou adhérent:\n\t tapez 1 --> membre , \n\t tapez 2 --> adhérent");
        
        int choix = sc.nextInt();
        sc.nextLine();

        String nomTable = "";



        if(choix==1)
        {
            nomTable = "membre";
        }
        else if(choix==2)
        {
            nomTable = "ADHERENT";
        }
        else
        {
            System.err.println("choix invalide ");
            System.exit(0);
        }



        try 
        {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO membre VALUES (? , ? , ? ,? , ? , ?) ");



            System.out.print("Entrez votre emial svp : ");
            String emial = sc.nextLine().replace("\n", "");

            System.out.print("Entrez votre mot de passe svp : ");
            String password = sc.nextLine().replace("\n", "");

            System.out.print("votre nom svp : ");
            String nom = sc.nextLine().replace("\n", "");

            System.out.print("Votre prenom svp: ");
            String prenom = sc.nextLine().replace("\n", "");

            System.out.print("Votre ADrPOST svp: ");
            String ADrPOST= sc.nextLine().replace("\n", "");



            // 
            stmt.setString(1, emial);
            stmt.setString(2, password);
            stmt.setString(3, nom);
            stmt.setString(4, prenom);
            stmt.setString(5, ADrPOST);
            stmt.setInt(6,idUser);


            

            int res = stmt.executeUpdate();

            if(res==1)
            {
                System.out.println("Inscription réussie");
                connection.commit();
            }
            else
            {
                System.out.println("Inscription échouée");
                connection.rollback();
            }


        } 
        catch(SQLException e) 
        {
        }



        return idUser;



    }


/*
 * 
 *--------------------------------- Parcour des informations : --------------------------------------------- 
 * 
 * 
*/


    
    /*
     * ------------------ Parcour des formations : -------------------- 
    */

    public static void afficherFormation(Connection connection)
    {

        try 
        {
            ResultSet res = executeFileSQL(connection , "sql/parcourInfo/afficheformation.sql");


            if(res==null)
            {
                System.err.println("Erreur SQL");
                System.exit(1);
            }

            // affichage de résultat : 
            System.out.println("Anneé , numéro , nom , , activités , dateDém , Duree , nbPlaceRes ");

            while(res.next())
            {
               int  annee = res.getInt("annee");
               int numero = res.getInt("numero");
               String nom = res.getString("nom");
               String activté = res.getString("nomActivité");
               Date datedem = res.getDate("dateDem");
               int duree = res.getInt("duree");
               int nbRestant = res.getInt("nbrRestant");
               System.out.println(annee + ",  " + numero + " , " + nom + ", " + activté + " , " + datedem + " , " + duree + " , " + nbRestant );



            }



            


        } 
        catch(SQLException e1) 
        {
            e1.printStackTrace();
        }



    }

    /*
     * ---------------------- Parcour des  Refuges : ------------------------------ 
    */

    public static void afficherRefuge(Connection connection )
    {
        try{


            ResultSet res = executeFileSQL(connection, "sql/parcourInfo/afficheRefuge.sql");

            if(res==null)
            {
                System.err.println("Erruer SQL");
                System.exit(1);
            }

            System.out.println("nom , dateOuv , dateFreme , nbRepasDispo , nbDormirDispo \n");

            while(res.next())
            {
                String nom = res.getString("nom");
                Date dateOuv = res.getDate("dateOuv");
                Date dateFreme = res.getDate("dateFerme");
                int nbDispoRepas = res.getInt("nbRepasDispo");
                int nbNuitDispo = res.getInt("nbDormirDispo");

                System.out.println(nom + ", " + dateOuv + " ," + dateFreme + " , " + nbDispoRepas + " , " + nbNuitDispo );
            }

            
            

            
        }
        catch (SQLException e1) 
        {

            e1.printStackTrace();
        }


    }


    /*
     *----------------------------------- Parcour des matériels : -----------------------------------------
    */

    public static void affichageMatériels(Connection connection , Scanner sc)
    {

        // affichage par nombre de piéces réserves ou par activités :
        System.out.println("affichag par catégorie -----> tapez 1");
        System.out.println("affichag par activité  -----> tapez 2");


        System.out.print("Tapez votre choix svp : ");

        int choix = sc.nextInt();

        if(choix==1)
        {


            // l'affichae par Arbre de catégories :
            try 
            {
                Statement stmt = connection.createStatement();


                ResultSet res = stmt.executeQuery("SELECT * FROM estparentDe WHERE sousCategorie2='EPI'");                
                

                int i = 1 ;
                

                HashMap<Integer , String> dict = new HashMap<>();



                while (res.next()) 
                {
                    String souscategorie = res.getString("souscategorie1");
                    
                    System.out.println(souscategorie +" ----->  tapez " + i );

                    
                    dict.put(i , souscategorie);
                    i++ ;                    

                    


                }

                System.out.println("Si vous confirmez le choix tapez : -1");
                
                boolean bool = true ; 

                
                int choix2 = 0  ; 
                int choixPrec = 0 ;

                while(bool)
                {

                    choixPrec = choix2 ; 
                    System.out.print("Tapez un nouveau choix ou confirmez (avce -1) : ");

                    choix2 = sc.nextInt();
                    sc.nextLine();


                    if(choix2==-1)
                    {
                        bool = false ;
                    }
                    else
                    {

                        if(choix2 > i )
                        {
                            System.out.println("Choix invalide");
                            System.exit(0);
                        }
                        
                        

                        ResultSet res2 = stmt.executeQuery("SELECT * FROM estparentDe WHERE sousCategorie2='"+dict.get(choix2) +"'");

                        if(res2==null)
                        {
                            System.out.println("Il n'y apas de sous catégorie de cette catégorie ");
                        }


                        while(res2.next())
                        {
                            String souscategorie = res2.getString("souscategorie1");
                            
                            if(!dict.containsValue(souscategorie))
                            {
                                System.out.println(souscategorie +" ----->  tapez " + i );
                                dict.put(i , souscategorie);

                                i++;
                            }

                        }                        
                    }

                }


                ResultSet res3 = stmt.executeQuery("SELECT * FROM Lot WHERE souscategorie='" + dict.get(choixPrec) +"'");
                
                System.out.println("marque\t, modele\t , nbpiecesTotale ");
                while(res3.next())
                {
                    String marque = res3.getString("Marque");
                    String modele  = res3.getString("Modele");
                    int nbPiecesTotal  = res3.getInt("NBPIECES");



                    System.out.println(marque + "\t , " + modele + "\t , " + nbPiecesTotal);
                    
                }


            
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }




        }
        else if(choix==2)
        {
            // l'affichage par activités : 

            
        }
        else 
        {
            System.err.println("choix Invalide ");
            System.exit(0);
        }


    }




/*
 *
 * --------------------------------------------------- Réservation : ------------------------------------- 
 * 
*/


    /*
     *----------------- Réservation d'une formation: ---------------------------  
    */
    public static void réserverFormation(Connection connection , Scanner sc , int id )
    {

        // verifiction que : USER est un adhérant :
        
        try
        {


            Statement stmt = connection.createStatement();

            ResultSet res = stmt.executeQuery("SELECT idUSER FROM ADHERENT ");
        
            
            int s = 0 ;

            while (res.next()) 
            {
                int idadh = res.getInt("idUSER"); 
                
                
                if(idadh==id)
                {
                    s+=1;
                    break;
                    
                }
            }


            if(s!=1) 
            {
                System.err.println("Désolé vous n'êtes pas adhérant ");
                  System.exit(1);  
            }

            

        
        
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        








        System.out.print("Donnez l'année de la foramtion (par example 2022 ..) :");
        int anneeFor = sc.nextInt();
        sc.nextLine();

        System.out.print("Donnez le numéro de formation :");
        int numeroFor  = sc.nextInt();
        sc.nextLine();



        try{

            executeFileSQL(connection , "sql/réservation/réserveFormation.sql");

            String date = "TO_DATE('"+anneeFor+"-01-01','YYYY-MM-DD')";

            Statement stmt = connection.createStatement();

            
    



            ResultSet res = stmt.executeQuery("SELECT nbDispo FROM NBDISPOFOR WHERE IdFORM="+numeroFor +"AND ANNEeFORM=" + date);


            

            int nbReservFor = -1 ;

            while(res.next())
            {
                nbReservFor = res.getInt("nbDispo");

                // System.out.println("nbDispo = "+nbReservFor);
            }

            System.out.println("nbReserv = "+nbReservFor);
            if(nbReservFor >0 )
            {
                // donc on peut réserver une formation :

                ResultSet res3 = stmt.executeQuery("SELECT COUNT(*) as count FROM RESERVATION_FORMATION");

                int idReservFor = -1  ;


                System.out.println("je suis la à res3");
                while (res3.next()) 
                {
                    idReservFor = res3.getInt("count");    
                }

                ResultSet res4 = stmt.executeQuery("SELECT MAX(RANG) as max FROM RESERVATION_FORMATION WHERE NUMERO="+numeroFor +"AND ANNEE=" + date);

                int rang = -1 ;
                
                
                
                System.out.println("je suis la à res4");

                while (res4.next()) 
                {
                    rang = res4.getInt("max");    
                }

                idReservFor++;
                rang++; 

                System.out.println("idResreevFor = "+idReservFor);
                System.out.println("rang = "+rang);
                String query = String.format("INSERT INTO RESERVATION_FORMATION VALUES (%d ,%d ,%d , %d ,%s)",idReservFor,rang ,id,numeroFor, date);

                int row = stmt.executeUpdate(query);
                
                if(row==1)
                {
                    System.out.println("Réservation bien pasée ");
                    connection.commit();
                }
                else
                {
                    connection.rollback();
                }

                


            }
            else
            {
                // 

                System.out.println("Désole il n'existe plus de place, on va vous mettre en liste d'attente  : -----------> votre ranga est " + (-1*nbReservFor)+1);
            }


        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }





    }


    /*
     *----------------- Réservation de Refuge : ---------------------------  
    */
    public static void réserverRefuge(Connection connection , Scanner sc )
    {
        System.out.println("Réservation de nuités          ------>   tapez 1");

        System.out.println("Réservation de repas            ------->  tapez 2");

        System.out.println("Réservation de repas et de nuités    ------->  tapez 3");


        System.out.print("Tapez votre choix 1 , 2 ou 3 : ");

        int choix = sc.nextInt();
        sc.nextLine();


        if(choix==1)
        {
	/* On souhaite réserver uniquement des nuités */
            try 
            {
            	PreparedStatement stmt;
            	ResultSet res;
            	
            	/* On exécute ls script de création des tables de réservations et de disponiblités */
            	/*-------------------------------------------------------------------------*/
                res =             // TODO: handle exception
executeFileSQL(connection , "../../sql/réserveRefuge.sql");
                if(res==null)
                {
                    System.err.println("Erreur SQL : Echec lors de la création des tables de réservations et de disponiblités");
                    System.exit(1);                
                }
                /*-------------------------------------------------------------------------*/
                
                
                /* On affiche tous les refuges (nom + email) */
		/*-------------------------------------------------------------------------*/
                stmt =  connection.prepareStatement("SELECT emailref, nomref FROM Refuge");
                res = stmt.executeQuery();
                if(res==null)
                {
                    System.err.println("Erreur SQL : Echec lors de la sélection des noms et emails des refuges");
                    System.exit(1);                
                }

               //  PreparedStatement stmt = connection.prepareStatement("SELECT nbDormirDispo FROM Dispo WHERE Dispo.email=?");
                
                
                System.out.print("tapez svp les nombres de nuits :");
                
                int nbNuits = sc.nextInt();
                stmt.setInt(1, nbNuits);

                ResultSet res2 = stmt.executeQuery();

                int nbDispo = -1 ;

                while(res2.next()) 
                {
                    nbDispo = res2.getInt("nbDormirDispo");

                    
                }

                if(nbDispo==-1)
                {
                    System.err.println("Erreur SQL ou Refuge non trouvé ");
                    System.exit(0);
                }
                else
                {
                    if(nbDispo < nbNuits)
                    {
                        System.out.println("Désolé, nous n'avons pas de chambres suffisantes.");                            
                    }
                    else
                    {
                        // réservation :

                        

                    }
                }

                        
            } 
            catch(SQLException e) 
            {
                e.printStackTrace();
            }
            

        
        
        }
        else if(choix==2)
        {

        }
        else if(choix==3)
        {

        }
        else
        {
            // choix invalide :
            System.out.println("choix invalide ");
            System.exit(0);
        }
    
    
    }


    /*
     *----------------- Réservation d'une formation: ---------------------------  
    */
    public static void réserverMatériels(Connection connection , Scanner sc )
    {
        // réservation matériels : 
        System.out.println("le nombre de piéces à rserver :");

        int nb = sc.nextInt() ; 
        sc.nextLine(); // pour le \n 

        System.out.println("la date de réservation sous la forme DD-MM-YYYY : ");
        String dateRéserv = sc.nextLine();

        System.out.println("la date de récupération sous la forme DD-MM-YYYY : ");
        String dateRécup = sc.nextLine();

        try {
    
            Statement stmt = connection.createStatement(); 
            


            // on récupere le nombre des piéces disponibbles entre le deux date : 
            ResultSet res  = stmt.executeQuery("SELECT COUNT(*) FROM "); 





        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }



    }



/*
 * ------------------------- Droit de l'oublie --------------------------
*/

public static void oublierMoi(Connection connection)
{

}

    
/*
 * --------- fonction : exécuter un fichier .sql ---------------
 */

    public static ResultSet executeFileSQL(Connection connection , String nameFile)
    {


        try 
        {
            Statement stmt = connection.createStatement();

            // lecture de fichier SQL : 
            BufferedReader reader = new BufferedReader(new FileReader(nameFile));

            StringBuilder query = new StringBuilder();
            String line ; 

            ResultSet res = stmt.executeQuery("SELECT 0 FROM Membre"); // Init : ?? 

            while((line = reader.readLine())!=null)
            {
                String lineCopie = new String(line);

                if(!lineCopie.trim().isEmpty() && !lineCopie.trim().startsWith("--"))
                {
                    // si n'est pas une ligne vide , ni un commentaire:
                    query.append(line).append(" ");
                }


                if(lineCopie.trim().endsWith(";"))
                {
                    // il s'agit de la fin d'une Réquette : 
                    res = stmt.executeQuery(query.toString().replace(";" , " "));
                
                    query = new StringBuilder();
                }
            }
            
            
            reader.close();


            return res ;
        } 
        catch (SQLException e1) 
        {
            e1.printStackTrace();
        }
        catch(IOException e2)
        {
            e2.printStackTrace();
        }

        return null ; // en cas d'errure  
    }




    /*
     * 
     * ------------------- fonction pour la parcour dse matériels : 
     * 
     */


    // public static void parcourArbreMat(Statement stmt , String choix)
    // {

    //     ResultSet res = stmt.executeQuery("SELECT * FROM estparentDe WHERE sousCategorie2='"+choix+"'");        
                

    //     int i = 1 ;
                
    //             HashMap<Integer , String> dict = new HashMap<>();

    //             while (res.next()) 
    //             {
    //                 String souscategorie = res.getString("souscategorie1");
                    
    //                 System.out.println(souscategorie +" ----->  tapez" + i );
    //                 dict.put(i , souscategorie);

    //                 i++ ;                    
    //             }

    //             System.out.print("Tapez votre choix svp : ");
                
    //             int choix2 = sc.nextInt(); 

    //             if(choix2 > i )
    //             {
    //                 System.out.println("Choix invalide");
    //                 System.exit(0);
    //             }
                



    //             if(choix2 > i )
    //             {
    //                 System.out.println("Choix invalide");
    //                 System.exit(0);
    //             }
                
                

    //             ResultSet res2 = stmt.executeQuery("SELECT * FROM estparentDe WHERE sousCategorie2='"+dict.get(choix2) +"'");


    //             while(res2.next())
    //             {
    //                 String souscategorie = res2.getString("souscategorie1");
                    
    //                 System.out.println(souscategorie +" ----->  tapez " + i );
    //                 dict.put(i , souscategorie);

    //                 i++;
    //             }
    // }



    /*
     * ---------------- Calcule le idUser ------------------
     */
    public static int idUser(Connection connection)
    {
        int iduser = 0 ;

        try
        {
            Statement stmt2 = connection.createStatement() ;

            ResultSet res0 = stmt2.executeQuery("SELECT COUNT(*) as count FROM MEMBRE");
            ResultSet res1 = stmt2.executeQuery("SELECT COUNT(*) as count FROM ADHERENT");


            int nbUser = 0 ;

            while(res0.next())
            {
                nbUser += res0.getInt("count");
            }

            while(res1.next())
            {
                nbUser += res1.getInt("count");
            }

                
                
            iduser = nbUser +1 ;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }





        return iduser ;
    }




}
