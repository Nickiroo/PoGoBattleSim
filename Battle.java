public class Battle {
    private final Team teamA;
    private final Team teamB;
    private final Strategy strategyA;
    private final Strategy strategyB;
    private int turn = 0;
    private final boolean verbose;

    public Battle(Team teamA, Team teamB, Strategy strategyA, Strategy strategyB, boolean verbose) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.strategyA = strategyA;
        this.strategyB = strategyB;
        this.verbose = verbose;
    }

    // Runs until one team is out and returns the winning team
    public Team runBattle() {
        while (teamA.hasRemainingPokemon() && teamB.hasRemainingPokemon()) {
            turn++;
            if (verbose) System.out.println("\n--- Turn " + turn + " ---");
            executeTurn();

            if (teamA.getActivePokemon().isFainted()) {
                if (verbose) System.out.println(teamA.getActivePokemon().getName() + " fainted!");
                teamA.switchPokemon();
                if (verbose && teamA.hasRemainingPokemon()) {
                    System.out.println("Team A sends out " + teamA.getActivePokemon().getName());
                }
            }
            if (teamB.getActivePokemon().isFainted()) {
                if (verbose) System.out.println(teamB.getActivePokemon().getName() + " fainted!");
                teamB.switchPokemon();
                if (verbose && teamB.hasRemainingPokemon()) {
                    System.out.println("Team B sends out " + teamB.getActivePokemon().getName());
                }
            }
        }
        return checkWinCondition();
    }

    private void executeTurn() {
        Pokemon a = teamA.getActivePokemon();
        Pokemon b = teamB.getActivePokemon();

        Move moveA = a.chooseMove(strategyA);
        Move moveB = b.chooseMove(strategyB);

        boolean aFirst = a.getAttack() >= b.getAttack();

        if (aFirst) {
            resolve(a, b, moveA, strategyB);
            if (!b.isFainted()) resolve(b, a, moveB, strategyA);
        } else {
            resolve(b, a, moveB, strategyA);
            if (!a.isFainted()) resolve(a, b, moveA, strategyB);
        }
    }

    private void resolve(Pokemon attacker, Pokemon defender, Move move, Strategy defenderStrategy) {
        if (move.isCharged()) {
            applyChargedMove(attacker, defender, move, defenderStrategy);
        } else {
            applyFastMove(attacker, defender, move);
        }
    }

    private void applyFastMove(Pokemon attacker, Pokemon defender, Move move) {
        int dmg = DamageCalculator.calculateDamage(attacker, defender, move);
        defender.takeDamage(dmg);
        attacker.useMove(move);
        if (verbose) {
            System.out.println(attacker.getName() + " used " + move.getName()
                    + " (fast) -> " + dmg + " dmg. "
                    + defender.getName() + " HP: " + defender.getCurrentHP() + "/" + defender.getMaxHP()
                    + " | " + attacker.getName() + " energy: " + attacker.getEnergy());
        }
    }

    private void applyChargedMove(Pokemon attacker, Pokemon defender, Move move, Strategy defenderStrategy) {
        attacker.useMove(move);
        if (defenderStrategy.shouldUseShield(defender, move)) {
            if (verbose) {
                System.out.println(attacker.getName() + " used " + move.getName()
                        + " (charged) -- " + defender.getName() + " SHIELDED!");
            }
            return;
        }
        int dmg = DamageCalculator.calculateDamage(attacker, defender, move);
        defender.takeDamage(dmg);
        if (verbose) {
            System.out.println(attacker.getName() + " used " + move.getName()
                    + " (charged) -> " + dmg + " dmg. "
                    + defender.getName() + " HP: " + defender.getCurrentHP() + "/" + defender.getMaxHP());
        }
    }

    private Team checkWinCondition() {
        boolean aAlive = teamA.hasRemainingPokemon();
        boolean bAlive = teamB.hasRemainingPokemon();
        if (aAlive && !bAlive) return teamA;
        if (bAlive && !aAlive) return teamB;
        return null;
    }
}
