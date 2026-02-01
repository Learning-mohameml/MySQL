package packages;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

import java.time.LocalTime;




public class requete 
{
    public requete()
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
    public static boolean connexionMembre(Connection connection , Scanner sc )
    {
        try {
    
            PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) as count FROM membre WHERE Email=? AND password=? ");

            System.out.print("Entrez votre email svp : ");
            String emailAvecRetour = sc.nextLine();
            String email = emailAvecRetour.replace("\n", "");

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

            
            }

            if(verif==1)
            {
                System.out.println("Connexion réussie ");

                return true;
            }
            else if(verif==0)
            {
                System.out.println("Invalid Email ou mot de passe .");

                return false;
            }
            else
            {
                System.out.println("");
                return false;
            }

        } 
        catch (SQLException e) 
        {

            e.printStackTrace();
            return false;
        }

    }


    /*
     * 
     * ------------  Inscription : ------------ 
    */

    public static boolean inscriptionMembre(Connection connection)
    {
        boolean bool = true ;



        return bool;



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
     * ---------------------- Parcours des  Refuges : ------------------------------ 
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
                Date dateFerme = res.getDate("dateFerme");
                int nbDispoRepas = res.getInt("nbRepasDispo");
                int nbNuitDispo = res.getInt("nbDormirDispo");

                System.out.println(nom + ", " + dateOuv + " ," + dateFerme + " , " + nbDispoRepas + " , " + nbNuitDispo );
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

    }




