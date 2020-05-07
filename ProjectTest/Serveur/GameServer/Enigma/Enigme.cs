﻿using System;
using System.Collections.Generic;
using System.Text;

namespace Serveur.GameServer.Enigma
{
    public class Enigme
    {
        public String label;
        public Category category;
        public int size;
        public int consonantNb;
        public int vowelNb;

        public Enigme(String labelE, Category categoryE)
        {
            this.label = labelE;
            this.category = categoryE;
            this.size = label.Length;
            this.vowelNb = calculateVowelNb(label);
            this.consonantNb = size - vowelNb;
        }

        private int calculateVowelNb(String label)
        {
            int count = 0;

            var vowels = new HashSet<char> { 'a', 'e', 'i', 'o', 'u', 'y' };

            for(int i =0; i<label.Length; i++)
            {
                if (vowels.Contains(label[i]))
                {
                    count++;
                }
            }

            return count;
        }

    }
}
