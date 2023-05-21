package app.clients;

public class Miner {
    public String name;

    public Miner(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Miner: " + this.name;
    }
}