/*
 *
 * --------------------------------------------------- Réservation : ------------------------------------- 
 * 
*/


    /*
     *----------------- Réservation d'une formation: ---------------------------  
    */
    public static void réserverFormation(Connection connection , Scanner sc )
    {

    }


    /*
     *----------------- Réservation de Refuge : ---------------------------  
    */
    public static void réserverRefuge(Connection connection , Scanner sc, int idUser)
    {
        try 
        {
            PreparedStatement stmt;
            ResultSet res;

            String emailRefuge;
            String nomRefuge;
            	
            /* On exécute ls script de création des tables de réservations et de disponiblités */
            /*-------------------------------------------------------------------------*/
            res = executeFileSQL(connection , "../../sql/réserveRefuge.sql");
            if(res==null)
            {
                System.err.println("Erreur SQL : Echec lors de la création des tables de réservations et de disponiblités");
                System.exit(1);                
            }
            /*-------------------------------------------------------------------------*/
                
                
            /* On affiche tous les refuges (nom + email) */
		    /*-------------------------------------------------------------------------*/
            stmt =  connection.prepareStatement("SELECT emailref, nomref FROM Refuge;");
            res = stmt.executeQuery();
            if(res==null)
            {
                System.err.println("Erreur SQL : Echec lors de la sélection des noms et emails des refuges");
                System.exit(1);                
            }
            System.out.println("Liste des refuges :");
                
            while(res.next())
            {
                String email = res.getString("emailref");
                String nom = res.getString("nom");
		        System.out.println(nom + " : " + email);
            }
            /*-------------------------------------------------------------------------*/
            	
            	
            /* On demande quel refuge est sollicité (email) et on cherche le nombre de nuités et de repas disponibles (POUR PERIODE DE RESERVATION) et on affiche pour informer l'utilisateur */
            /*-------------------------------------------------------------------------*/
            System.out.print("\nEntrez l'adresse e-mail du refuge dans lequel vous souhaitez réserver :");
                
            emailRefuge = sc.nextLine().replace("\n", "");
            stmt = connection.prepareStatement("SELECT nbrplacesdormir FROM Refuge WHERE emailref=?;");
            stmt.setString(1, emailRefuge);
            int nbDormirDispo = stmt.executeQuery().getInt("nbrplacesdormir");

            stmt = connection.prepareStatement("SELECT nbrrepas FROM Refuge WHERE Dispo.email=?;");
            stmt.setString(1, emailRefuge);
            int nbRepasDispo = stmt.executeQuery().getInt("nbrrepas");
                
            stmt = connection.prepareStatement("SELECT nom FROM Refuge WHERE Refuge.emailref=?;");
            stmt.setString(1, emailRefuge);
            nomRefuge = stmt.executeQuery().getString("nom");

            System.out.println("Le refuge" + nomRefuge + "d'email " + emailRefuge + " a " + nbDormirDispo + " nuités de disponible ainsi que " + nbRepasDispo + " repas de disponible.");
            /*-------------------------------------------------------------------------*/
                
                
            /* On traite les nuités */
            /*-------------------------------------------------------------------------*/
            int nbNuits = 0;
            java.sql.Date dateReservation = new java.sql.Date(0);
            // Il faut boucler pour redonner une tentative à l'utilisateur
            boolean done = false;
            while(!done)
            {
                System.out.println("Attention, il n'est pas possible de ne réserver à la fois aucune nuité et à la fois aucun repas !");
                System.out.print("Entrez la date de réservation (format : YYYY-MM-DD) : ");
                String dateReservationString = sc.nextLine().replace("\n", "");
                        
                /* Traiter la date de réservation */
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
                long millis = sdf.parse(dateReservationString).getTime();
                dateReservation = new java.sql.Date(millis);


                System.out.print("Entrez le nombre de nuités que vous souhaitez réserver : ");
                nbNuits = sc.nextInt();

                
                /* A itérer sur toutes les dates de la période de réservation */
                boolean previousOk = true;
                for(int i = 0; previousOk && i < nbNuits; i++)
                {
                    stmt = connection.prepareStatement("SELECT COUNT(*), FROM Reservation_refuge WHERE emailref=? AND date_reservation <= ? AND DATEADD(day, ?, date_reservation) <= ? AND nbrnuitsreserv > 0;");
                    stmt.setString(1, emailRefuge);
                    stmt.setDate(2, dateReservation);
                    stmt.setInt(3, i);
                    stmt.setDate(4, dateReservation);
                    previousOk = nbDormirDispo > stmt.executeQuery().getInt("COUNT");
                }

                if(previousOk)
                {
                    // Il y a au moins une place toutes les nuits de la période
                    done = true;
                }
                else
                {
                    // Il y a au moins une nuit sans aucune place de disponible
                    System.out.println("Désolé, ce refuge a aucune place lors d'au moins une nuit.");
                }
            }
            /*-------------------------------------------------------------------------*/


            /* On traite les repas */
            /*-------------------------------------------------------------------------*/
            System.out.print("Entrez le nombre de repas que vous souhaitez réserver : ");
            int nbRepasReserve = sc.nextInt();

            while(nbNuits == 0 || nbRepasReserve > nbRepasDispo)
            {
                System.out.println("Désolé, ce refuge n'a pas assez autant de repas de disponible.");
                if(nbNuits == 0) {
                    System.out.println("Vous n'avez pas réservé de nuités, il vous faut réserver au moins un repas !");
                }
                System.out.print("Entrez le nombre de repas que vous souhaitez réserver : ");
                nbRepasReserve = sc.nextInt();
            }
            /*-------------------------------------------------------------------------*/


            /* On procède à la réservation */
            /*-------------------------------------------------------------------------*/
            stmt = connection.prepareStatement("SELECT prixnuite FROM Refuge WHERE Refuge.emailref=?;");
            int prixNuite = stmt.executeQuery().getInt("prixnuite");
            int idReservationRefuge = connection.prepareStatement("SELECT MAX(idreservref) FROM Reservation_refuge;").executeQuery().getInt("idreservref") + 1;

            int heureReservation = LocalTime.now().getHour();

            stmt = connection.prepareStatement("INSERT INTO RESERVATION_REFUGE VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, idReservationRefuge);
            stmt.setDate(2, dateReservation);
            stmt.setInt(3, heureReservation);
            stmt.setInt(4, nbNuits);
            stmt.setInt(5, nbRepasReserve);
            stmt.setInt(6, nbNuits * prixNuite);
            stmt.setString(7, emailRefuge);
            stmt.setInt(8, idUser);
            
            System.out.println("Votre réservation a été prise en compte, merci !");
		    /*-------------------------------------------------------------------------*/
                        
        } 
        catch(SQLException | ParseException e) 
        {
            e.printStackTrace();
        }
    }


    /*
     *----------------- Réservation d'une formation: ---------------------------  
    */
    public static void réserverMatériels(Connection connection , Scanner sc )
    {

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


                if(line.trim().endsWith(";"))
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

}
