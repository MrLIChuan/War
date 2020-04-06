import java.lang.Math;
import java.util.ArrayList;

public class JeuDeLaBataille {

    public static void main (String args[]){

        JeuDeCarte jeu = new JeuDeCarte();        
        Joueur Chuan = new Joueur(jeu);
        Joueur Steve_Jobs = new Joueur(jeu);

        Chuan.CreerPaquetDePartie();
        Steve_Jobs.CreerPaquetDePartie();
        
        int CarteDeChuan, CarteDeSteve_Jobs = 0;
        
        Chuan.JouerCarte();
        Steve_Jobs.JouerCarte();

        CarteDeChuan = Chuan.ValeurDerniereCarteDuJeu();
        CarteDeSteve_Jobs = Steve_Jobs.ValeurDerniereCarteDuJeu();

        while( (Chuan.NePeutPasJouer() == false) && (Steve_Jobs.NePeutPasJouer() == false )){
        
            System.out.println("-----------------------------------------------");
            System.out.println("Chuan carte " + CarteDeChuan + " Steve_Jobs carte " + CarteDeSteve_Jobs);
            System.out.println("Chuan nb carte " + Chuan.TailleJeuDuJoueur() + " Steve_Jobs nb carte " + Steve_Jobs.TailleJeuDuJoueur());
            System.out.println("-----------------------------------------------");

            if( CarteDeChuan > CarteDeSteve_Jobs  ){
                Chuan.RamaserPaquetDePartieDe(Steve_Jobs);
                System.out.println("Chuan gagne la manche");
            }
            else if( CarteDeChuan  < CarteDeSteve_Jobs  ){
                Steve_Jobs.RamaserPaquetDePartieDe(Chuan);
                System.out.println("Steve_Jobs gagne la manche");
            }
            else  Bataille(Chuan, Steve_Jobs);

            Chuan.JouerCarte();
            Steve_Jobs.JouerCarte();

            CarteDeChuan = Chuan.ValeurDerniereCarteDuJeu();
            CarteDeSteve_Jobs = Steve_Jobs.ValeurDerniereCarteDuJeu();
        }
        
        if(Chuan.NePeutPasJouer() == true){
            System.out.println("Steve_Jobs a gagner !");
        }
        else System.out.println("Chuan a gagner !");

    }

    public static void Bataille(Joueur J1, Joueur J2){
        J1.JouerCarte();
        J2.JouerCarte();
    }
}

class Carte{

    private int ValeurSymbolique;
    private String couleur;
    private String nom;

    public Carte(int n){
        if((n-1)/13 == 0){
            couleur = "Carreaux";
        }
        else if((n-1)/13 == 1){
            couleur = "Treffle";
        }
        else if((n-1)/13 == 2){
            couleur = "Piques";
        }
        else couleur = "Coeurs";

        ValeurSymbolique = ((n-1) % 13 + 1);

        switch(ValeurSymbolique) {

            case 1:
                nom = String.format("%s As", couleur);
            break;
            case 11:
                nom = String.format("%s Valet", couleur);
            break;
            case 12:
                nom = String.format("%s Dame", couleur);
            break;
            case 13:
                nom = String.format("%s Roi", couleur);
            break;
            default:
                nom = String.format("%s %d", couleur, ValeurSymbolique);
        }
        
    }

    public int DonnerValeurDeCarte(){

        return ValeurSymbolique;
    }

    public void AfficherCarte(){
        System.out.println("Valeur du symbole = " + ValeurSymbolique);
        System.out.println("Couleur de la carte = " + couleur);
        System.out.println("Nom de la carte = " + nom);
    }
}

class JeuDeCarte{

    private ArrayList<Carte> JeuComplet;
    public static final int NB_CARTE_JEU_COMPLET = 26;

    public JeuDeCarte(){

        JeuComplet = new ArrayList<Carte>(NB_CARTE_JEU_COMPLET);
        for(short i = 0; i < NB_CARTE_JEU_COMPLET; i++){
            Carte CARTE = new Carte(i);
            JeuComplet.add(CARTE);
        }
    }

    public Carte DistribuerCarte(){

        int indiceAuHasard = (int) (Math.random() * (JeuComplet.size()));
        Carte Carte = JeuComplet.get(indiceAuHasard);
        JeuComplet.remove(indiceAuHasard);
        return Carte;
    }

    public void AfficherJeuDeCarte(){
        for(short i = 0; i < JeuComplet.size(); i++){
            Carte Carte = JeuComplet.get(i);
            Carte.AfficherCarte();
        }
    }
}


class Paquet{

    public ArrayList<Carte>PaquetDeCarte;
    private boolean vide;

