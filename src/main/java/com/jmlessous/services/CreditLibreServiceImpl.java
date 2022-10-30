package com.jmlessous.services;


import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditLibreRepository;
import com.jmlessous.repositories.GarantieRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    GarantieRepository garantie;

    @Override
    public List<CreditLibre> retrieveAllCreditLibre() {
        return (List<CreditLibre>) creditLibre.findAll();
    }

    @Override
    public CreditLibre addCreditLibre(CreditLibre credit, Long idUser, Long idGarantie) {
        Utilisateur u=user.findById(idUser).orElse(null);
        CompteCourant c=compte.getCompteByUser(idUser);
        Garantie g = garantie.findById(idGarantie).orElse(null);
        credit.setCompteCredit(c);
        credit.setDateDemande(new Date());
        float montant=credit.getMontantCredit();

 // a.setMensualite(100F);

        float ratio = credit.getMensualite()/u.getSalaire();
        credit.setTauxInteret((float) (0.05+(ratio/20)));
        float tauxmensuel=credit.getTauxInteret()/12;
        float period= credit.getDuree()*12;
        float mensualite=(float) ((montant*tauxmensuel)/(1-(Math.pow((1+tauxmensuel),-period ))));
       if(u.getCreditAuthorization()==null)
       {
           //tester sur le risk(client nouveau)
           //NB LE TAUX DE RISQUE 1%<R<2.5%
           if(1.5*credit.getGarantie().getValeur()>=credit.getMontantCredit())
           {

               credit.setRisque((float) (0.01+credit.getMontantCredit()/(credit.getGarantie().getValeur()*100)));

               credit.setMensualite(mensualite);
               credit.getCompteCredit().getUtilisateurC().setCreditAuthorization(false);
               credit.setMotif("nouvel client avec garantie certifié");
               credit.setSTATUS(Status.ENCOURSDETRAITEMENT);

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

            float Ratio_retard=(CalculateLateDays(idUser))/(creditLibre.getIDofLatestCompletedCreditsByClient(idUser).getDuree()*12*30);
            if (Ratio_retard<0.1)
            {
                credit.setRisque((float) 0.1);
                credit.setMensualite(mensualite);
                credit.getCompteCredit().getUtilisateurC().setCreditAuthorization(false);
                credit.setMotif("Ancien client avec un BON risque");
                credit.setSTATUS(Status.ENCOURSDETRAITEMENT);
               // Acceptation(credit,fund,"Ancien client avec un BON risque ");
            }
            else if (Ratio_retard>=0.1 && Ratio_retard<=0.25)
            {
                credit.setRisque(Ratio_retard);
                credit.setMensualite(mensualite);
                credit.getCompteCredit().getUtilisateurC().setCreditAuthorization(false);
                credit.setMotif("Ancien client avec un risque modérable ");
                credit.setSTATUS(Status.ENCOURSDETRAITEMENT);
                //Acceptation(credit,fund,"Ancien client avec un risque modérable ");
            }
            else
            {   credit.setSTATUS(Status.REFUS);
                credit.setMotif("Client trop Risqué Mauvais Historique");
               	//blackLIster le client
                u.setCreditAuthorization(false);
                user.save(u);

            }
        }
       else
       {
           credit.setSTATUS(Status.REFUS);
           credit.setMotif("Interdiction de Crédit");
       }




        creditLibre.save(credit);
        return credit;
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
    public Amortissement[] TabAmortissement(CreditLibre cr)
    {


        double interet=cr.getTauxInteret()/12;
        System.out.println("intereeeeeeeeeeeettt");
        System.out.println(cr.getTauxInteret());
        int leng=(int) (cr.getDuree()*12);
        System.out.println("perioddeeeeeeee");
        System.out.println(cr.getDuree()*12);
        System.out.println(leng);

        Amortissement[] ListAmortissement =new Amortissement[leng];

        Amortissement amort=new Amortissement() ;
        //System.out.println(cr.getAmount());


        amort.setMontantR(cr.getMontantCredit());
        float mensualite=(float) ((cr.getMontantCredit()*interet)/(1-(Math.pow((1+interet),-leng ))));
        amort.setMensualite(mensualite);
        amort.setInterest(amort.getMontantR()*interet);
        amort.setAmortissement(amort.getMensualite()-amort.getInterest());
        ListAmortissement[0]=amort;

        //System.out.println(ListAmortissement[0]);
        for (int i=1;i< cr.getDuree()*12;i++) {
            Amortissement amortPrecedant=ListAmortissement[i-1];
            Amortissement amortNEW=new Amortissement() ;
            amortNEW.setMontantR(amortPrecedant.getMontantR()-amortPrecedant.getAmortissement());
            amortNEW.setInterest(amortNEW.getMontantR()*interet);
            amortNEW.setMensualite(Calcul_mensualite(cr));
            amortNEW.setAmortissement(amortNEW.getMensualite()-amortNEW.getInterest());
            ListAmortissement[i]=amortNEW;

        }



        return ListAmortissement;
    }
    @Override
    public Amortissement Simulateur(CreditLibre credit)

    {   System.out.println(credit.getMontantCredit());
        Amortissement simulator =new Amortissement();
        //mnt total
        simulator.setMontantR(0);

        //mnt interet
        simulator.setInterest(0);
        //mnt monthly
        simulator.setMensualite(Calcul_mensualite(credit));

        Amortissement[] Credittab = TabAmortissement(credit);
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
