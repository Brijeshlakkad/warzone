package com.warzone.team08.VM.entities.orders;

import com.warzone.team08.VM.constants.enums.OrderType;
import com.warzone.team08.VM.constants.interfaces.Order;
import com.warzone.team08.VM.entities.Country;
import com.warzone.team08.VM.entities.Player;
import com.warzone.team08.VM.exceptions.EntityNotFoundException;
import com.warzone.team08.VM.exceptions.InvalidArgumentException;
import com.warzone.team08.VM.exceptions.InvalidOrderException;
import com.warzone.team08.VM.repositories.CountryRepository;

/**
 * This order represents the deploy order.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class DeployOrder extends Order {
    private final Player d_owner;
    private final Country d_targetCountry;
    private final int d_numOfArmies;

    /**
     * To find the country using its data members.
     */
    private final CountryRepository d_countryRepository = new CountryRepository();

    /**
     * Default parameterised constructor.
     *
     * @param p_targetCountry Name of target country.
     * @param p_numOfArmies   Number of reinforcements to be deployed.
     * @param p_owner         Player who has initiated this order.
     * @throws EntityNotFoundException  Throws if the target country not found.
     * @throws InvalidArgumentException Throws if the number of armies is not a number.
     */
    public DeployOrder(String p_targetCountry, String p_numOfArmies, Player p_owner)
            throws
            EntityNotFoundException,
            InvalidArgumentException {
        // Get the target country from repository.
        d_targetCountry = d_countryRepository.findFirstByCountryName(p_targetCountry);
        try {
            d_numOfArmies = Integer.parseInt(p_numOfArmies);
            // Checks if the number of moved armies is less than zero.
            if (d_numOfArmies < 0) {
                throw new InvalidArgumentException("Number of armies can not be negative.");
            }
        } catch (NumberFormatException p_e) {
            throw new InvalidArgumentException("Number of reinforcements is not a number.");
        }
        d_owner = p_owner;
    }

    /**
     * Executes the order during <code>GameLoopState#EXECUTE_ORDER</code>
     *
     * @throws InvalidOrderException If the order can not be performed due to an invalid country, an invalid number of
     *                               armies, or other invalid input.
     */
    public void execute() throws InvalidOrderException {
        if (this.d_owner.getAssignedCountries().contains(d_targetCountry)) {
            int l_remainingReinforcementCount = d_owner.getRemainingReinforcementCount() - d_numOfArmies;
            if (l_remainingReinforcementCount < 0) {
                throw new InvalidOrderException("You don't have enough reinforcements.");
            }
            d_owner.setRemainingReinforcementCount(l_remainingReinforcementCount);
            d_targetCountry.setNumberOfArmies(this.d_targetCountry.getNumberOfArmies() + this.d_numOfArmies);
            d_owner.addExecutedOrder(this);
        } else {
            throw new InvalidOrderException("You can deploy the reinforcements only in your assigned countries");
        }
    }

    /**
     * Returns the string describing player order.
     *
     * @return String representing player orders.
     */
    @Override
    public String toString() {
        return String.format("Deploy order of %s player: %s %s", d_owner.getName(), d_targetCountry.getCountryName(), d_numOfArmies);
    }

    /**
     * Gets the type of order.
     *
     * @return Value of the order type.
     */
    public OrderType getType() {
        return OrderType.deploy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void expire() {
        // Does nothing.
    }
}
