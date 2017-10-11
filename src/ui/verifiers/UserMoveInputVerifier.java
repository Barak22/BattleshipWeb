package ui.verifiers;

/**
 * Created by barakm on 15/08/2017
 */
public class UserMoveInputVerifier {

    public String checkInput(String input, int boardSize) {
        int userInput = -1;
        try {
            userInput = Integer.parseInt(input);
        } catch (Exception exeption) {
            return "Invalid input!";
        }

        if (userInput < 1 || userInput >= boardSize) {
            return "The given input is out of range!";
        }
        return null;
    }
}