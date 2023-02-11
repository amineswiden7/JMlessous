package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditEtuRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

@Service
public class CreditEtuServiceImp implements ICreditEtu {


    @Autowired
    CreditEtuRepository etu;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    CompteCourantRepository cc;


    private static final DecimalFormat df = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.US));

    @Override
    public List<CreditEtudiant> retrieveAllCredit() {

        return (List<CreditEtudiant>) etu.findAll();
    }

    @Override
    public CreditEtudiant addCredit(CreditEtudiant a, Long idUser) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUser).orElse(null);
        CreditEtudiant ce = etu.getActiveCreditByUser(idUser);
        CompteCourant c = (CompteCourant) cc.getCompteByUser(idUser);
        a.setCompteCredit(c);

        if (utilisateur.getProfession() == Profession.ETUDIANT) {
            if (ce == null) {
                if (a.getNiveauEtude() == NiveauEtude.INGENIERIE) {
                    a.setTauxInteret((float) 0.03);


                } else if (a.getNiveauEtude() == NiveauEtude.MASTER) {
                    a.setTauxInteret((float) 0.025);

                } else
                    a.setTauxInteret((float) 0.02);
                float montant = a.getMontantCredit();
                float tauxmensuel = a.getTauxInteret() / 12;
                float period = a.getDuree() * 12;
                float mensualite = (float) ((montant * tauxmensuel) / (1 - (Math.pow((1 + tauxmensuel), -period))));
                double sc = Scoring(montant, period, a.getNiveauEtude());
                a.setScore(sc);
                a.setMensualite(mensualite);
                a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                a.setFinC(false);

                a.setDateDemande(new Date());
                a.setCompteCredit((CompteCourant) cc.getCompteByUser(idUser));
               // etu.save(a);
                if(a.getScore()<30){
                    a.setSTATUS(Status.REFUS);
                    a.setMotif("Risque trés éleve ");

                }
                return a;
            }
        }


        return null;

    }

    @Override
    public CreditEtudiant addCreditSim(CreditEtudiant a) {
        if (a.getNiveauEtude() == NiveauEtude.INGENIERIE) {
            a.setTauxInteret((float) 0.03);


        } else if (a.getNiveauEtude() == NiveauEtude.MASTER) {
            a.setTauxInteret((float) 0.025);

        } else
            a.setTauxInteret((float) 0.02);
        float montant = a.getMontantCredit();
        float tauxmensuel = a.getTauxInteret() / 12;
        float period = a.getDuree() * 12;
        float mensualite = (float) ((montant * tauxmensuel) / (1 - (Math.pow((1 + tauxmensuel), -period))));
        double sc = Scoring(montant, period, a.getNiveauEtude());
        a.setScore(sc);
        a.setMensualite(mensualite);
        a.setSTATUS(Status.ENCOURSDETRAITEMENT);
        a.setFinC(false);

        a.setDateDemande(new Date());
       // a.setCompteCredit(cc.getCompteByUser(idUser));
        //etu.save(a);
        if(a.getScore()<30){
            a.setSTATUS(Status.REFUS);
            a.setMotif("Risque trés éleve ");

        }
        return a;

    }

    @Override
    public Amortissement Simulateurr(CreditEtudiant cr) {
        CreditEtudiant credit=addCreditSim(cr);
        System.out.println(credit.getMontantCredit());
        Amortissement simulator =new Amortissement();
        //mnt total
        simulator.setMontantR(0);

        //mnt interet
        simulator.setInterest(0);
        //mnt monthly
        simulator.setMensualite(credit.getTauxInteret());

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
    public Amortissement[] TabAmortissementt(CreditEtudiant cr) {
        CreditEtudiant c=addCreditSim(cr);
        double interest=c.getTauxInteret()/12;
        System.out.println("intereettt");
        System.out.println(c.getTauxInteret());
        int leng=(int) (c.getDuree()*12);
        System.out.println("periode");
        System.out.println(c.getDuree()*12);
        System.out.println(leng);

        Amortissement[] ListAmortissement =new Amortissement[leng];

        Amortissement amort=new Amortissement() ;
        //System.out.println(cr.getAmount());


        amort.setMontantR(c.getMontantCredit());
        amort.setMensualite(c.getMensualite());
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
    public List<CreditEtudiant> retrieveCreditByUser(Long id_user) {
        return etu.getCreditByUser(id_user);
    }

    @Override
    public CreditEtudiant Archive(Long Id_credit) {
        return null;
    }

    @Override
    public CreditEtudiant retrieveActiveCredit(Long id_User) {
        return etu.getActiveCreditByUser(id_User);
    }

    @Override
    public float Calcul_mensualite(CreditEtudiant cr) {
        return 0;
    }

    @Override
    public void ActiverCredit(Long a) {
        CreditEtudiant cr = etu.findById(a).orElse(null);
        if (cr.getSTATUS() == Status.ENCOURSDETRAITEMENT) {
            cr.setSTATUS(Status.ACCEPTE);
            float b =cr.getCompteCredit().getSolde();
            cr.getCompteCredit().setSolde(b+cr.getMontantCredit());
            etu.save(cr);


        }

    }

    @Override
    public Hashtable<String, Double> simulation(float montant, float periode, NiveauEtude typeEtudiant) {
        Hashtable<String, Double> sim = new Hashtable<String, Double>();
        double mensuality;
        double MontantRestant;
        MontantRestant = montant;
        double interest = 0.01;//score personel manquant
        //interest=interest/100;
        // System.out.println("interest "+ interest );
        switch (typeEtudiant) {
            case LICENCE: {
                interest = 0.02;
                System.out.println("interet " + interest);
                mensuality = (montant * interest) / (1 - Math.pow(1 + interest, -periode));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensualite : " + Double.parseDouble(df.format(mensuality)));
                System.out.println("MontantRestant : " + montant);
                sim.put("MontantRestant 1", MontantRestant);
                sim.put("Interet 1", MontantRestant * interest);
                sim.put("Principal 1", mensuality - (MontantRestant * interest));
                //  System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                // System.out.println("CRD 1 :"+df.format(crd));

                for (int i = 2; i <= periode; i++) {
                    double interet = MontantRestant * interest;
                    sim.put("interet " + i, interet);
                    double principal = mensuality - interet;
                    sim.put("Principal " + i, principal);
                    MontantRestant = MontantRestant - principal;
                    sim.put("MontantRestant " + i, Double.parseDouble(df.format(MontantRestant)));
                    System.out.println("MontantRestant " + i + " : " + Double.parseDouble(df.format(MontantRestant)));
                }
            }
            break;
            case MASTER: {
                interest = 0.025;
                System.out.println("interest " + interest);
                mensuality = (montant * interest) / (1 - Math.pow(1 + interest, -periode));

                sim.put("Mensualite", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensualite : " + mensuality);
                System.out.println("MontantRestant : " + montant);
                sim.put("MontantRestant 1", MontantRestant);
                System.out.println("MontantRestant 1 :" + Double.parseDouble(df.format(MontantRestant)));
                sim.put("Interet 1", MontantRestant * interest);
                sim.put("Principal 1", mensuality - (MontantRestant * interest));

                for (int i = 2; i <= periode; i++) {
                    double interet = MontantRestant * interest;
                    sim.put("Interet " + i, interet);
                    double principal = mensuality - interet;
                    sim.put("Pprincipal " + i, principal);
                    MontantRestant = MontantRestant - principal;
                    sim.put("MontantRestant " + i, MontantRestant);
                    System.out.println("MontantRestant " + i + " : " + Double.parseDouble(df.format(MontantRestant)));
                }
            }
            break;
            case INGENIERIE: {
                interest = 0.03;
                System.out.println("interest " + interest);
                mensuality = (montant * interest) / (1 - Math.pow(1 + interest, -periode));

                sim.put("Mensualite", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensualite : " + mensuality);
                System.out.println("montant : " + montant);
                sim.put("MontantRestant 1", MontantRestant);
                System.out.println("MontantRestant 1 :" + Double.parseDouble(df.format(MontantRestant)));
                sim.put("Interet 1", MontantRestant * interest);
                sim.put("Principal 1", mensuality - (MontantRestant * interest));

                for (int i = 2; i <= periode; i++) {
                    MontantRestant = MontantRestant - (mensuality - (MontantRestant * interest));
                    sim.put("MontantRestant " + i, MontantRestant);
                    System.out.println("MontantRestant " + i + " : " + Double.parseDouble(df.format(MontantRestant)));
                }
            }
            break;
            default: {
                System.out.println("Niveau d'etude incorrect ");
            }
        }


        return sim;
    }

    @Override
    public Double Scoring(float montant, float periode, NiveauEtude typeEtu) {
        double score = 0;
        double revenuprévu = 0;
        double taux = 0;
        double mensualite = 0;
        switch (typeEtu) {
            case LICENCE: {
                revenuprévu = 800;
                taux = (float) 0.03;


            }break;
            case MASTER: {
                revenuprévu = 1000;
                taux = (float) 0.025;


            }break;
            case INGENIERIE: {
                revenuprévu = 1500;
                taux = (float) 0.02;
            } break;
        default: {score = 0;}
    }

            mensualite = (montant * taux) / (1 - Math.pow(1 + taux, -periode * 12));
            if (montant > 10000)
                score = 0;
            else if (montant <= 10000)
                score = 100 * (1-(mensualite / revenuprévu));



        System.out.println(revenuprévu);
        System.out.println(taux);
        System.out.println("Mensualite : " + Double.parseDouble(df.format(mensualite)));


        return score;
    }

    @Override
    public CreditEtudiant transemtre(CreditEtudiant a) {
        return etu.save(a);
    }

    @Override
    public void acceptercredit(Long id) {
        CreditEtudiant a = etu.findById(id).orElse(null);
        if(a.getSTATUS()==Status.ENCOURSDETRAITEMENT){
            a.setSTATUS(Status.ACCEPTE);
            //a.setFinC(true);
            CompteCourant com= a.getCompteCredit();
            com.setSolde(com.getSolde()+a.getMontantCredit());
            cc.save(com);
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("bkfinpi@gmail.com");
            message.setTo(com.getUtilisateurC().getEmail());
            message.setText("votre demande de prêt a été acceptée");
            message.setSubject("service de credit ");

            mailSender.send(message);
            System.out.println("Mail Send...");




        }
        etu.save(a);
    }

    @Override
    public void Refusercredit(Long id) {
        CreditEtudiant a = etu.findById(id).orElse(null);
        if(a.getSTATUS()==Status.ENCOURSDETRAITEMENT){
            a.setSTATUS(Status.REFUS);

            a.setFinC(true);
            CompteCourant com= a.getCompteCredit();
            a.setMotif("votre crédit est trop risqué");
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("bkfinpi@gmail.com");
            message.setTo(com.getUtilisateurC().getEmail());
            message.setText("votre demande de prêt a été réfusée " +
                    "Merci de consulter notre site Web pour suivre votre Credit ");
            message.setSubject("service de credit ");

            mailSender.send(message);
            System.out.println("Mail Send...");

        }
        etu.save(a);
    }

    }









