package com.jmlessous.services;

import com.jmlessous.entities.*;
import com.jmlessous.repositories.CompteCourantRepository;
import com.jmlessous.repositories.CreditImobRepository;
import com.jmlessous.repositories.UtilisateurRepository;
import com.sun.org.apache.xpath.internal.operations.And;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CreditImobServiceImp implements ICreditImobService {

    @Autowired
    CreditImobRepository cr;
    CompteCourantRepository cp;
    UtilisateurRepository u;

    @Override
    public List<CreditImmobilier> retrieveAllCredit() {
        return (List<CreditImmobilier>) cr.findAll();
    }

    @Override
    public CreditImmobilier addCredit(Credit a, Long id_User) {
        Utilisateur uu = u.findById(id_User).orElse(null);
        CompteCourant c = cp.getCompteByUser(id_User);
        a.setCompteCredit(c);
        a.setDateDemande(new Date());

        float montant = a.getMontantCredit();
        float tauxmensuel = 12;
        float period = a.getDuree() * 12;
        float mensualite = (float) ((montant * tauxmensuel) / (1 - (Math.pow((1 + tauxmensuel), -period))));
        a.setMensualite(mensualite);
        // a.setMensualite(100F);
        float ratio = a.getMensualite() / uu.getSalaire();
        if (uu.getCreditAuthorization() == true) {   //
            //NB LE TAUX DE RISQUE 1%<R<2.5%
            if (ratio <= 0.4) {
                //CALCUL RISK
                //a.setTaux((float) (0.05+(ratio/20)));
                float montantt = a.getMontantCredit();
                //float tauxmensuell=a.getTaux()/12;
                float tauxmensuell = 12;
                float periodd = a.getDuree() * 12;
                float mensualitee = (float) ((montantt * tauxmensuell) / (1 - (Math.pow((1 + tauxmensuell), -periodd))));
                a.setMensualite(mensualitee);
                // a.setMensualite(200F);
                // uu.setCreditAuthorization(true);
                //Acceptation(credit,fund,"NouveauClient avec garant certifié");
                a.setSTATUS(Status.ACCEPTE);
                a.setDateFin(java.sql.Date.valueOf(Instant.ofEpochMilli(a.getDateDemande().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().plusMonths((long) (periodd))));


            } else {
                a.setSTATUS(Status.REFUS);

                a.setMotif("montant guarantie insuffisant il doit etre égale au minimum 40% d'écheance de crédit");
            }

        }


        return null;
    }

    @Override
    public List<Credit> retrieveCreditByUser(Long id_user) {
        return cr.getCreditByUser(id_user);
    }

    @Override
    public Credit Archive(Long Id_credit) {
        return null;
    }

    @Override
    public Credit retrieveActiveCredit(Long id_User) {
        return null;
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
        a.setLeMontantDeLaTransaction(a.getApportPersonnel()+a.getMontantCredit());
        float RatioAp = a.getApportPersonnel() / a.getLeMontantDeLaTransaction();
        Utilisateur uu = u.findById(id_User).orElse(null);
        CompteCourant c = cp.getCompteByUser(id_User);
        a.setCompteCredit(c);
        if(RatioAp<0.2){
            a.setSTATUS(Status.REFUS);
            return a;
        }
        else {

                    switch (a.getProduit()) {
                        case "terrain": {
                            a.setTauxNominal((float) 0.05);
                            a.setMensualite(Calcul_mensualite(a));
                            float NouTaux = calculTaux(a);
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
                                    float NouTaux = calculTaux(a);
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
                                    float NouTaux = calculTaux(a);
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
                                    float NouTaux = calculTaux(a);
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
                                    float NouTaux = calculTaux(a);
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
                                    float NouTaux = calculTaux(a);
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
                                    float NouTaux = calculTaux(a);
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
    public float calculTaux(CreditImmobilier a) {
        float Ratio = a.getMensualite() / (a.getCompteCredit().getUtilisateurC().getSalaire() - a.getChargeMensuel());
        float aa=0;
        if (Ratio <= 0.4) {
            switch (a.getLocalisation()) {
                case LesCôtes:
                    aa=0;
                case BanlieueSud:
                    aa=10;
                case Tunis:
                    aa=20;
                case BanlieueNord:
                    aa=30;
            }

            return Ratio / (20+aa);


        } else return -1;


    }
}
