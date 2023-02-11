package com.jmlessous.services;



import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditLibreRepository;
import com.jmlessous.repositories.GarantieRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.jmlessous.entities.CreditLibre;
import com.jmlessous.entities.Garantie;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class CreditLibreServiceImpl implements ICreditLibreService {
    @Autowired
    CreditLibreRepository creditLibre;

    @Autowired
    UtilisateurRepository user;
    @Autowired
    CompteCourantRepository compte;
    @Autowired
    GarantieRepository garantieS;


    @Override
    public List<CreditLibre> retrieveAllCreditLibre() {
        return (List<CreditLibre>) creditLibre.findAll();
    }

    @Override
    public List<CreditLibre> listCreditLibreByClient(Long idUser) {


        return (List<CreditLibre>)  creditLibre.getCreditByUser(idUser);
    }

    @Override
    public CreditLibre getCreditLibreByID(Long idCredit) {
        return (CreditLibre) creditLibre.findById(idCredit).orElse(null);
    }

    @Override
    public List<CreditLibre> listCreditLibreByStatus(Status status) {
        return (List<CreditLibre>) creditLibre.getCreditLibreBySTATUS(status);
    }

    @Override
    public CreditLibre accepterCreditLibre(Long idCredit, Status status) {
        CreditLibre a= creditLibre.findById(idCredit).orElse(null);
        a.setSTATUS(status);
        CompteCourant cc = a.getCompteCredit();
        float x=cc.getSolde()+a.getMontantCredit();
        cc.setSolde(x);
        compte.save(cc);
        creditLibre.save(a);
        return a ;
    }

    @Override
    @Transactional
    public CreditLibre addCreditLibre(CreditLibre credit,Long idCpt, Long idUser,Garantie garantie) {
        Utilisateur u=user.findById(idUser).orElse(null);

        CompteCourant c=compte.findById(idCpt).orElse(null);
        //Garantie g = credit.getGarantie();
        //g.setCredit(credit);
        //credit.getGarantie().setCredit(credit);
        //credit.setGarantie(g);

        credit.setCompteCredit(c);

        //CompteCourant c=compte.getCompteByUser(idUser);
        //  Garantie g = garantie.findById(idGarantie).orElse(null);
       // credit.setCompteCredit(c);

        credit.setDateDemande(new Date());
        float montant=credit.getMontantCredit();

        // a.setMensualite(100F);
       // credit.setMensualite(20000.0F);
        System.out.println(garantie.getValeur());
        float ratio = montant/(u.getSalaire()*12+garantie.getValeur());
        System.out.println(ratio);

        credit.setTauxInteret((float) (0.05+(ratio/20)));
        float period= credit.getDuree()*12;
        float tauxmensuel=credit.getTauxInteret()/12;
        float mensualite=(float) ((montant*tauxmensuel)/(1-(Math.pow((1+tauxmensuel),-period ))));





        if(u.getCreditAuthorization()==null)
       {
           //tester sur le risk(client nouveau)
           //NB LE TAUX DE RISQUE 1%<R<2.5%
           System.out.println(garantie.getValeur());
           System.out.println(credit.getMontantCredit());
           if(1.5*garantie.getValeur()>=credit.getMontantCredit())
           {

               credit.setRisque((float) (0.01+credit.getMontantCredit()/(garantie.getValeur()*100)));
               //credit.setGarantie(g);
               credit.setMensualite(mensualite);
               credit.getCompteCredit().getUtilisateurC().setCreditAuthorization(false);
               credit.setFinC(false);
               credit.setMotif("nouvel client avec garantie certifié");
               credit.setSTATUS(Status.ENCOURSDETRAITEMENT);
               u.setCreditAuthorization(true);
               user.save(u);

           }
           else
           {
               credit.setSTATUS(Status.REFUS);
               credit.setMotif("montant du garantie insuffisant il doit etre egale à 0.33*montant du crédit");
           }
       }
        else if(u.getCreditAuthorization()==true)
        {
            //Ratio retard=late_days/period_of credit
            CreditLibre cridi=CalculatePlusGrandCredit(idUser);
            //float Ratio_Credit=(credit.getMontantCredit()/cridi.getMontantCredit());
          //  float Ratio_Credit=ratio;
           // System.out.println(Ratio_Credit);
            //credit.setGarantie(g);
            credit.setRisque((float) (0.01+credit.getMontantCredit()/(garantie.getValeur()*100)));
            Date jourJ=new Date();
            System.out.println(jourJ);
            int dureeFin = (int) ((jourJ.getTime() - cridi.getDateFin().getTime())/ 1000 * 60 * 60 * 24);
            System.out.println(dureeFin);
            System.out.println("ratioooooo");
            //System.out.println(Ratio_Credit);
           if(cridi.getFinC()==true && dureeFin>=90 ){
            if (1.5*garantie.getValeur()>=credit.getMontantCredit())
            {

                credit.setMensualite(mensualite);
                credit.getCompteCredit().getUtilisateurC().setCreditAuthorization(false);
                credit.setFinC(false);
                credit.setMotif("Ancien client avec un BON risque");
                credit.setSTATUS(Status.ENCOURSDETRAITEMENT);
                u.setCreditAuthorization(true);
                user.save(u);
               // Acceptation(credit,fund,"Ancien client avec un BON risque ");
            }
          /*  else if (credit.getMontantCredit()>=1.5*credit.getGarantie().getValeur() && credit.getMontantCredit()>=1.75*credit.getGarantie().getValeur())
            {
                credit.setRisque(Ratio_Credit);
                credit.setMensualite(mensualite);
                credit.getCompteCredit().getUtilisateurC().setCreditAuthorization(false);
                credit.setMotif("Ancien client avec un risque modérable ");
                credit.setSTATUS(Status.ENCOURSDETRAITEMENT);
                credit.setFinC(false);
                //Acceptation(credit,fund,"Ancien client avec un risque modérable ");
                u.setCreditAuthorization(true);
                user.save(u);
            }*/
            else
            {   credit.setSTATUS(Status.REFUS);
                credit.setMotif("Client trop Risqué Mauvais Historique");
               	//blackLIster le client
                u.setCreditAuthorization(false);
                user.save(u);

            }

           }
           else
           {   credit.setSTATUS(Status.REFUS);
               credit.setMotif("il faut attendre jusqu'a passer les 180 jour apres le dernier credit");

           }

        }
       else
       {
           credit.setSTATUS(Status.REFUS);
           credit.setMotif("Interdiction de Crédit");
           u.setCreditAuthorization(false);
           user.save(u);
       }

        //garantie.setCredit(credit);
        credit.setGarantie(garantie);

        garantieS.save(garantie);
        creditLibre.save(credit);
        return credit;
    }

    public CreditLibre CalculatePlusGrandCredit(Long idUser){
        List<CreditLibre> listCredit= creditLibre.getCreditByUser(idUser);
       CreditLibre cridi = new CreditLibre();
       float montant=0;
        for(CreditLibre cr : listCredit)
        {
            if(cr.getMontantCredit()>= montant)
            {
                cridi=cr;
                montant= cridi.getMontantCredit();
            }

        }
        return cridi;
    }
    //Fonction qui calcule les retards enregistré dans le history dues

    public int CalculateLateDays(Long idUser) {

        List<CreditLibre> listCredit = creditLibre.getCreditByUser(idUser);
        int MS_PER_DAY = 1000 * 60 * 60 * 24;
        int S=0;


        for (CreditLibre CR : listCredit) {
            Date end=CR.getDateFin();
            Date begin=CR.getDateDemande();
            //difference entre deux dates en jours
            int diffInDays = (int)( (end.getTime() - begin.getTime()) / MS_PER_DAY  );
            S=S+(diffInDays);
        }
        return S;


    }

    @Override
    public void deleteCreditLibre(Long idCreditLibre) {
       creditLibre.deleteById(idCreditLibre);
    }

    @Override
    public CreditLibre updateCreditLibre(CreditLibre a, Long idCreditLibre) {
       CreditLibre creditLibre1=creditLibre.findById(idCreditLibre).orElse(null);
       creditLibre1.setCompteCredit(a.getCompteCredit());
        creditLibre1.setTauxInteret(a.getTauxInteret());
        creditLibre1.setMontantCredit(a.getMontantCredit());
        creditLibre1.setMensualite(a.getMensualite());
        creditLibre1.setSTATUS(a.getSTATUS());
        creditLibre1.setDateDemande(a.getDateDemande());
        creditLibre1.setDateFin(a.getDateFin());
        creditLibre1.setGarantie(a.getGarantie());
        creditLibre.save(creditLibre1);
        return creditLibre1;
    }

    //Fonction qui calcule la mensualité
    public float Calcul_mensualite(CreditLibre cr)
    {
        float montant=cr.getMontantCredit();
        float tauxmensuel=cr.getTauxInteret()/12;
        float period=cr.getDuree()*12;
        float mensualite=(float) ((montant*tauxmensuel)/(1-(Math.pow((1+tauxmensuel),-period ))));
        return mensualite;
    }
    //Fonction qui calcule le tabamortissement
    @Override
    public Amortissement[] TabAmortissement(CreditLibre cr, Garantie garantie, float salaire)
    {
        float ratio = cr.getMontantCredit()/(salaire+garantie.getValeur());
        System.out.println(ratio);

        cr.setTauxInteret((float) (0.05+(ratio/20)));

        double TauxinteretMensuel=cr.getTauxInteret()/12;
        System.out.println("interet");
        System.out.println(TauxinteretMensuel);
        int leng=(int) (cr.getDuree()*12);
        System.out.println("periode");
        System.out.println(cr.getDuree()*12);
        System.out.println(leng);

        Amortissement[] ListAmortissement =new Amortissement[leng];

        Amortissement amort=new Amortissement() ;
        //System.out.println(cr.getAmount());


        amort.setMontantR(cr.getMontantCredit());
        float mensualite=Calcul_mensualite(cr);
        amort.setMensualite(mensualite);
        amort.setInterest(amort.getMontantR()*TauxinteretMensuel);
        amort.setAmortissement(amort.getMensualite()-amort.getInterest());

        System.out.println(amort.getMensualite());
        System.out.println(amort.getInterest());
        System.out.println(amort.getAmortissement());
        ListAmortissement[0]=amort;

        //System.out.println(ListAmortissement[0]);
        for (int i=1;i< cr.getDuree()*12;i++) {
            Amortissement amortPrecedant=ListAmortissement[i-1];
            Amortissement amortNEW=new Amortissement() ;
            System.out.println(amortPrecedant.getAmortissement());
            amortNEW.setMontantR(amortPrecedant.getMontantR()-amortPrecedant.getAmortissement());
            amortNEW.setInterest(amortNEW.getMontantR()*TauxinteretMensuel);
            amortNEW.setMensualite(Calcul_mensualite(cr));
            amortNEW.setAmortissement(amortNEW.getMensualite()-amortNEW.getInterest());
            ListAmortissement[i]=amortNEW;

        }



        return ListAmortissement;
    }
    @Override
    public Amortissement Simulateur(CreditLibre credit, Garantie garantie, float salaire)

    {
        float ratio = credit.getMontantCredit()/(salaire+garantie.getValeur());
        System.out.println(ratio);

        credit.setTauxInteret((float) (0.05+(ratio/20)));

        System.out.println(credit.getMontantCredit());
        Amortissement simulator =new Amortissement();
        //mnt total
        simulator.setMontantR(0);

        //mnt interet
        simulator.setInterest(0);
        //mnt monthly
        simulator.setMensualite(Calcul_mensualite(credit));

        Amortissement[] Credittab = TabAmortissement(credit,garantie,salaire);
        float s=0;
        float s1=0;
        for (int i=0; i < Credittab.length; i++) {
            s1=(float) (s1+Credittab[i].getInterest());
        }
        //mnt interet
        simulator.setInterest(s1);
        //mnt total
        simulator.setAmortissement(credit.getMontantCredit()+s1);
        //mnt credit
        simulator.setMontantR(credit.getMontantCredit());

        return simulator;

    }

}
