class Hillstations {

    void famousfood() {
        System.out.println("General Hill Station Food");
    }

    void famousfor() {
        System.out.println("Famous for Natural Beauty");
    }
}

class Manali extends Hillstations {
    void famousfood() {
        System.out.println("Manali Famous Food: Siddu");
    }

    void famousfor() {
        System.out.println("Manali is famous for Snow");
    }
}

class Mussoorie extends Hillstations {
    void famousfood() {
        System.out.println("Mussoorie Famous Food: Momos");
    }

    void famousfor() {
        System.out.println("Mussoorie is famous for Hills");
    }
}

class Gulmarg extends Hillstations {
    void famousfood() {
        System.out.println("Gulmarg Famous Food: Rogan Josh");
    }

    void famousfor() {
        System.out.println("Gulmarg is famous for Skiing");
    }
}

public class MPJ_Assignment_03_2 {

    public static void main(String[] args) {

        Hillstations h;

        h = new Manali();
        h.famousfood();
        h.famousfor();

        h = new Mussoorie();
        h.famousfood();
        h.famousfor();

        h = new Gulmarg();
        h.famousfood();
        h.famousfor();
    }
}