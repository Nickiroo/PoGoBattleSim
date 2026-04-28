**Notes to professor/TA:**
I unfortunately did not have the time to implement everything I wanted. 
- I decided halfway through that it would be a good idea to instead just find a list of all available pokemon currently in pokemon go and use that for types and to generate random teams, but there isn't a good up-to-date one in a format that would be easy to work with without using external libraries. So I am just using the final 3 starter evolutions along with pikachu, machamp, and dragonite. 
- I also realized in making this that this is really only accurate if both players are tapping to attack as quickly as permitted by the game, otherwise the project is entirely useless. 
- As I noted in the damagecalculator.java, it isn't possible to do 100% accurate damage calculations for pokemon go like other pokemon games. 
- The 'aggressive' and 'defensive' strategies are also far from ideal, because, for example, you almost never want to use a charged attack if the opponent's active pokemon is at extremely low health because most of the damage from the charged attack will be wasted, UNLESS it is the last pokemon available in the opponent's team.
- Also, in the simulations it doesn't use any sort of logic to swap to a pokemon that better counters the current opponent pokemon. This is far from ideal.

Pokemon Go Team Comp Simulator

Proposal

Nicholas Everett, CS121

**Purpose**

This project is intended to demonstrate mastery of concepts learned in CSCI 121, specifically Object-Oriented programming, and also be an opportunity for me to apply those skills and concepts to something fun and potentially useful. 

**Overview**

This will be a simulation program that models Pokémon GO battles between teams of Pokémon that takes into account pokemon type, fast and charged moves, energy generation, cooldown timing, and the use of shields. The goal of this project is to allow users to test different team compositions and evaluate how they perform in simulated battles against other people.

**Intended Users:**

Anyone who takes Pokemon Go PvP seriously, or anyone trying to maximize an anti-meta team comp to counter commonly used teams in the Go Battle Leagues. I personally don't take it that seriously and only play the game recreationally but I still might actually use it to construct an anti-meta team and test it out.

**Use Cases:**

1. Simulating a battle:
  1. User inputs Team A (3 Pokémon)
  2. User inputs Team B (3 Pokémon)
  3. User selects strategy type (optional AI behavior settings)
  4. Program begins simulation
    1. Active Pokémon use fast move
    2. Energy is accumulated
    3. Charged moves are used when available
    4. Opponent may use shields, if I can find some sort of optimal strategy for shields than I will implement this but in my years of playing it seems that it is more of a “absolutely don’t use it now” combat feature instead of having an ideal time to use it
    5. Type effectiveness is applied
    6. Pokémon faint when HP reaches 0
    7. Next Pokémon is switched in automatically
  5. Battle ends when one team has no remaining Pokémon
2. Team testing mode
  1. User inputs or selects a fixed Team A
  2. Program generates multiple opponent teams
  3. Simulation runs multiple times (e.g., 100 battles)
  4. Results are displayed to the user

## Data Design & Management

**Actual data:**

- Pokémon data:
  - Name
  - Type(s)
  - IVs
  - Moves
  - Energy
- Move data:
  - Name
  - Type
  - Power
  - Energy cost/gain
  - Charged vs Fast classification
- Battle data:
  - Active Pokémon per team
  - Current HP values
  - Energy levels
  - Shield counts
  - Turn counter

Almost all of this data will be represented using vectors. The data doesn’t need to be persistent, but I plan on adding the ability to save specific team data to a single CSV that holds all custom teams, load teams from said CSV, and output data from battles to a CSV. Data is aggregated in a hierarchical manner.

## Algorithm

1. 1 Pokemon Class
  1. Data Members
    1. name
    2. types (vector)
    3. maxHP
    4. currentHP
    5. attack
    6. defense
    7. energy
    8. fastMoves
    9. chargedMoves
  2. Constructor
    1. Initializes stats and assigns default energy = 0
  3. Methods
    1. takeDamage()
    2. addEnergy()
    3. useMove()
    4. isFainted()
    5. chooseMove()
  4. Testing
    1. Create sample Pokémon and simulate damage interactions
2. Move Class
  1. Data Members
    1. name
    2. type
    3. power
    4. energyDelta
    5. isCharged
  2. Methods
    1. getters only for each above member
  3. Testing
    1. Validate damage and energy values
3. Team Class
  1. Data Members
    1. vector
    2. activeIndex
  2. Methods
    1. addPokemon()
    2. getActivePokemon()
    3. switchPokemon()
    4. hasRemainingPokemon()
4. Battle Class
  1. Data Members
    1. Team A, Team B
    2. Strategy A, Strategy B
    3. turn counter
  2. Methods
    1. runBattle()
      1. Pseudocodee:
        while both teams have Pokémon:  
         executeTurn()  
         check fainted Pokémon  
         switch if needed  
         return winner
    2. executeTurn()
    3. applyFastMove()
    4. applyChargedMove()
    5. checkWinCondition()
5. Strategy
  1. Base Class
  2. shouldUseShield()
  3. shouldUseChargedMove()
  4. Derived Classes
  5. AggressiveStrategy
  6. DefensiveStrategy
  7. Purpose
  8. Determines AI behavior during battle.
6. DamageCalculator
  1. Methods
    1. calculateDamage(attacker, defender, move)
      1. Steps:
        1. Apply base power
        2. Apply attack/defense ratio
        3. Apply type effectiveness multiplier
7. TypeChart
  1. Methods
    1. getEffectiveness(attackType, defendTypes)
      1. returns multiplier (0.625, 1.0, 1.6, etc.)
8. Testing Plan
  1. Each class will be tested individually:
    1. Pokemon: HP reduction, energy gain
    2. Move: correctness of values
    3. Battle: full simulation runs
    4. Strategy: decision consistency
    5. DamageCalculator: expected output validation

## Milestones

- Implement Move, Pokemon, Team classes
- Implement DamageCalculator
- Implement TypeChart
- Implement Battle loop
- Add fainting and switching logic
- Implement AI decision-making
- Add shield logic

The rest is just grunt work, like making the menus look nice with good formatting and running the battle engine multiple times for team testing.