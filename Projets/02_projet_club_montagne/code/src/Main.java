import java.sql.*;
import java.util.Scanner;

import packages.requete;
import packages.requettes;


public class Main
{
    public static void main(String[] args) {
    

        System.out.println("-------------------------------- Bienvenue dans l'application : gestion des services de l'équipe 1---------------------------------- \n ");
        


        String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
        String user="chrifmhm";
        String password = "chrifmhm";
    
        try 
        {

            // l'énregistrement du pilote driver d'orcale :

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 

            // la connexion:
            Connection connection =  DriverManager.getConnection(url, user, password);
            

            // Désactiver le mode autocommit pour gérer manuellement la transaction                   
            connection.setAutoCommit(false);

        
            
          // Sacnner 

          Scanner sc = new  Scanner(System.in);


            /* 
            ------------------ étape 1 : connexion -------------------- 
            */
            int idUser = etape1(connection , sc);

            System.out.println("idUser = " + idUser);
            // if(!verif)
            // {
            //     //  la connexion ou l'inscription ne marche pas bien : 
            //     System.exit(0);
            // }

            /*
             * 
             *-------------------------------- étpae 2 : choix des fonctionnalités ----------------   
             */

            System.out.println("------------------------ Régles d'Utilisation -------------------------");
            
            System.out.println("Pour le choix entre parcoures des informations ou réservation : ");
            System.out.println("\t-parcoures des informations ----> tapez 1 ");
            System.out.println("\t-réservation               ----> tapez 2 ");

            System.out.println("\nPour le choix entre formation/refuges/matériels:");
            System.out.println("\t-formation --> tapez 1 \n\t-refuges ----> tapez 2\n\t-matériels ---> tapez 3 ");
            
            System.out.println("\nExemple : \n\t -si vous tapez 11 ----> parcour des formations ");
            System.out.println("\t -si vous tapez 21 : ---> réservation des formations ");


            System.out.println("\nPour quitter le mini-shell: -----> tapez exit\n");

            System.out.println("\nPour le droit de l'oublie : ----> tapez oublierMoi");


            String choix ;

            boolean bool = true ;
            
            while(bool)
            {
                System.out.println("");
                System.out.print("> ");
                choix = sc.nextLine();


                if(choix.equals("exit"))
                {
                    System.out.println("----- Au revoir ------");
                    bool=false;
                }
                else if(choix.equals("oublierMoi"))
                {
                    requete.oublierMoi(connection);
                }
                else if(!choix.isEmpty()  && choix.charAt(0)=='1')
                {
                    // parcour de informations :
                    parcourInfo(connection , choix , sc);
                }
                else if(!choix.isEmpty()  && choix.charAt(0)=='2')
                {
                    // réservation :
                
                    réservation(connection , choix , sc  , idUser);
                }
                else 
                {
                    // choix invalide : 
                    
                    System.err.println(" \n choix invalide ");
                    System.exit(0);
                }


            }
            

            connection.close();
            sc.close();
        
        
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

    }




/*

-------------------------- Les fonctionnalités : ------------------------------------------------------------------

*/

    public static int etape1(Connection connection , Scanner sc ) 
    {
        System.out.println("\n----------------- Page de connexion :------------------------\n");
        System.out.println("Pour connecter à votre compte: ----> tapez 1.");
        System.out.println("Pour l'inscription :           ----> tapez 2.");
        System.out.print("\ntapez votre choix 1 ou 2 :");
    
        int idUser = -1 ;

        int choix = sc.nextInt();
        sc.nextLine();


        if(choix==1)
        {
            // connexion :
            System.out.println("");
            idUser  = requettes.connexionMembre(connection , sc);
            
        }
        else if(choix==2)
        {
            // inscription :

            idUser = requettes.inscriptionMembre(connection ,sc);

        }
        else
        {
            // choix invalide :

            System.out.println("choix invalide");


        }
        


        return idUser ;
    }



    public static void parcourInfo(Connection connection , String choix , Scanner sc )
    {
        if(choix.equals("11"))
        {
            // System.out.println(" ici : 11");
            // parcour des formations : 
            requettes.afficherFormation(connection);
        }
        else if(choix.equals("12"))
        {
            //System.out.println(" ici : 12");
            requettes.afficherRefuge(connection);
        }
        else if(choix.equals("13"))
        {
            //System.out.println(" ici : 13");
            requettes.affichageMatériels(connection, sc);
        }
        else
        {
            System.err.println("choix invalide");
            System.exit(0);
        }

    }


    public static void réservation(Connection connection , String choix , Scanner sc , int id )
    {
        if(choix.equals("21"))
        {
            requettes.réserverFormation(connection, sc, id);
        }
        else if(choix.equals("22"))
        {
            System.out.println(" ici : 22");

        }
        else if(choix.equals("23"))
        {
            System.out.println(" ici : 13");

        }
        else
        {
            System.err.println("choix invalide");
            System.exit(0);
        }  
    }

}
    






