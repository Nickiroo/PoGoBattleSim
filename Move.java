public class Move {
    private final String name;
    private final String type;
    private final int power;
    private final int energyDelta;
    private final boolean charged;

    public Move(String name, String type, int power, int energyDelta, boolean charged) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.energyDelta = energyDelta;
        this.charged = charged;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getPower() { return power; }
    public int getEnergyDelta() { return energyDelta; }
    public boolean isCharged() { return charged; }
}
