using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public class FinalLetters
    {
        public List<char> finalLetters;

        public FinalLetters()
        {
            this.finalLetters = new List<char>(4);
        }

/*        public void fill(List<char> lettersToAdd)
        {
            foreach(char c in lettersToAdd)
            {
                this.finalLetters.Add(c);
            }
        }
*/
    }
}
