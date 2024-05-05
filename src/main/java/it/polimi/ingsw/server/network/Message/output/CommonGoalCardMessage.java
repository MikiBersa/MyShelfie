package it.polimi.ingsw.server.network.Message.output;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.visitor.ClientVisitor;
import it.polimi.ingsw.server.model.common_objective_cards.CommonObjectiveCard;
import it.polimi.ingsw.server.network.Message.MessageByServer;

/**
 * Message used to set the common goal cards
 */
public class CommonGoalCardMessage extends MessageByServer {
    private final CommonObjectiveCard[] commonObjectiveCards;

    /**
     * Create a CommonGoalCardMessage with the common goal cards
     *
     * @param commonGoalCardsId the common goal cards array
     */
    public CommonGoalCardMessage(CommonObjectiveCard[] commonGoalCardsId) {
        super();
        this.commonObjectiveCards = commonGoalCardsId;
    }

    /**
     * Get the common objective cards
     *
     * @return the common objective cards
     */
    public CommonObjectiveCard[] getCommonObjectiveCards() {
        return commonObjectiveCards;
    }

    /**
     * Method used to handle the CommonGoalCardMessage in the client controller
     *
     * @param clientVisitor visitor used by the client
     * @param controller    client controller used to handle the message
     */
    @Override
    public void accept(ClientVisitor clientVisitor, ClientController controller) {
        clientVisitor.visitCommonGoalCardMessage(this, controller);
    }

    /**
     * toString method
     *
     * @return the message to string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (CommonObjectiveCard idCommonGoalCard : commonObjectiveCards) {
            stringBuilder.append("[id = ").append(idCommonGoalCard.getId())
                    .append(' ').append(']');
        }

        return getClass().getSimpleName() + "{" + stringBuilder + "}";
    }
}
