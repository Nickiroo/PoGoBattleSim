import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeChart {
    private static final double SUPER = 1.6;
    private static final double RESIST = 0.625;
    private static final double DOUBLE_RESIST = 0.390625;

    // chart.get(attackingType).get(defendingType) -> multiplier
    private static final Map<String, Map<String, Double>> chart = new HashMap<>();

    static {
        add("Normal", new String[]{}, new String[]{"Rock", "Steel"}, new String[]{"Ghost"});
        add("Fire", new String[]{"Grass", "Ice", "Bug", "Steel"},
                new String[]{"Fire", "Water", "Rock", "Dragon"}, new String[]{});
        add("Water", new String[]{"Fire", "Ground", "Rock"},
                new String[]{"Water", "Grass", "Dragon"}, new String[]{});
        add("Electric", new String[]{"Water", "Flying"},
                new String[]{"Electric", "Grass", "Dragon"}, new String[]{"Ground"});
        add("Grass", new String[]{"Water", "Ground", "Rock"},
                new String[]{"Fire", "Grass", "Poison", "Flying", "Bug", "Dragon", "Steel"}, new String[]{});
        add("Ice", new String[]{"Grass", "Ground", "Flying", "Dragon"},
                new String[]{"Fire", "Water", "Ice", "Steel"}, new String[]{});
        add("Fighting", new String[]{"Normal", "Ice", "Rock", "Dark", "Steel"},
                new String[]{"Poison", "Flying", "Psychic", "Bug", "Fairy"}, new String[]{"Ghost"});
        add("Poison", new String[]{"Grass", "Fairy"},
                new String[]{"Poison", "Ground", "Rock", "Ghost"}, new String[]{"Steel"});
        add("Ground", new String[]{"Fire", "Electric", "Poison", "Rock", "Steel"},
                new String[]{"Grass", "Bug"}, new String[]{"Flying"});
        add("Flying", new String[]{"Grass", "Fighting", "Bug"},
                new String[]{"Electric", "Rock", "Steel"}, new String[]{});
        add("Psychic", new String[]{"Fighting", "Poison"},
                new String[]{"Psychic", "Steel"}, new String[]{"Dark"});
        add("Bug", new String[]{"Grass", "Psychic", "Dark"},
                new String[]{"Fire", "Fighting", "Poison", "Flying", "Ghost", "Steel", "Fairy"}, new String[]{});
        add("Rock", new String[]{"Fire", "Ice", "Flying", "Bug"},
                new String[]{"Fighting", "Ground", "Steel"}, new String[]{});
        add("Ghost", new String[]{"Psychic", "Ghost"},
                new String[]{"Dark"}, new String[]{"Normal"});
        add("Dragon", new String[]{"Dragon"},
                new String[]{"Steel"}, new String[]{"Fairy"});
        add("Dark", new String[]{"Psychic", "Ghost"},
                new String[]{"Fighting", "Dark", "Fairy"}, new String[]{});
        add("Steel", new String[]{"Ice", "Rock", "Fairy"},
                new String[]{"Fire", "Water", "Electric", "Steel"}, new String[]{});
        add("Fairy", new String[]{"Fighting", "Dragon", "Dark"},
                new String[]{"Fire", "Poison", "Steel"}, new String[]{});
    }

    private static void add(String attacker, String[] superEff, String[] notVery, String[] doubleResist) {
        Map<String, Double> row = new HashMap<>();
        for (String t : superEff) row.put(t, SUPER);
        for (String t : notVery) row.put(t, RESIST);
        for (String t : doubleResist) row.put(t, DOUBLE_RESIST);
        chart.put(attacker, row);
    }

    public static double getEffectiveness(String attackType, List<String> defenderTypes) {
        Map<String, Double> row = chart.get(attackType);
        if (row == null) return 1.0;
        double mult = 1.0;
        for (String def : defenderTypes) {
            mult *= row.getOrDefault(def, 1.0);
        }
        return mult;
    }
}
