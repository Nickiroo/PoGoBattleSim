public class DamageCalculator {
    // Simplified PoGo PvP damage formula:
    // floor(0.5 * power * (atk/def) * STAB * typeEffectiveness) + 1
    // There are conflicting formulas for this found on Reddit and StackOverflow, so this seems to be
    // an almost impossible thing for me to get 100% correct. 
    public static int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {
        double stab = attacker.getTypes().contains(move.getType()) ? 1.2 : 1.0;
        double eff = TypeChart.getEffectiveness(move.getType(), defender.getTypes());
        double raw = 0.5 * move.getPower()
                * ((double) attacker.getAttack() / defender.getDefense())
                * stab * eff;
        return (int) Math.floor(raw) + 1;
    }
}
