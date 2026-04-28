public abstract class Strategy {
    public abstract boolean shouldUseShield(Pokemon defender, Move incoming);
    public abstract boolean shouldUseChargedMove(Pokemon self);
}

class AggressiveStrategy extends Strategy {
    @Override
    public boolean shouldUseShield(Pokemon defender, Move incoming) {
        return false;
    }

    // Fire any charged move as soon as it's affordable. 
    @Override
    public boolean shouldUseChargedMove(Pokemon self) {
        for (Move m : self.getChargedMoves()) {
            if (self.getEnergy() + m.getEnergyDelta() >= 0) return true;
        }
        return false;
    }
    
}

class DefensiveStrategy extends Strategy {
    private int shieldsLeft = 2;

    // Burn shields on the first two incoming charged moves, then stop.
    @Override
    public boolean shouldUseShield(Pokemon defender, Move incoming) {
        if (shieldsLeft > 0) {
            shieldsLeft--;
            return true;
        }
        return false;
    }

    // Hold energy for the strongest charged move available.
    @Override
    public boolean shouldUseChargedMove(Pokemon self) {
        Move strongest = null;
        for (Move m : self.getChargedMoves()) {
            if (strongest == null || m.getPower() > strongest.getPower()) {
                strongest = m;
            }
        }
        if (strongest == null) return false;
        return self.getEnergy() + strongest.getEnergyDelta() >= 0;
    }
}
