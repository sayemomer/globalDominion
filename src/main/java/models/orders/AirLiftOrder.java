package models.orders;

import models.Country;
import models.GameState;
import models.Player;

public class AirLiftOrder extends Order{

    private final int d_sourceCountyId;
    private final int d_targetCountryId;
    private final int d_numReinforcements;

    public AirLiftOrder(GameState p_gameState, Player p_owner, int d_sourceCountyId,
                        int d_targetCountryId, int d_numReinforcements) {
        super(p_gameState, p_owner);
        this.d_sourceCountyId = d_sourceCountyId;
        this.d_targetCountryId = d_targetCountryId;
        this.d_numReinforcements = d_numReinforcements;

        validate();
    }

    @Override
    public void execute() {

        Country sourceCountry = d_gameState.getCountries().get(d_sourceCountyId);
        Country targetCountry = d_gameState.getCountries().get(d_targetCountryId);

        sourceCountry.setNumberOfReinforcements(sourceCountry.getNumberOfReinforcements()-d_numReinforcements);
        targetCountry.setNumberOfReinforcements(targetCountry.getNumberOfReinforcements()+d_numReinforcements);



    }

    @Override
    protected void validate() {

        if (!d_owner.getCountryIds().contains(d_sourceCountyId))
            throw new IllegalArgumentException("The first given country is not owned by the player. Please try again.");

        if (!d_owner.getCountryIds().contains(d_targetCountryId))
            throw new IllegalArgumentException("The second given country is not owned by the player. Please try again.");


        if (d_numReinforcements < 0)
            throw new IllegalArgumentException("Number of reinforcements cannot be negative");

        if (d_gameState.getCountries().get(d_sourceCountyId) == null)
            throw new IllegalArgumentException("First country does not exist");

        if (d_gameState.getCountries().get(d_targetCountryId) == null)
            throw new IllegalArgumentException("Second country does not exist");

        if (d_owner.getCountries().get(d_sourceCountyId).getNumberOfReinforcements() < d_numReinforcements)
            throw new IllegalArgumentException("Not enough reinforcements in country "+ d_sourceCountyId);

    }
}
