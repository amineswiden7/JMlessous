package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditEtuRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        CompteCourant c = cc.getCompteByUser(idUser);

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
                a.setMensualite(mensualite);
                a.setSTATUS(Status.ENCOURSDETRAITEMENT);
                a.setFinC(false);

                a.setDateDemande(new Date());
                a.setCompteCredit(cc.getCompteByUser(idUser));
                etu.save(a);
                return a;
            }
        }


        return null;

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
            etu.save(cr);


        }

    }

    @Override
    public Hashtable<String, Double> simulation(float montant, float periode, NiveauEtude typeEtudiant) {
        Hashtable<String, Double> sim = new Hashtable<String, Double>();
        double mensuality;
        double crd;
        crd = montant;
        double interest = 0.01;//score personel manquant
        //interest=interest/100;
        // System.out.println("interest "+ interest );
        switch (typeEtudiant) {
            case LICENCE: {
                interest = 0.02;
                System.out.println("interest " + interest);
                mensuality = (montant * interest) / (1 - Math.pow(1 + interest, -periode));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : " + Double.parseDouble(df.format(mensuality)));
                System.out.println("montant : " + montant);
                sim.put("CRD 1", crd);
                sim.put("I 1", crd * interest);
                sim.put("P 1", mensuality - (crd * interest));
                //  System.out.println("CRD 1 :"+Double.parseDouble(df.format(crd)));
                // System.out.println("CRD 1 :"+df.format(crd));

                for (int i = 2; i <= periode; i++) {
                    double interet = crd * interest;
                    sim.put("I " + i, interet);
                    double principal = mensuality - interet;
                    sim.put("P " + i, principal);
                    crd = crd - principal;
                    sim.put("CRD " + i, Double.parseDouble(df.format(crd)));
                    System.out.println("CRD " + i + " : " + Double.parseDouble(df.format(crd)));
                }
            }
            break;
            case MASTER: {
                interest = 0.025;
                System.out.println("interest " + interest);
                mensuality = (montant * interest) / (1 - Math.pow(1 + interest, -periode));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : " + mensuality);
                System.out.println("amount : " + montant);
                sim.put("CRD 1", crd);
                System.out.println("CRD 1 :" + Double.parseDouble(df.format(crd)));
                sim.put("I 1", crd * interest);
                sim.put("P 1", mensuality - (crd * interest));

                for (int i = 2; i <= periode; i++) {
                    double interet = crd * interest;
                    sim.put("I " + i, interet);
                    double principal = mensuality - interet;
                    sim.put("P " + i, principal);
                    crd = crd - principal;
                    sim.put("CRD " + i, crd);
                    System.out.println("CRD " + i + " : " + Double.parseDouble(df.format(crd)));
                }
            }
            break;
            case INGENIERIE: {
                interest = 0.03;
                System.out.println("interest " + interest);
                mensuality = (montant * interest) / (1 - Math.pow(1 + interest, -periode));

                sim.put("Mensuality", Double.parseDouble(df.format(mensuality)));
                System.out.println("Mensuality : " + mensuality);
                System.out.println("amount : " + montant);
                sim.put("CRD 1", crd);
                System.out.println("CRD 1 :" + Double.parseDouble(df.format(crd)));
                sim.put("I 1", crd * interest);
                sim.put("P 1", mensuality - (crd * interest));

                for (int i = 2; i <= periode; i++) {
                    crd = crd - (mensuality - (crd * interest));
                    sim.put("CRD " + i, crd);
                    System.out.println("CRD " + i + " : " + Double.parseDouble(df.format(crd)));
                }
            }
            break;
            default: {
                System.out.println("Invalid Type");
            }
        }


        return sim;
    }


}





