using System;
using System.Collections.Generic;
using System.Text;

namespace Share.Network.Message.modele
{
    public enum ChoiceStepEnum
    {
        PROPOSAL,
        BUYVOY,
        TURNWHEEL
    }

    public class ChoiceStep
    {
        public String choiceStep;
        public String proposal;
        public String voy;
    }
}
