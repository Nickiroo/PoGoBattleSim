import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final List<Pokemon> ROSTER = buildRoster();
    private static final Scanner IN = new Scanner(System.in);
    private static final Random RNG = new Random();

    public static void main(String[] args) {
        System.out.println("=== Pokemon GO Team Comp Simulator ===");
        while (true) {
            System.out.println("\n1) Single battle");
            System.out.println("2) Team test (run N random battles)");
            System.out.println("3) Quit");
            int choice = readInt("Choose: ");
            if (choice == 1) singleBattle();
            else if (choice == 2) teamTest();
            else if (choice == 3) { printBulbasaur(); return; }
        }
    }

    private static void singleBattle() {
        System.out.println("\n-- Pick Team A --");
        Team a = pickTeam();
        Strategy stratA = pickStrategy("Team A");
        System.out.println("\n-- Pick Team B --");
        Team b = pickTeam();
        Strategy stratB = pickStrategy("Team B");

        Battle battle = new Battle(a, b, stratA, stratB, true);
        Team winner = battle.runBattle();
        System.out.println("\n=== Result ===");
        if (winner == a) System.out.println("Team A wins!");
        else if (winner == b) System.out.println("Team B wins!");
        else System.out.println("Draw.");
    }

    private static void teamTest() {
        System.out.println("\n-- Pick your Team A --");
        List<Pokemon> aPicks = pickPokemonList();
        Strategy stratA = pickStrategy("Team A");
        Strategy stratB = pickStrategy("Random opponents");
        int n = readInt("How many battles to simulate? ");

        int wins = 0, losses = 0, draws = 0;
        for (int i = 0; i < n; i++) {
            Team a = freshTeam(aPicks);
            Team b = randomTeam();
            Battle battle = new Battle(a, b, stratA, stratB, false);
            Team winner = battle.runBattle();
            if (winner == a) wins++;
            else if (winner == b) losses++;
            else draws++;
        }
        System.out.println("\n=== Team Test Results (" + n + " battles) ===");
        System.out.println("Wins:   " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Draws:  " + draws);
    }

    private static Team pickTeam() {
        return freshTeam(pickPokemonList());
    }

    private static List<Pokemon> pickPokemonList() {
        listRoster();
        List<Pokemon> picks = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int idx = readInt("Pokemon #" + i + " (1-" + ROSTER.size() + "): ") - 1;
            if (idx < 0 || idx >= ROSTER.size()) {
                System.out.println("Invalid, try again.");
                i--;
                continue;
            }
            picks.add(ROSTER.get(idx));
        }
        return picks;
    }

    private static Team freshTeam(List<Pokemon> templates) {
        Team t = new Team();
        for (Pokemon p : templates) t.addPokemon(clone(p));
        return t;
    }

    private static Team randomTeam() {
        List<Pokemon> shuffled = new ArrayList<>(ROSTER);
        Collections.shuffle(shuffled, RNG);
        Team t = new Team();
        for (int i = 0; i < 3; i++) t.addPokemon(clone(shuffled.get(i)));
        return t;
    }

    private static Strategy pickStrategy(String label) {
        System.out.println("Strategy for " + label + ":");
        System.out.println("  1) Aggressive");
        System.out.println("  2) Defensive");
        int s = readInt("Choose: ");
        return s == 2 ? new DefensiveStrategy() : new AggressiveStrategy();
    }

    private static void listRoster() {
        System.out.println("Available Pokemon:");
        for (int i = 0; i < ROSTER.size(); i++) {
            Pokemon p = ROSTER.get(i);
            System.out.println("  " + (i + 1) + ") " + p.getName()
                    + " " + p.getTypes() + "  HP:" + p.getMaxHP()
                    + " Atk:" + p.getAttack() + " Def:" + p.getDefense());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = IN.hasNextLine() ? IN.nextLine().trim() : "";
            try { return Integer.parseInt(line); }
            catch (NumberFormatException e) { System.out.println("Enter a number."); }
        }
    }

    private static Pokemon clone(Pokemon p) {
        return new Pokemon(p.getName(), p.getTypes(), p.getMaxHP(),
                p.getAttack(), p.getDefense(),
                p.getFastMoves(), p.getChargedMoves());
    }

    private static void printBulbasaur() {
        System.out.println("""
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠉⢳⠴⢲⠂⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⠤⠤⠤⠤⠤⠤⠤⠤⠤⠖⠊⠀⣠⠎⠀⡞⢹⠏⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡴⠊⠁⠀⠀⠀⠀⠀⢀⡠⠤⠄⠀⠀⠀⠁⠀⠀⢀⠀⢸⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⣠⠤⠤⠄⣀⠀⠀⠀⠀⢀⣌⠀⠀⠀⠀⠀⢀⣠⣆⡁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠘⡄⠀⠀⠀⠀
                ⠀⠀⠀⠀⡴⠁⠀⠀⠐⠛⠉⠁⠀⠀⣉⠉⠉⠉⠑⠒⠉⠁⠀⠀⢸⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢧⠀⠱⡀⠀⠀⠀
                ⠀⠀⠀⢰⣥⠆⠀⠀⠀⣠⣴⣶⣿⣿⣿⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⢇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡆⠀⠑⡄⠀⠀
                ⠀⠀⢀⡜⠁⠀⠀⢀⠀⠻⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠀⠀⠸⡀⠀
                ⠀⢀⣮⢖⣧⢠⠀⣿⠇⠀⠀⠁⠀⠀⠀⠠⠀⢀⣠⣴⣤⡀⠀⠀⠀⠈⡗⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⢱⠀
                ⠀⣼⠃⣼⣿⠘⠀⠀⠀⢠⣶⣿⡆⠀⠀⠁⣠⠊⣸⣿⣿⣿⡄⠀⠀⠀⡇⠀⢑⣄⠀⠀⠀⠀⠀⠀⢠⠃⠀⠀⠸⡆
                ⠀⣿⢰⣿⣿⠀⠀⠀⠀⠙⠻⠿⠁⠀⠀⠠⠁⠀⣿⣿⣿⣿⡇⠀⠀⠀⠇⠀⢻⣿⣷⣦⣀⡀⣀⠠⠋⠀⠀⠀⢀⡇
                ⠈⠉⠺⠿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⢿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠈⢿⣿⣿⣿⣿⢦⡀⠀⠀⠀⠀⡸⠀
                ⠘⣟⠦⢀⠀⠀⢠⠀⠀⡠⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠁⣀⠔⠀⠀⠀⠀⠀⠀⠀⠛⠻⠟⠋⠀⠙⢦⠀⣠⠜⠀⠀
                ⠀⠈⠑⠤⡙⠳⣶⣦⣤⣤⣤⣤⣤⣤⣤⣤⣴⣶⡶⠞⠁⠀⠀⣠⠖⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠈⢯⠁⠀⠀⠀
                ⠀⠀⠀⠀⠈⢳⠤⣙⡻⠿⣿⣿⣿⣿⡿⠿⠛⠉⠀⢀⣀⡤⡚⠁⠀⠀⠀⠀⠀⠀⣧⠖⣁⣤⣦⠀⠀⠈⢇⠀⠀⠀
                ⠀⠀⠀⠀⠀⢸⠀⢀⣩⣍⠓⠒⣒⠒⠒⠒⠒⠊⠉⠁⢀⡟⠀⠀⣾⣷⠀⠀⠀⠀⠏⢴⣿⣿⣿⠀⠀⠀⢸⠀⠀⠀
                ⠀⠀⠀⠀⠀⠘⣶⣿⣿⣿⠀⠀⠈⠒⢄⣀⡀⠀⠀⠀⣼⣶⣿⡇⠈⠋⠀⠀⠀⡼⠀⠈⠻⣿⡿⠀⠀⠀⢸⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠹⡿⠿⠋⠀⠀⠀⠀⡜⠁⠈⢯⡀⢺⣿⣿⣿⠃⠀⠀⠀⢀⣼⣇⠀⠀⠀⠀⠀⠀⠀⠀⡞⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣿⣦⣄⣠⣀⣠⠞⠀⠀⠀⠈⠛⣿⡛⠛⠁⠀⠀⠀⣠⠊⠀⠈⢦⣄⣀⣀⣀⣀⢀⡼⠁⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠛⠉⠀⠀⠀⠀⠀⠀⠘⠛⠿⣿⠷⡾⠗⠊⠁⠀⠀⠀⠈⠉⠙⠛⠛⠛⠉⠀⠀⠀⠀⠀
                Goodbye!
                """);
    }

    private static List<Pokemon> buildRoster() {
        // These are just testing moves for the testing pokemon that I chose. Ideally I would want
        // to use some sort of database of moves and pokemon, but I couldn't find a good one that was
        // easy to work with without using external libraries.
        Move vineWhip = new Move("Vine Whip", "Grass", 5, 8, false);
        Move frenzyPlant = new Move("Frenzy Plant", "Grass", 100, -45, true);
        Move sludgeBomb = new Move("Sludge Bomb", "Poison", 80, -50, true);

        Move ember = new Move("Ember", "Fire", 6, 7, false);
        Move blastBurn = new Move("Blast Burn", "Fire", 110, -50, true);
        Move stoneEdge = new Move("Stone Edge", "Rock", 100, -55, true);

        Move waterGun = new Move("Water Gun", "Water", 4, 9, false);
        Move hydroCannon = new Move("Hydro Cannon", "Water", 80, -40, true);
        Move iceBeam = new Move("Ice Beam", "Ice", 90, -55, true);

        Move thunderShock = new Move("Thunder Shock", "Electric", 5, 9, false);
        Move wildCharge = new Move("Wild Charge", "Electric", 100, -50, true);

        Move counter = new Move("Counter", "Fighting", 8, 7, false);
        Move closeCombat = new Move("Close Combat", "Fighting", 100, -45, true);

        Move dragonBreath = new Move("Dragon Breath", "Dragon", 4, 3, false);
        Move outrage = new Move("Outrage", "Dragon", 110, -60, true);

        return Arrays.asList(
            new Pokemon("Venusaur", Arrays.asList("Grass", "Poison"), 165, 132, 130,
                Arrays.asList(vineWhip), Arrays.asList(frenzyPlant, sludgeBomb)),
            new Pokemon("Charizard", Arrays.asList("Fire", "Flying"), 145, 158, 124,
                Arrays.asList(ember), Arrays.asList(blastBurn, stoneEdge)),
            new Pokemon("Blastoise", Arrays.asList("Water"), 170, 130, 142,
                Arrays.asList(waterGun), Arrays.asList(hydroCannon, iceBeam)),
            new Pokemon("Pikachu", Arrays.asList("Electric"), 120, 112, 96,
                Arrays.asList(thunderShock), Arrays.asList(wildCharge)),
            new Pokemon("Machamp", Arrays.asList("Fighting"), 160, 175, 110,
                Arrays.asList(counter), Arrays.asList(closeCombat, stoneEdge)),
            new Pokemon("Dragonite", Arrays.asList("Dragon", "Flying"), 175, 180, 140,
                Arrays.asList(dragonBreath), Arrays.asList(outrage, hydroCannon))
        );
    }
}
