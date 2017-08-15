import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Philipp on 03.07.2017.
 */

public class Main_t3 {
    static TicTacToe game_t3 = new TicTacToe();

    public static void main(String[] args) throws IOException{
        int user_entry;

        System.out.println("Hallo User, das hier ist das Spiel ~Tic-Tac-Toe~");
        System.out.println("Um einen Zug zu machen, gib eine Zahl von 1-9 ein(Siehe Beispiel)");
        System.out.println("|1|2|3|\n" +
                "|4|5|6|\n" +
                "|7|8|9|");
        System.out.println("Wenn der Computer ziehen soll, gib 0 ein!");

        while (true) {
            System.out.println(game_t3.toString());
            if (game_t3.isWin() || game_t3.isDraw()) {
                System.out.println(game_t3.toString());
                if (game_t3.isWin()) {
                    if (!game_t3.isBeginnersTurn()) {
                        System.out.println("X hat gewonnen!");
                    } else {
                        System.out.println("O hat gewonnen!");
                    }
                } else {
                    System.out.println("Unentschieden!");
                }
                System.out.println("Gib \"new\" für ein neues Spiel ein, oder \"exit\" um das Spiel zu beenden!");
                System.out.println("[?: Hilfe]: _");
            }
            user_entry = user_entry_t3() - 1;
            if (user_entry == -1) {
                System.out.println("Ich denke nach...");
                TicTacToe newGame = (TicTacToe) new Algorithmen(game_t3).minimax();
                game_t3 = newGame;
                System.out.print(" und setze auf: ");
                System.out.print(((Move) game_t3.getHistory().get(game_t3.getHistory().size() - 1)).getT3()+1 + " ");
                //game_m.monteCarlo(100);
            } else {
                TicTacToe newGame = (TicTacToe) game_t3.makeMove(new Move(user_entry));
                game_t3 = newGame;
            }
        }
    }

    private static int user_entry_t3() throws IOException {
        boolean contains_check = false;
        boolean check = true;
        String test_str = "";
        int user_int = 0;
        Scanner scanner = new Scanner(System.in);
        while (check) {
            test_str = scanner.nextLine(); //speichere eingegebenen String
            if (isInteger(test_str)) {
                user_int = Integer.parseInt(test_str);
                if (user_int == 0) {
                    return 0;
                }
                if (user_int >= 0 && user_int <= 9) {
                    for (int i = 0; i < game_t3.moves().size(); i++) {
                        if (((Move) game_t3.moves().get(i)).getT3() == (user_int - 1)) {
                            contains_check = true;
                        }
                    }
                    if (contains_check) {
                        check = false;
                    } else {
                        System.out.println("Dieser Platz ist schon belegt, gib einen neuen Zug ein!");
                        System.out.println(game_t3.toString());
                    }
                } else {
                    System.out.println("Ungültige Eingabe! Bitte gib eine Zahl von 0-9 ein!");
                    System.out.println(game_t3.toString());
                }
            } else {
                switch (test_str.length() != 0 ? test_str.substring(0, 1).toLowerCase() : test_str) {
                    case "?"://Hilfe aufruf
                        help_t3();
                        System.out.println(game_t3.toString());
                        break;
                    case "h"://Hilfe aufruf
                        help_t3();
                        System.out.println(game_t3.toString());
                        break;
                    case "l"://Spiel laden
                        loadGameUI_t3();
                        break;
                    case "s"://Spiel speichern
                        saveGameUI_t3();
                        break;
                    case "e"://Spiel beenden
                        System.exit(0);
                        break;
                    case "n"://Neues Spiel starten
                        game_t3 = new TicTacToe();
                        System.out.println(game_t3.toString());
                        break;
                    case "u"://Letzten Zug rückgänging machen
                        if (game_t3.getHistory().size() == 0) {
                            System.out.println("Es gibt keinen Zug zum rückgängig machen!");
                        } else {
                            TicTacToe newGame = (TicTacToe) game_t3.undoMove();
                            game_t3 = newGame;
                        }
                        System.out.println(game_t3.toString());
                        break;
                    case "f"://Tausche X und O
                        TicTacToe newGame = (TicTacToe) game_t3.flip();
                        game_t3 = newGame;
                        System.out.println(game_t3.toString());
                        break;
                    default://Keiner der Befehle erkannt, also Fehlerhafte eingabe.
                        System.out.println("Unbekannter Befehl, gib \"help\" für eine Auswahl an Befehlen ein.: _");
                        System.out.println(game_t3.toString());
                }
            }
        }
        return user_int;
    }

