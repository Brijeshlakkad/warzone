package com.warzone.team08.VM.entities;

import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.repositories.CountryRepository;
import com.warzone.team08.VM.responses.CommandResponse;

/**
 * This class provides different getter-setter methods to perform different operation on Continent entity.
 *
 * @author CHARIT
 * @author Brijesh Lakkad
 */
public class Order {
    private OrderType d_orderType;
    private Country d_country;
    private int d_NumOfReinforcements;
    private Player d_owner;

    /**
     * To find the country given its name.
     */
    private final static CountryRepository d_countryRepository = new CountryRepository();

    public Order() {

    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    public OrderType getOrderType() {
        return d_orderType;
    }

    /**
     * Sets the type of order.
     *
     * @param p_orderType Value of the order type.
     */
    public void setOrderType(OrderType p_orderType) {
        d_orderType = p_orderType;
    }

    /**
     * Gets the destination country for the reinforcements.
     *
     * @return Value of the target country.
     */
    public Country getCountry() {
        return d_country;
    }

    /**
     * Sets the destination country for the reinforcements.
     *
     * @param p_country Value of the target country.
     */
    public void setCountry(Country p_country) {
        d_country = p_country;
    }

    /**
     * Gets the number of reinforcements.
     *
     * @return Value of reinforcements number.
     */
    public int getNumOfReinforcements() {
        return d_NumOfReinforcements;
    }

    /**
     * Sets the number of reinforcements.
     *
     * @param p_numOfReinforcements Value of reinforcements number.
     */
    public void setNumOfReinforcements(int p_numOfReinforcements) {
        d_NumOfReinforcements = p_numOfReinforcements;
    }

    /**
     * Gets the owner player of the order.
     *
     * @return Player for the issued order.
     */
    public Player getOwner() {
        return d_owner;
    }

    /**
     * Sets the owner player of the order.
     *
     * @param p_owner Player for the issued order.
     */
    public void setOwner(Player p_owner) {
        d_owner = p_owner;
    }

    /**
     * Maps the <code>CommandResponse</code> to <code>Order</code> object.
     * <p>This is a static method and returns a new
     * order instance created from <code>CommandResponse</code>.
     *
     * @param p_commandResponse Command object received at VM which represents the user command input.
     * @return Value of the new order created from user command response.
     * @throws EntityNotFoundException  Throws if the target country not found.
     * @throws InvalidCommandException  Throws if the command values not present.
     * @throws InvalidArgumentException Throws if the number of reinforcements is not a number.
     */
    public static Order map(CommandResponse p_commandResponse)
            throws EntityNotFoundException,
            InvalidCommandException,
            InvalidArgumentException {
        Order l_newOrder = new Order();
        l_newOrder.setOrderType(OrderType.valueOf(p_commandResponse.getHeadCommand().toLowerCase()));
        try {
            Country targetCountry = d_countryRepository.findFirstByCountryName(p_commandResponse.getCommandValues().get(0));
            // Get country from repository.
            l_newOrder.setCountry(targetCountry);
            try {
                l_newOrder.setNumOfReinforcements(Integer.parseInt(p_commandResponse.getCommandValues().get(1)));
            } catch (NumberFormatException p_e) {
                throw new InvalidArgumentException("Number of reinforcements is not a number!");
            }
        } catch (ArrayIndexOutOfBoundsException p_e) {
            throw new InvalidCommandException("Invalid command!");
        }
        return l_newOrder;
    }

    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     */
    public void execute() {
        if (this.getOrderType() == OrderType.deploy) {
            this.getCountry().setNumberOfArmies(this.getCountry().getNumberOfArmies() + this.getNumOfReinforcements());
        }
        this.getOwner().addExecutedOrder(this);
    }

    @Override
    public String toString() {
        return String.format("%s Player's order: %s %s %s", d_owner.getName(), d_orderType, d_country.getCountryName(), d_NumOfReinforcements);
    }
}
