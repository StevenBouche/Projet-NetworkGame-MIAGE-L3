using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
using Serveur.GameServer.GameModel;
using share;
using Share.Network.Message;
using Share.Network.Message.modele;
using Share.Network.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Serveur.GameServer.CommandPack.CommandPlayer
{

    public class CommandHandleTurn : CommandReceiverClient<ChoiceStep>
    {
        public CommandHandleTurn(GameEngine context, CommandManager CM) : base(context, CM) { }

        private Boolean haveAlreadyBuyVoy = false;
        
        public override void onExecute()
        {
            Context.endTurn = false;
            while (!Context.endTurn)
            {
                //reset data for choice
                Data = null;
                idClient = null;

                SendAChoiceStep();

                WaitReceiveClient(); // wait event of current player

                // if want buy a voyel and not null
                if (Data.choiceStep.Equals(ChoiceStepEnum.BUYVOY.ToString()) && Data.voy != null) 
                {
                    if (!haveAlreadyBuyVoy) //and have not already try to buy a voyel
                    {
                        commandManager.TriggerActivePlayerWantBuyAVoy(idClient, Data.voy);
                        haveAlreadyBuyVoy = true;
                    }
                    else  EndTurn();
                }
                else if (Data.choiceStep.Equals(ChoiceStepEnum.PROPOSAL.ToString()) && Data.proposal != null) //if want proposal enigma and not null
                {
                    HandleProposition();
                }
                else if (Data.choiceStep.Equals(ChoiceStepEnum.TURNWHEEL.ToString())) //if want turnwheel and have consonne in enigma
                {
                    if (!HaveConsonneEnigma())
                    {
                        commandManager.TriggerWheelTurn();
                    }
                    else
                    {
                        SendNotifyNoMoreConsonnant();
                    }
                        
                }
            }
          
        }

        private void SendNotifyNoMoreConsonnant()
        {
            String str = "Il n'y a plus de consonnes";
            PacketMessage<String> msg = new PacketMessage<String>()
            {
                evt = ProtocolEventsTCP<String>.NOTIFYNOMORECONSONNANT.eventName,
                data = str
            };
        }

        private void SendAChoiceStep()
        {
            String id = Context.CurrentPlayer.id;
            PacketMessage<ChoiceStep> msg = new PacketMessage<ChoiceStep>()
            {
                evt = ProtocolEventsTCP<ChoiceStep>.CHOICESTEP.eventName,
                data = new ChoiceStep()
            };
            Context.SendClient(msg, id);
        }

        private void HandleProposition()
        {
            Enigme e = Context.CurrentEnigma;
            if (e.label.Equals(Data.proposal))
            {
                UpdateMontantEndRound();
                Context.NotifyPlayerHaveWinRound(idClient); // notify player win round
            }
            EndTurn(); // end turn meme si win round pour break le recursif
        }

        private void UpdateMontantEndRound()
        {
            foreach (KeyValuePair<String,Joueur> elem in Context.listPlayers)
            {
                if (elem.Key.Equals(idClient))
                {
                    elem.Value.cagnotte.Montant_Total += elem.Value.cagnotte.Montant_Manche;
                   
                }

                elem.Value.cagnotte.Montant_Manche = 0;


                //TODO FUSIONNER SEND CAR 3*3 packet a sens au lieu de 3
             /*   PlayerMoneyInfo pInfo = new PlayerMoneyInfo(elem.Key, elem.Value.cagnotte.Montant_Manche, elem.Value.cagnotte.Montant_Total);
                PacketMessage<PlayerMoneyInfo> msg = new PacketMessage<PlayerMoneyInfo>()
                {
                    evt = ProtocolEventsTCP<PlayerMoneyInfo>.UPDATEROUNDMONEY.eventName,
                    data = pInfo
                };
                Context.SendAllClient(msg);*/
            }
        }

        private void EndTurn()
        {
            Context.endTurn = true;
        }

        public Boolean HaveConsonneEnigma()
        {
            List<char> alreadyProposedLetters = Context.letterBuyInARound;
            
            var consonnants = new HashSet<char> { 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z'};
            int cpt = 0;
            
            foreach(char c in alreadyProposedLetters)
            {
                if (consonnants.Contains(c))
                {
                    cpt++;
                }
            }

            if(Context.CurrentEnigma.consonantNb < cpt)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
