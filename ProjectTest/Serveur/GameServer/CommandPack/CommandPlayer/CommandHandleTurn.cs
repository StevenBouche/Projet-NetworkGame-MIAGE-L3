using Serveur.GameServer.CommandPack.ReceiverNetwork;
using Serveur.GameServer.Game;
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
        private Boolean endTurn = false;
        public override void onExecute()
        {

            while (!endTurn)
            {

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
                else if (Data.choiceStep.Equals(ChoiceStepEnum.TURNWHEEL.ToString()) && HaveConsonneEnigma()) //if want turnwheel and have consonne in enigma
                {
                    commandManager.TriggerWheelTurn();
                }
            }
          
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
            if (!e.label.Equals(Data.proposal))
            {
                Context.NotifyPlayerHaveWinRound(idClient); // notify player win round
            }
            EndTurn(); // end turn meme si win round pour break le recursif
        }

        private void EndTurn()
        {
            endTurn = true;
        }

        public Boolean HaveConsonneEnigma()
        {
            throw new NotImplementedException();
        }
    }
}
