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
        public char[] order;

        public Enigme(String labelE, Category categoryE)
        {
            this.label = labelE;
            this.category = categoryE;
            this.size = label.Length;
            this.vowelNb = calculateVowelNb(label);
            this.consonantNb = size - vowelNb;
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

        private char[] determineOrder(String label)
        {
            char[] uniqueLetters = { };
            

            //Gather all differents letters in res[]
            for(int i=0; i<label.Length; i++)
            {
                if (!uniqueLetters.Contains(label[i]))
                {
                    uniqueLetters[i] = label[i];
                }
            }
            
            Random r = new Random();
            int randPos;
            char[] randomArray = new char[uniqueLetters.Length];

            //Shuffle res[] in randomArray[]
            for (int i = uniqueLetters.Length; i>= 1; i--)
            {
                randPos = r.Next(1, i + 1) - 1;
                randomArray[i - 1] = uniqueLetters[randPos];
                uniqueLetters[randPos] = uniqueLetters[i - 1];
            }

            return randomArray;
        }

    }
}