    private static void help_t3() {
        System.out.println("Spielen Sie Tic-Tac-Toe gegen den Computer oder gegen einen anderen Spieler.");
        System.out.println("Die Positionen des Spielfelds sind von 1-9 nummeriert");
        System.out.println("|1|2|3|\n" +
                "|4|5|6|\n" +
                "|7|8|9|");
        System.out.println("Es wird abwechselnd gezogen. Gewonnen hat," +
                " wer zuerst drei Spielsteine(entweder X oder O) in Reihe anordnet");
        System.out.println("1-9     Position des Feldes, das man besetzen möchte");
        System.out.println("0       Der Computer macht für sie einen Zug");
        System.out.println("undo    Macht den letzten Spielzug rückgänging");
        System.out.println("flip    Tauscht X und O.");
        System.out.println("new     Beginnt ein neues Spiel");
        System.out.println("exit    Beendet das Programm");
        System.out.println("save    Speichert aktuelle Spielsituation");
        System.out.println("load    Lädt letzte gespeicherte Spielsituation");
        System.out.println("help    Zeigt diese Übersicht an");
        System.out.println("?       Wie \"help_t3\"");
    }

    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void saveGameUI_t3() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String test_str;
        System.out.println("Spielstand via Pfad oder Name speichern? (P|N + <ENTER>): _");
        test_str = scanner.nextLine();
        switch (test_str.length() != 0 ? test_str.substring(0, 1).toLowerCase() : test_str) {
            case "p":
                System.out.println("Bitte gib einen Pfad für den Spielstand ein(ohne \".txt!\"): _");
                game_t3.save(game_t3, Paths.get(scanner.nextLine() + ".txt"));//das hier mit pfad und name unterscheiden wie beim laden
                break;
            case "n":
                System.out.println("Bitte gib einen Namen für den Spielstand ein(ohne \".txt!\"): _");
                game_t3.save(game_t3, scanner.nextLine() + ".txt");//das hier mit pfad und name unterscheiden wie beim laden
                break;
            default:
                System.out.println("Fehlerhafte Eingabe!");
                break;
        }
        System.out.println(game_t3.toString());
    }

    private static void loadGameUI_t3() throws IOException {
        String test_str;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Spielstand via Pfad oder Name laden? (P|N + <ENTER>): _");
        test_str = scanner.nextLine();
        switch (test_str.length() != 0 ? test_str.substring(0, 1).toLowerCase() : test_str) {
            case "p":
                System.out.println("Geben sie den Pfad des Spielstandes ein, den sie laden wollen(ohne \".txt!\"): _");
                game_t3 = (TicTacToe) game_t3.load(Paths.get(scanner.nextLine() + ".txt"));
                System.out.println("Spiel erfolgreich geladen!");
                break;
            case "n":
                System.out.println("Geben sie den Namen des Spielstandes ein, den sie laden wollen(ohne \".txt!\"): _");
                game_t3 = (TicTacToe) game_t3.load(scanner.nextLine() + ".txt");
                System.out.println("Spiel erfolgreich geladen!");
                break;
            default:
                System.out.println("Fehlerhafte Eingabe!");
                break;
        }
        System.out.println(game_t3.toString());
    }
}
