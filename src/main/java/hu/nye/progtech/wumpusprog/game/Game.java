
/**
 * .
 * wumpuszprog
 */

package hu.nye.progtech.wumpusprog.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**.
 * Játék
 */

public class Game {
    /**.
     * felhasználónév
     **/
    private String username;
    /**.
     * nyilak
     * */
    private int arrows = 0;
    /**.
     * hős kezdőhelye
     * */
    private int startX;
    /**.
     * hős kezdőhelye
     * */
    private int startY;
    /**.
     * hős jelenlegi helye
     * */
    private int heroX;
    /**.
     * hős jelenlegi helye
     * */
    private int heroY;
    /**.
     * hős iránya
     * */
    private Orientation lookDir;
    /**.
     * hős birtokolja-e az aranyat
     * */
    private boolean hasGold = false;
    /**.
     * pálya
     * */
    private char[][] map;
    Scanner scanner = new Scanner(System.in);

    public void inputName() throws Exception {
        System.out.println("Adjon meg egy felhasználónevet!");
        username = scanner.nextLine();
        System.out.println("Az ön neve:" + username);
        menu();
    }

    /**.
     * Menü
     */
    public void menu() throws Exception {
        while (true) {
            System.out.println("Menü:");
            System.out.println("1. Pályaszerkesztés");
            System.out.println("2. Fájlból beolvasás");
            System.out.println("3. Adatbázisból betöltés");
            System.out.println("4. Adatbázisba mentés");
            System.out.println("5. Játék");
            System.out.println("6. Kilépés");

            int menupoint = scanner.nextInt();

            switch (menupoint) {
                case 1:
                    System.out.println("Csak a fájlból beolvasást implementáltam");
                    break;
                case 2:
                    parse();
                    break;
                case 3:
                    //  loading();
                    System.out.println("Még nem sikerült implementálnom");
                    break;
                case 4:
                    //             saving();
                    System.out.println("Még nem sikerült implementálnom");
                    break;
                case 5:
                    play();
                    break;
                case 6:
                    System.out.println("Kilépés a játékból");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Nem megfelelő input");

            }
        }


    }

    private void drawBoard() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == heroX && j == heroY) {
                    System.out.print('H');
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("A hős helyzete: " + (heroX + 1) + ", " + (heroY + 1));
        System.out.println("A hős iránya: " + lookDir);
        System.out.println("Nyilak száma: " + arrows);
        System.out.println("Nálunk van-e az arany: " + hasGold);
    }

    private void left() {
        switch (lookDir) {
            case North -> lookDir = Orientation.West;
            case West -> lookDir = Orientation.South;
            case South -> lookDir = Orientation.East;
            case East -> lookDir = Orientation.North;
        }


    }

    private void right() {
        switch (lookDir) {
            case North -> lookDir = Orientation.East;
            case West -> lookDir = Orientation.North;
            case South -> lookDir = Orientation.West;
            case East -> lookDir = Orientation.South;
        }
    }

    private void evaluate() {

    }

    private boolean forward() throws Exception {
        int newX = heroX;
        int newY = heroY;
        switch (lookDir) {
            case North:
                newX--;
                break;
            case West:
                newY--;
                break;
            case South:
                newX++;
                break;
            case East:
                newY++;
                break;
            default:
                System.out.println("Nem megfelelő input");
        }
        char value = map[newX][newY];
        switch (value) {
            case 'W':
                System.out.println("Nem léphetsz falra");
                break;
            case 'U':
                System.out.println("Vesztettél");
                heroX = newX;
                heroY = newY;
                menu();
                break;
            case '_':
                heroX = newX;
                heroY = newY;
                break;
            case 'G':
                heroX = newX;
                heroY = newY;
                System.out.println("Felvetted az aranyat");
                hasGold = true;
                map[heroX][heroY] = '_';
                break;
            case 'P':
                heroX = newX;
                heroY = newY;
                System.out.println("Verembe léptél, elvesztettél egy nyílvesszőt");
                if (arrows > 0) {
                    arrows--;
                }
                break;
            case 'K':
                heroX = newX;
                heroY = newY;
                if (hasGold = true) {
                    System.out.println("Végig vitted a játékot");
                    menu();
                }
                break;
            default:
                System.out.println("Nem megfelelő input");
        }

        return true;
    }

    private void shoot() {
        if (arrows == 0) {
            System.out.println("Nincsen több nyilvessződ");
        } else {
            arrows--;
            int arrowX = heroX;
            int arrowY = heroY;
            while (true) {
                switch (lookDir) {
                    case North:
                        arrowX--;
                        break;
                    case West:
                        arrowY--;
                        break;
                    case South:
                        arrowX++;
                        break;
                    case East:
                        arrowY++;
                        break;
                    default:
                        System.out.println("Nem megfelelő input");
                }
                if (map[arrowX][arrowY] == 'W') {
                    System.out.println("Falat találtál el");
                    break;
                } else if (map[arrowX][arrowY] == 'U') {
                    System.out.println("Wumpuszt találtál el");
                    map[arrowX][arrowY] = '_';
                    break;
                }
            }
        }
    }

    private void play() throws Exception {
        while (true) {
            drawBoard();
            System.out.print("Input lehetőségek: balra fordul: l | jobbra fordul: r | előre megy: f | lövés: s | kilépés a menübe: c\n");
            System.out.print("Input: ");
            char c = scanner.next().charAt(0);
            System.out.println(c);

            switch (c) {
                case 'l':
                    left();
                    break;
                // turn right
                case 'r':
                    right();
                    break;
                // move forward
                case 'f':
                    forward();
                    break;
                // shoot
                case 's':
                    shoot();
                    break;
                case 'c':
                    System.out.println("Visszalépés a menübe");
                    menu();
                    break;
                default:
                    System.out.println("Nem megfelelő input");
            }

        }
    }

    private void parse() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(
                "D:\\Sulihoz\\Java project\\Progtech\\Wumpus beadando\\Wumpus prog\\src\\main\\resources\\map\\wumpuszinput.txt"));
        String[] parts = br.readLine().split("\\s");

        int dim = Integer.parseInt(parts[0]);
        map = new char[dim][dim];

        startY = ((int) 'A' - (int) parts[1].charAt(0)) * -1;
        startX = Integer.parseInt(parts[2]) - 1;
        heroX = startX;
        heroY = startY;

        switch (parts[3].charAt(0)) {
            case 'E':
                lookDir = Orientation.East;
                break;
            case 'W':
                lookDir = Orientation.West;
                break;
            case 'S':
                lookDir = Orientation.South;
                break;
            case 'N':
                lookDir = Orientation.North;
                break;
            default:
                System.out.println("Nem megfelelő input");
        }

        for (int i = 0; i < dim; i++) {
            String line = br.readLine();
            map[i] = line.toCharArray();

            for (int j = 0; j < dim; j++) {
                if (map[i][j] == 'U') {
                    arrows++;
                }
            }
        }
        map[startX][startY] = 'K';
    }

}
