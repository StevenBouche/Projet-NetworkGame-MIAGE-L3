using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Share.Network.Message.modele
{
    public class Enigme
    {
        public String label;
        public Category category;
        public int size;
        public int consonantNb;
        public int vowelNb;
        public List<char> order;

        public Enigme(String labelE, Category categoryE)
        {
            this.label = labelE;
            this.category = categoryE;
            this.size = label.Length;
            this.vowelNb = calculateVowelNb(label);
            this.consonantNb = size - vowelNb - getNbOfSpace(label);
            this.order = determineOrder(label);
        }

        private int calculateVowelNb(String label)
        {
            int count = 0;

            var vowels = new HashSet<char> { 'A', 'E', 'I', 'O', 'U', 'Y' };

            for(int i =0; i<label.Length; i++)
            {
                if (vowels.Contains(label[i]))
                {
                    count++;
                }
            }

            return count;
        }

        private int getNbOfSpace(String label)
        {
            int res=0;
            
            for(int i = 0; i<label.Length; i++)
            {
                if(label[i] == ' ')
                {
                    res++;
                }
            }

            return res;
        }

        private List<char> determineOrder(String label)
        {
            List<char> uniqueLetters = new List<char>();
            

            //Gather all differents letters in uniqueLetters
            for(int i=0; i<label.Length; i++)
            {   
                if (!uniqueLetters.Contains(label[i]) && label[i] != ' ')
                {
                    uniqueLetters.Add(label[i]);
                }
            }
            
            Random r = new Random();

            //Shuffle uniqueLetter
            int n = uniqueLetters.Count;
            while (n > 1)
            {
                n--;
                int k = r.Next(n + 1);
                char value = uniqueLetters[k];
                uniqueLetters[k] = uniqueLetters[n];
                uniqueLetters[n] = value;
            }

            return uniqueLetters;
        }

        public String toString()
        {
            String o = "";

            foreach(char c in this.order)
            {
                o += c + ", ";
            }
            
            String l = "Engime label : " + this.label + " Category " + this.category + " Taille : " + this.size + "\n";
            String l2 = " Nb consonnes : " + this.consonantNb + " Nb voyelles : " + this.vowelNb + " Ordre : " + o;
            
            return l + l2;
        }

    }
}