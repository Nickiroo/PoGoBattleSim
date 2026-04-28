import java.util.ArrayList;
import java.util.List;

public class Team {
    private final List<Pokemon> pokemons = new ArrayList<>();
    private int activeIndex = 0;

    public void addPokemon(Pokemon p) {
        pokemons.add(p);
    }

    public Pokemon getActivePokemon() {
        return pokemons.get(activeIndex);
    }

    public void switchPokemon() {
        for (int i = 0; i < pokemons.size(); i++) {
            if (!pokemons.get(i).isFainted()) {
                activeIndex = i;
                return;
            }
        }
    }

    public boolean hasRemainingPokemon() {
        for (Pokemon p : pokemons) {
            if (!p.isFainted()) return true;
        }
        return false;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
