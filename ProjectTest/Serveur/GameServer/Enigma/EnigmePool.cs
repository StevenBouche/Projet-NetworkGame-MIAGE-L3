using System;
using System.Collections.Generic;

namespace Serveur.GameServer.Enigma
{
    public class EnigmePool
    {
        public static Dictionary<Category, List<Enigme>> enigmePool { get => getEnigmePool(); }
        private static Boolean firstGet = true;
        private static Dictionary<Category, List<Enigme>> list;

        private static Dictionary<Category, List<Enigme>> getEnigmePool()
        {
            if (firstGet)
            {
                //Create musical enigmas
                List<Enigme> listEnigmeMusique = new List<Enigme>();
                Enigme e1 = new Enigme("HAPPY BIRTHDAY", Category.MUSIQUE);
                Enigme e2 = new Enigme("LES FRERES EXISTENT ENCORE", Category.MUSIQUE);
                Enigme en3 = new Enigme("AH ! SI J'ETAIS RICHE", Category.MUSIQUE);
                listEnigmeMusique.Add(e1); listEnigmeMusique.Add(e2); listEnigmeMusique.Add(en3);
                list.Add(Category.MUSIQUE, listEnigmeMusique);

                //Create job enigmas
                List<Enigme> listEnigmeJob = new List<Enigme>();
                Enigme e3 = new Enigme("ANIMATEUR", Category.METIER);
                Enigme e4 = new Enigme("MENUISIER", Category.METIER);
                listEnigmeJob.Add(e3); listEnigmeJob.Add(e4);
                list.Add(Category.METIER, listEnigmeJob);

                //Create familiary expressions enigmas
                List<Enigme> listEnigmeExpressionF = new List<Enigme>();
                Enigme e5 = new Enigme("UNE VEINE DE COCU", Category.EXPRESSION_FAMILIERE);
                Enigme e6 = new Enigme("AVOIR LA PATATE", Category.EXPRESSION_FAMILIERE);
                listEnigmeExpressionF.Add(e5); listEnigmeExpressionF.Add(e6);
                list.Add(Category.EXPRESSION_FAMILIERE, listEnigmeExpressionF);

                //Create drinks enigmas
                List<Enigme> listEnigmeDrinks = new List<Enigme>();
                Enigme e7 = new Enigme("DU CHAMPAGNE", Category.BOISSON);
                Enigme e8 = new Enigme("DU CIDRE", Category.BOISSON);
                listEnigmeDrinks.Add(e7); listEnigmeDrinks.Add(e8);
                list.Add(Category.BOISSON, listEnigmeDrinks);

                //Create things enigmas
                List<Enigme> listEnigmeThings = new List<Enigme>();
                Enigme e9 = new Enigme("UN FEU D'ARTIFICE", Category.CHOSE_MACHIN_TRUC);
                Enigme e10 = new Enigme("UN GROS POUF", Category.CHOSE_MACHIN_TRUC);
                Enigme e11 = new Enigme("UN KIT MAIN LIBRE", Category.CHOSE_MACHIN_TRUC);
                Enigme e12 = new Enigme("TAPETTE A MOUCHE", Category.CHOSE_MACHIN_TRUC);
                listEnigmeThings.Add(e9); listEnigmeThings.Add(e10); listEnigmeThings.Add(e11); listEnigmeThings.Add(e12);
                list.Add(Category.CHOSE_MACHIN_TRUC, listEnigmeThings);

                //Create TV enigmas
                List<Enigme> listEnigmeTV = new List<Enigme>();
                Enigme e13 = new Enigme("TOTALLY SPIES", Category.TELEVISION);
                Enigme e14 = new Enigme("INSPECTEUR DERRICK", Category.TELEVISION);
                listEnigmeTV.Add(e13); listEnigmeTV.Add(e14);
                list.Add(Category.TELEVISION, listEnigmeTV);

                //Create expressions enigmas
                List<Enigme> listEnigmeExpression = new List<Enigme>();
                Enigme e15 = new Enigme("AVOIR DU BOL", Category.EXPRESSION);
                Enigme e16 = new Enigme("CARAMBA !", Category.EXPRESSION);
                listEnigmeExpression.Add(e15); listEnigmeExpression.Add(e16);
                list.Add(Category.EXPRESSION, listEnigmeExpression);

                //Create catering enigmas
                List<Enigme> listEnigmeCatering = new List<Enigme>();
                Enigme e17= new Enigme("UNE TERRINE DE CAMPAGNE", Category.CUISINE);
                Enigme e18 = new Enigme("ACRAS DE MORUE", Category.CUISINE);
                Enigme e19 = new Enigme("UNE SOUPE AU PISTOU", Category.CUISINE);
                Enigme e20 = new Enigme("DES PETITS-FOURS", Category.CUISINE);
                listEnigmeCatering.Add(e17); listEnigmeCatering.Add(e18); listEnigmeCatering.Add(e19); listEnigmeCatering.Add(e20);
                list.Add(Category.CUISINE, listEnigmeCatering);

                //Create celebrities enigmas
                List<Enigme> listEnigmeCelebrities = new List<Enigme>();
                Enigme e21 = new Enigme("PIERRE RICHARD", Category.CELEBRITES);
                Enigme e22 = new Enigme("JACQUES BREL", Category.CELEBRITES);
                Enigme e23 = new Enigme("ANDY WARHOL", Category.CELEBRITES);
                listEnigmeCelebrities.Add(e21); listEnigmeCelebrities.Add(e22); listEnigmeCelebrities.Add(e23);
                list.Add(Category.CELEBRITES, listEnigmeCelebrities);

                //Create flora and fauna enigmas
                List<Enigme> listEnigmeFloraAndFauna = new List<Enigme>();
                Enigme e24 = new Enigme("UN RAGONDIN", Category.FAUNE_ET_FLORE);
                Enigme e25 = new Enigme("UN DOBERMAN", Category.FAUNE_ET_FLORE);
                listEnigmeFloraAndFauna.Add(e24); listEnigmeFloraAndFauna.Add(e25);
                list.Add(Category.FAUNE_ET_FLORE, listEnigmeFloraAndFauna);

                //Create literature enigmas
                List<Enigme> listEnigmeLiterature = new List<Enigme>();
                Enigme e26 = new Enigme("PEAU D'ANE", Category.LITTERATURE);
                Enigme e27 = new Enigme("LE PARFUM", Category.LITTERATURE);
                listEnigmeLiterature.Add(e26); listEnigmeLiterature.Add(e27);
                list.Add(Category.LITTERATURE, listEnigmeLiterature);

                //Create sport enigmas
                List<Enigme> listEnigmeSport = new List<Enigme>();
                Enigme e28 = new Enigme("LE SQUASH", Category.SPORT);
                Enigme e29 = new Enigme("LUTTE GRECO-ROMAINE", Category.SPORT);
                Enigme e30 = new Enigme("LE MARATHON DE NEW YORK", Category.SPORT);
                listEnigmeSport.Add(e28); listEnigmeSport.Add(e29); listEnigmeSport.Add(e30);
                list.Add(Category.SPORT, listEnigmeSport);

                //Create on the earth enigmas
                List<Enigme> listEnigmeOnTheEarth = new List<Enigme>();
                Enigme e31 = new Enigme("LE MONT CANIGOU", Category.SUR_LA_PLANETE);
                Enigme e32 = new Enigme("LE TRIANGLE DES BERMUDES", Category.SUR_LA_PLANETE);
                Enigme e33 = new Enigme("LE HONDURAS", Category.SUR_LA_PLANETE);
                listEnigmeOnTheEarth.Add(e31); listEnigmeOnTheEarth.Add(e32); listEnigmeOnTheEarth.Add(e33);
                list.Add(Category.SUR_LA_PLANETE, listEnigmeOnTheEarth);

                //Create known places enigmas
                List<Enigme> listEnigmeKnownPlaces = new List<Enigme>();
                Enigme e34 = new Enigme("LES HOSPICES DE BEAUNE", Category.LIEU_CONNU);
                Enigme e35 = new Enigme("LE CHRIST REDEMPTEUR", Category.LIEU_CONNU);
                listEnigmeKnownPlaces.Add(e34); listEnigmeKnownPlaces.Add(e35);
                list.Add(Category.LIEU_CONNU, listEnigmeKnownPlaces);

                //Create 7th art enigmas
                List<Enigme> listEnigme7thArt = new List<Enigme>();
                Enigme e36 = new Enigme("CINQ CENTS JOURS ENSEMBLE", Category.SEPTIEME_ART);
                Enigme e37 = new Enigme("LE GENDARME DE SAINT-TROPEZ", Category.SEPTIEME_ART);
                listEnigme7thArt.Add(e36); listEnigme7thArt.Add(e37);
                list.Add(Category.SEPTIEME_ART, listEnigme7thArt);

                //Create games & hobbies enigmas
                List<Enigme> listEnigmeGameAndHobbies = new List<Enigme>();
                Enigme e38 = new Enigme("GAGNER LE JACKPOT", Category.LOISIRS_ET_JEUX);
                Enigme e39 = new Enigme("UNE COURSE D'ORIENTATION", Category.LOISIRS_ET_JEUX);
                listEnigmeGameAndHobbies.Add(e38); listEnigmeGameAndHobbies.Add(e39);
                list.Add(Category.LOISIRS_ET_JEUX, listEnigmeGameAndHobbies);

                //Create in winter enigmas
                List<Enigme> listEnigmeInWinter = new List<Enigme>();
                Enigme e40 = new Enigme("PRENDRE TOUTE LA COUETTE", Category.EN_HIVER);
                Enigme e41 = new Enigme("LA SCULPTURE SUR GLACE", Category.EN_HIVER);
                listEnigmeInWinter.Add(e40); listEnigmeInWinter.Add(e41);
                list.Add(Category.EN_HIVER, listEnigmeInWinter);

                //Create in do-it-yourself and gardening enigmas
                List<Enigme> listEnigmeGardening = new List<Enigme>();
                Enigme e42 = new Enigme("UNE STATION DE SOUDURE", Category.BRICOLAGE_ET_JARDINAGE);
                Enigme e43 = new Enigme("MONTER UN MEUBLE", Category.BRICOLAGE_ET_JARDINAGE);
                listEnigmeGardening.Add(e42); listEnigmeGardening.Add(e43);
                list.Add(Category.BRICOLAGE_ET_JARDINAGE, listEnigmeGardening);

                //Create in-a-party enigmas
                List<Enigme> listEnigmeInAParty = new List<Enigme>();
                Enigme e44 = new Enigme("OFFRIR DES CHOCOLATS", Category.EN_SOIREE);
                Enigme e45 = new Enigme("APPELER LES POMPIERS", Category.EN_SOIREE);
                listEnigmeInAParty.Add(e44); listEnigmeInAParty.Add(e45);
                list.Add(Category.EN_SOIREE, listEnigmeInAParty);

                //Create characters enigmas
                List<Enigme> listEnigmeCharacters = new List<Enigme>();
                Enigme e46 = new Enigme("FIGARO", Category.PERSONNAGE);
                Enigme e47 = new Enigme("ASTERIX", Category.PERSONNAGE);
                Enigme e48 = new Enigme("LA PANTHERE ROSE", Category.PERSONNAGE);
                listEnigmeCharacters.Add(e46); listEnigmeCharacters.Add(e47); listEnigmeCharacters.Add(e48);
                list.Add(Category.PERSONNAGE, listEnigmeCharacters);

                //Create holidays enigmas
                List<Enigme> listEnigmeHolidays = new List<Enigme>();
                Enigme e49 = new Enigme("CONCOURS DE TEE-SHIRTS MOUILLES", Category.EN_VACANCES);
                Enigme e50 = new Enigme("PRENDRE UN APERITIF", Category.EN_VACANCES);
                listEnigmeHolidays.Add(e49); listEnigmeHolidays.Add(e50);
                list.Add(Category.PERSONNAGE, listEnigmeHolidays);

                firstGet = false;
            }
            return list;
        }

        private EnigmePool()
        {

        }
    }
}
