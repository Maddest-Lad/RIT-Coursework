package puzzles.jam.model;

import java.util.HashMap;

public class Colors {

    public HashMap<Character, String> colorMap;

    public Colors() {
        loadColors();
    }

    private void loadColors() {
        this.colorMap = new HashMap<>();
        this.colorMap.put('A', "#81F781");
        this.colorMap.put('B', "#FE642E");
        this.colorMap.put('C', "#0101DF");
        this.colorMap.put('D', "#FF00FF");
        this.colorMap.put('E', "#AC58FA");
        this.colorMap.put('F', "#0B610B");
        this.colorMap.put('G', "#A4A4A4");
        this.colorMap.put('H', "#F5D0A9");
        this.colorMap.put('I', "#F3F781");
        this.colorMap.put('J', "#8A4B08");
        this.colorMap.put('K', "#0B6121");
        this.colorMap.put('L', "#FFFFFF");
        this.colorMap.put('O', "#FFFF00");
        this.colorMap.put('P', "#DA81F5");
        this.colorMap.put('Q', "#58ACFA");
        this.colorMap.put('R', "#088A08");
        this.colorMap.put('S', "#000000");
        this.colorMap.put('X', "#DF0101");
    }

}
