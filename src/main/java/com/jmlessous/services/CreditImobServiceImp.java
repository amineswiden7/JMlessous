package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditImobRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import com.sun.org.apache.xpath.internal.operations.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CreditImobServiceImp implements ICreditImobService {

    @Autowired
    CreditImobRepository cr;
    @Autowired
    CompteCourantRepository cp;
    @Autowired
    UtilisateurRepository u;

    @Override
    public List<CreditImmobilier> retrieveAllCredit() {
        return (List<CreditImmobilier>) cr.findAll();
    }

    @Override
    public CreditImmobilier addCredit(CreditImmobilier a) {
        a.setLeMontantDeLaTransaction(a.getApportPersonnel()+a.getMontantCredit());
        a.setDateDemande(new Date());
        float RatioAp = a.getApportPersonnel() / a.getLeMontantDeLaTransaction();
        //Utilisateur uu = u.findById(id_User).orElse(null);
        //CompteCourant c = cp.getCompteByUser(id_User);
        //a.setCompteCredit(c);
        if(RatioAp<0.2){
            a.setSTATUS(Status.REFUS);
            return a;
        }
        else {

            switch (a.getProduit()) {
                case "terrain": {
                    a.setTauxNominal((float) 0.05);
                    a.setMensualite(Calcul_mensualite(a));
                    float NouTaux = calculTauxSim(a);
                    if (NouTaux == -1) {
                        a.setSTATUS(Status.REFUS);
                        return a;
                    } else { if (RatioAp >= 0.3 && RatioAp <= 0.5) {
                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                    } else {
                        Float R = Math.max(NouTaux - (RatioAp / 100), 0);

                        a.setTauxNominal(a.getTauxNominal() + R);


                    }

                        if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                            a.setMensualite(Calcul_mensualite(a));
                            a.setSTATUS(Status.ACCEPTE);
                        } else
                            a.setSTATUS(Status.REFUS);



                    }


                }
                break;

                case "Maison": {

                    switch (a.getDestination()) {
                        case "résidence principale": {
                            RatioAp =a.getMontantReventResidencePrincipal()/a.getLeMontantDeLaTransaction()+RatioAp;

                            a.setTauxNominal((float) 0.05);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTauxSim(a);
                            if(NouTaux==-1){
                                a.setSTATUS(Status.REFUS);
                                return a;
                            }
                            else {
                                if (RatioAp >= 0.4 && RatioAp <= 0.6) {
                                    a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                } else {
                                    Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                    a.setTauxNominal(a.getTauxNominal() + R);


                                }

                                if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                    a.setMensualite(Calcul_mensualite(a));
                                    a.setSTATUS(Status.ACCEPTE);
                                } else
                                    a.setSTATUS(Status.REFUS);
                            }


                            //fonction qui retourne le nouveau taux (scoring)
                            // -1 ou taux

                        }
                        break;

                        case "résidence secondaire": {

                            a.setTauxNominal((float) 0.07);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTauxSim(a);
                            if (NouTaux == -1) {
                                a.setSTATUS(Status.REFUS);
                                return a;
                            } else { if (RatioAp >= 0.2 && RatioAp <= 0.4) {
                                a.setTauxNominal(a.getTauxNominal() + NouTaux);
                            } else {
                                Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                a.setTauxNominal(a.getTauxNominal() + R);


                            }

                                if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                    a.setMensualite(Calcul_mensualite(a));
                                    a.setSTATUS(Status.ACCEPTE);
                                } else
                                    a.setSTATUS(Status.REFUS);



                            }
                        }
                        break;
                        case "location": {

                            a.setTauxNominal((float) 0.08);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTauxSim(a);
                            if (NouTaux == -1) {
                                a.setSTATUS(Status.REFUS);
                                return a;
                            } else { if (RatioAp >= 0.15 && RatioAp <= 0.35) {
                                a.setTauxNominal(a.getTauxNominal() + NouTaux);
                            } else {
                                Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                a.setTauxNominal(a.getTauxNominal() + R);


                            }

                                if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                    a.setMensualite(Calcul_mensualite(a));
                                    a.setSTATUS(Status.ACCEPTE);
                                } else
                                    a.setSTATUS(Status.REFUS);



                            }



                        }
                        break;


                    }

                }
                break;
                case "appartement": {
                    switch (a.getDestination()) {
                        case "résidence principale": {
                            RatioAp = a.getMontantReventResidencePrincipal() / a.getLeMontantDeLaTransaction() + RatioAp;

                            a.setTauxNominal((float) 0.05);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTauxSim(a);
                            if (NouTaux == -1) {
                                a.setSTATUS(Status.REFUS);
                                return a;
                            } else {
                                if (RatioAp >= 0.4 && RatioAp <= 0.6) {
                                    a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                } else {
                                    Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                    a.setTauxNominal(a.getTauxNominal() + R);


                                }

                                if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                    a.setMensualite(Calcul_mensualite(a));
                                    a.setSTATUS(Status.ACCEPTE);
                                } else
                                    a.setSTATUS(Status.REFUS);
                            }


                            //fonction qui retourne le nouveau taux (scoring)
                            // -1 ou taux

                        }
                        break;
                        case "résidence secondaire": {
                            a.setTauxNominal((float) 0.07);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTauxSim(a);
                            if (NouTaux == -1) {
                                a.setSTATUS(Status.REFUS);
                                return a;
                            } else {
                                if (RatioAp >= 0.2 && RatioAp <= 0.4) {
                                    a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                } else {
                                    Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                    a.setTauxNominal(a.getTauxNominal() + R);


                                }

                                if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                    a.setMensualite(Calcul_mensualite(a));
                                    a.setSTATUS(Status.ACCEPTE);
                                } else
                                    a.setSTATUS(Status.REFUS);


                            }
                        }
                        break;
                        case "location": {

                            a.setTauxNominal((float) 0.08);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTauxSim(a);
                            if (NouTaux == -1) {
                                a.setSTATUS(Status.REFUS);
                                return a;
                            } else {
                                if (RatioAp >= 0.15 && RatioAp <= 0.35) {
                                    a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                } else {
                                    Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                    a.setTauxNominal(a.getTauxNominal() + R);


                                }

                                if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                    a.setMensualite(Calcul_mensualite(a));
                                    a.setSTATUS(Status.ACCEPTE);
                                } else
                                    a.setSTATUS(Status.REFUS);


                            }
                        }


                        break;
                    }

                }
                break;


            }

        }

        return a;
    }

    @Override
    public List<Credit> retrieveCreditByUser(Long id_user) {
        return null;
    }


    @Override
    public Credit Archive(Long Id_credit) {
        return null;
    }

    @Override
    public Credit retrieveActiveCredit(Long id_User) {


        return cr.getActiveCreditImobByUser(id_User) ;
    }

    @Override
    public float Calcul_mensualite(CreditImmobilier cr) {
        float montantt = cr.getMontantCredit();
        //float tauxmensuell=a.getTaux()/12;
        float tauxmensuell = cr.getTauxNominal() / 12;
        float periodd = cr.getDuree() * 12;
        float mensualitee = (float) ((montantt * tauxmensuell) / (1 - (Math.pow((1 + tauxmensuell), -periodd))));
        cr.setDateFin(java.sql.Date.valueOf(Instant.ofEpochMilli(cr.getDateDemande().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusMonths((long) (periodd))));


        return (float) ((montantt * tauxmensuell) / (1 - (Math.pow((1 + tauxmensuell), -periodd))));
    }

    @Override
    public CreditImmobilier addCreditt(CreditImmobilier a, Long id_User) {
        CreditImmobilier ce = cr.getActiveCreditImobByUser(id_User);//
        if(ce!=null) {
            a.setMotif("vous ne pouvez pas demander un credit ");
            a.setSTATUS(Status.REFUS);
            a.setFinC(Boolean.TRUE);
        }
            a.setLeMontantDeLaTransaction(a.getApportPersonnel() + a.getMontantCredit());
            a.setDateDemande(new Date());
            float RatioAp = a.getApportPersonnel() / a.getLeMontantDeLaTransaction();
            Utilisateur uu = u.findById(id_User).orElse(null);
            CompteCourant c = cp.getCompteByUser(id_User);
            a.setCompteCredit(c);
            if (RatioAp < 0.2) {// loi de finance
                a.setSTATUS(Status.REFUS);
                a.setFinC(Boolean.TRUE);
                return a;
            } else {

                switch (a.getProduit()) {
                    case "terrain": {
                        a.setTauxNominal((float) 0.05);
                        a.setMensualite(Calcul_mensualite(a));
                        float NouTaux = calculTaux(a);
                        if (NouTaux == -1) {
                            a.setSTATUS(Status.REFUS);
                            return a;
                        } else {
                            if (RatioAp >= 0.3 && RatioAp <= 0.5) {
                                a.setTauxNominal(a.getTauxNominal() + NouTaux);
                            } else {
                                Float R = Math.max(NouTaux - (RatioAp / 100), 0);

                                a.setTauxNominal(a.getTauxNominal() + R);


                            }

                            if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                a.setMensualite(Calcul_mensualite(a));
                                a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                            } else
                                a.setSTATUS(Status.REFUS);
                            a.setFinC(Boolean.TRUE);


                        }


                    }
                    break;

                    case "Maison": {

                        switch (a.getDestination()) {
                            case "résidence principale": {
                                RatioAp = a.getMontantReventResidencePrincipal() / a.getLeMontantDeLaTransaction() + RatioAp;

                                a.setTauxNominal((float) 0.05);
                                a.setMensualite(Calcul_mensualite(a));
                                float NouTaux = calculTaux(a);
                                if (NouTaux == -1) {
                                    a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                    return cr.save(a);
                                } else {
                                    if (RatioAp >= 0.4 && RatioAp <= 0.6) {
                                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                    } else {
                                        Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                        a.setTauxNominal(a.getTauxNominal() + R);


                                    }

                                    if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                        a.setMensualite(Calcul_mensualite(a));
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                                    } else
                                        a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                }


                                //fonction qui retourne le nouveau taux (scoring)
                                // -1 ou taux

                            }
                            break;

                            case "résidence secondaire": {

                                a.setTauxNominal((float) 0.07);
                                a.setMensualite(Calcul_mensualite(a));
                                float NouTaux = calculTaux(a);
                                if (NouTaux == -1) {
                                    a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                    return cr.save(a);
                                } else {
                                    if (RatioAp >= 0.2 && RatioAp <= 0.4) {
                                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                    } else {
                                        Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                        a.setTauxNominal(a.getTauxNominal() + R);


                                    }

                                    if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                        a.setMensualite(Calcul_mensualite(a));
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                                    } else
                                        a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);

                                }
                            }
                            break;
                            case "location": {

                                a.setTauxNominal((float) 0.08);
                                a.setMensualite(Calcul_mensualite(a));
                                float NouTaux = calculTaux(a);
                                if (NouTaux == -1) {
                                    a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                    return cr.save(a);
                                } else {
                                    if (RatioAp >= 0.15 && RatioAp <= 0.35) {
                                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                    } else {
                                        Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                        a.setTauxNominal(a.getTauxNominal() + R);


                                    }

                                    if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                        a.setMensualite(Calcul_mensualite(a));
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                                    } else
                                        a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);


                                }


                            }
                            break;


                        }

                    }
                    break;
                    case "appartement": {
                        switch (a.getDestination()) {
                            case "résidence principale": {
                                RatioAp = a.getMontantReventResidencePrincipal() / a.getLeMontantDeLaTransaction() + RatioAp;

                                a.setTauxNominal((float) 0.05);
                                a.setMensualite(Calcul_mensualite(a));
                                float NouTaux = calculTaux(a);
                                if (NouTaux == -1) {
                                    a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                    return a;
                                } else {
                                    if (RatioAp >= 0.4 && RatioAp <= 0.6) {
                                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                    } else {
                                        Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                        a.setTauxNominal(a.getTauxNominal() + R);
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);


                                    }

                                    if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                        a.setMensualite(Calcul_mensualite(a));
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                                    } else
                                        a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                }


                                //fonction qui retourne le nouveau taux (scoring)
                                // -1 ou taux

                            }
                            break;
                            case "résidence secondaire": {
                                a.setTauxNominal((float) 0.07);
                                a.setMensualite(Calcul_mensualite(a));
                                float NouTaux = calculTaux(a);
                                if (NouTaux == -1) {
                                    a.setSTATUS(Status.REFUS);
                                    a.setFinC(Boolean.TRUE);
                                    return a;
                                } else {
                                    if (RatioAp >= 0.2 && RatioAp <= 0.4) {
                                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                    } else {
                                        Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                        a.setTauxNominal(a.getTauxNominal() + R);
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);


                                    }

                                    if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                        a.setMensualite(Calcul_mensualite(a));
                                        a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                                        a.setFinC(Boolean.TRUE);
                                    } else
                                        a.setSTATUS(Status.REFUS);
                                    a.setMotif("Montant pret à payer incorrect");
                                    a.setFinC(Boolean.TRUE);


                                }
                            }
                            break;
                            case "location": {

                                a.setTauxNominal((float) 0.08);
                                a.setMensualite(Calcul_mensualite(a));
                                float NouTaux = calculTaux(a);
                                if (NouTaux == -1) {
                                    a.setSTATUS(Status.REFUS);
                                    a.setMotif("Risque très élevé");
                                    return a;
                                } else {
                                    if (RatioAp >= 0.15 && RatioAp <= 0.35) {
                                        a.setTauxNominal(a.getTauxNominal() + NouTaux);
                                    } else {
                                        Float R = Math.max(NouTaux - (RatioAp / 200), 0);

                                        a.setTauxNominal(a.getTauxNominal() + R);


                                    }

                                    if (a.getMontantMensuelPretPayer() - Calcul_mensualite(a) >= 0) {
                                        a.setMensualite(Calcul_mensualite(a));
                                        a.setSTATUS(Status.ACCEPTE);
                                        a.setFinC(Boolean.FALSE);
                                    } else
                                        a.setSTATUS(Status.REFUS);
                                    a.setMotif("Montant pret à payer incorrect");
                                    a.setFinC(Boolean.TRUE);


                                }
                            }


                            break;
                        }

                    }
                    break;


                }

            }


            return cr.save(a);

        }



    @Override
    public float calculTaux(CreditImmobilier a) {
        float Ratio = a.getMensualite() / (a.getCompteCredit().getUtilisateurC().getSalaire() - a.getChargeMensuel());//reglementation
        float aa=0;
        if (Ratio <= 0.5) {
            switch (a.getLocalisation()) {
                case LesCôtes:
                    aa = 0;
                case BanlieueSud:
                    aa = 10;
                case Tunis:
                    aa = 20;
                case BanlieueNord:
                    aa = 30;
            }

            return Ratio / (20 + aa);



        } else return -1;


    }

    @Override
    public float calculTauxSim(CreditImmobilier a) {
        float Ratio = a.getMensualite() / (a.getMontantMensuelPretPayer() - a.getChargeMensuel());
        float aa=0;
        if (Ratio <= 0.5) {
            switch (a.getLocalisation()) {
                case LesCôtes:
                    aa = 0;
                case BanlieueSud:
                    aa = 10;
                case Tunis:
                    aa = 20;
                case BanlieueNord:
                    aa = 30;
            }

            return Ratio / (20 + aa);



        } else return -1;

    }

    @Override
    public Amortissement[] TabAmortissementt(CreditImmobilier cr) {
        CreditImmobilier c=addCredit(cr);
        double interest=c.getTauxNominal()/12;
        System.out.println("intereettt");
        System.out.println(c.getTauxNominal());
        int leng=(int) (c.getDuree()*12);
        System.out.println("periode");
        System.out.println(c.getDuree()*12);
        System.out.println(leng);

        Amortissement[] ListAmortissement =new Amortissement[leng];

        Amortissement amort=new Amortissement() ;
        //System.out.println(cr.getAmount());


        amort.setMontantR(c.getMontantCredit());
        amort.setMensualite(Calcul_mensualite(c));
        amort.setInterest(amort.getMontantR()*interest);
        amort.setAmortissement(amort.getMensualite()-amort.getInterest());
        ListAmortissement[0]=amort;

        //System.out.println(ListAmortissement[0]);
        for (int i=1;i< c.getDuree()*12;i++) {
            Amortissement amortPrecedant=ListAmortissement[i-1];
            Amortissement amortNEW=new Amortissement() ;
            amortNEW.setMontantR(amortPrecedant.getMontantR()-amortPrecedant.getAmortissement());
            amortNEW.setInterest(amortNEW.getMontantR()*interest);
            amortNEW.setMensualite(Calcul_mensualite(cr));
            amortNEW.setAmortissement(amortNEW.getMensualite()-amortNEW.getInterest());
            ListAmortissement[i]=amortNEW;

        }



        return ListAmortissement;
    }

    @Override
    public Amortissement Simulateurr(CreditImmobilier cr) {
        CreditImmobilier credit=addCredit(cr);
        System.out.println(credit.getMontantCredit());
        Amortissement simulator =new Amortissement();
        //mnt total
        simulator.setMontantR(0);

        //mnt interet
        simulator.setInterest(0);
        //mnt monthly
        simulator.setMensualite(Calcul_mensualite(credit));

        Amortissement[] Credittab = TabAmortissementt(credit);
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

    @Override
    public void TraitementCredit() {
        List<CreditImmobilier> Credittab = cr.getCredit();
        for (int i=0; i < Credittab.size(); i++) {
            if(Credittab.get(i).getCompteCredit().getUtilisateurC().getCreditAuthorization() == true){
                Credittab.get(i).setSTATUS(Status.ACCEPTE);
                //Credittab.get(i).s();
            }
            else  Credittab.get(i).setSTATUS(Status.REFUS);

        }

    }


}
