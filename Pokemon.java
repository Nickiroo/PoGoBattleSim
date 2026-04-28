import java.util.List;

public class Pokemon {
    private final String name;
    private final List<String> types;
    private final int maxHP;
    private int currentHP;
    private final int attack;
    private final int defense;
    private int energy;
    private final List<Move> fastMoves;
    private final List<Move> chargedMoves;

    public Pokemon(String name, List<String> types, int maxHP, int attack, int defense,
                   List<Move> fastMoves, List<Move> chargedMoves) {
        this.name = name;
        this.types = types;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.energy = 0;
        this.fastMoves = fastMoves;
        this.chargedMoves = chargedMoves;
    }

    public String getName() { return name; }
    public List<String> getTypes() { return types; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getEnergy() { return energy; }
    public List<Move> getFastMoves() { return fastMoves; }
    public List<Move> getChargedMoves() { return chargedMoves; }

    public void takeDamage(int amount) {
        currentHP -= amount;
        if (currentHP < 0) currentHP = 0;
    }

    public void addEnergy(int amount) {
        energy += amount;
        if (energy > 100) energy = 100;
        if (energy < 0) energy = 0;
    }

    public void useMove(Move move) {
        addEnergy(move.getEnergyDelta());
    }

    public boolean isFainted() {
        return currentHP <= 0;
    }

    public Move chooseMove(Strategy strategy) {
        if (strategy.shouldUseChargedMove(this)) {
            Move best = null;
            for (Move m : chargedMoves) {
                if (energy + m.getEnergyDelta() >= 0) {
                    if (best == null || m.getPower() > best.getPower()) {
                        best = m;
                    }
                }
            }
            if (best != null) return best;
        }
        return fastMoves.get(0);
    }
}