    public Paquet(){

        PaquetDeCarte = new ArrayList<Carte>(JeuDeCarte.NB_CARTE_JEU_COMPLET);
    }

    public Paquet(JeuDeCarte UnJeu){

        PaquetDeCarte = new ArrayList<Carte>(JeuDeCarte.NB_CARTE_JEU_COMPLET);
    }
   
    public Paquet PaquetAS(){
        return null;
    }

    public Carte PoserCarte(){  //MARCHE
        
        Carte carte = PaquetDeCarte.get((PaquetDeCarte.size() - 1));
        PaquetDeCarte.remove((PaquetDeCarte.size() - 1));
        return carte;
    }

    public void JeterCarte(){   //Supprime la dernier carte ? //MARCHE

        PaquetDeCarte.remove((PaquetDeCarte.size() - 1));
    }

    public void AjouterCarte(Carte c){ //MARCHE

        PaquetDeCarte.ensureCapacity(PaquetDeCarte.size());
        PaquetDeCarte.add(c);
    }


    public void AjouterPaquet(Paquet p){ //Ajout d'un paquet de carte a un paquet  //MARCHE
    
        int taille = p.TaillePaquet();   //Enregistre taille de p avant la boucle    
        PaquetDeCarte.ensureCapacity( (PaquetDeCarte.size() + 0) + (p.TaillePaquet() + 0) );    

        for(short i = 0; i < taille ; i++){ // On mais taille et pas p.TaillePaquet car TaillePaquet va changer a force de retirer des cartes
            PaquetDeCarte.add(p.PoserCarte());  
        } 
    }   


    public Carte DernierCarte(){ //MARCHE

        return PaquetDeCarte.get((PaquetDeCarte.size() - 1));
    }


    public Carte PremiereCarte(){ //MARCHE

        return PaquetDeCarte.get(0);
    }

    public boolean EstVide(){   //1 egale vide , 0 plein //MARCHE

        if(PaquetDeCarte.get(0) == null){
            return true;
        }
        else return false;
    }

    public int TaillePaquet(){ //MARCHE
        
        int taille = PaquetDeCarte.size();
        return taille;
    }

    public void AfficherPaquet(){   //MARCHE
        for(short i = 0; i < PaquetDeCarte.size(); i++){
            Carte carte = PaquetDeCarte.get(i);
            carte.AfficherCarte();
            //PaquetDeCarte.get(i).AfficherCarte();
        }
    }
}

class Joueur{

    private Paquet p;
    private Paquet JeuDuJoueur;
    public String statut = null;
    public Paquet PaquetDePartie;

    public Joueur(JeuDeCarte jdc){
       
        JeuDuJoueur = new Paquet();
        
        for(short i = 0; i < (JeuDeCarte.NB_CARTE_JEU_COMPLET / 2); i++){

            JeuDuJoueur.AjouterCarte(jdc.DistribuerCarte());
        }
    }

    public Joueur(JeuDeCarte jdc, String message){
        
        JeuDuJoueur = new Paquet();
        
        for(short i = 0; i < (JeuDeCarte.NB_CARTE_JEU_COMPLET / 2); i++){

            JeuDuJoueur.AjouterCarte(jdc.DistribuerCarte());
        }
        statut = message;
    }

    public boolean PeutJouer(){

        return true;
    }

    public boolean NePeutPasJouer(){ //Pas forcement tres utile

        if(JeuDuJoueur.TaillePaquet() == 0){
            return true;
        }
        else return false;
    }

    public void CreerPaquetDePartie(){

        PaquetDePartie = new Paquet();
    }

    public void JouerCarte(){

        PaquetDePartie.AjouterCarte(JeuDuJoueur.PoserCarte());
    }

    public void DissimulerMauvaiseCarte(){
        

    }

    public int valeurPremiereCarteDuJeu(){

        return PaquetDePartie.PremiereCarte().DonnerValeurDeCarte();
    }

    public int ValeurDerniereCarteDuJeu(){

        return PaquetDePartie.DernierCarte().DonnerValeurDeCarte();
    }

    public void RamaserPaquetDePartieDe(Joueur j){

        JeuDuJoueur.AjouterPaquet(j.DonnerJeu());
        JeuDuJoueur.AjouterPaquet(PaquetDePartie);
    }

    public Paquet DonnerJeu(){

        return PaquetDePartie;
    }

    public void AfficherJeu(){
        JeuDuJoueur.AfficherPaquet();
    }

    public void AfficherPaquetDePartie(){
        PaquetDePartie.AfficherPaquet();
    }

    public int TailleJeuDuJoueur(){

        return JeuDuJoueur.TaillePaquet();
    }
}
