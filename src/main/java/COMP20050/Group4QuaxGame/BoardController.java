package COMP20050.Group4QuaxGame;

/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Colour;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class BoardController
{
    protected static Map<String, Polygon> tiles = new HashMap<>();

    // gameManager reference
    protected final GameManager gameManager = new GameManager();

    private static final Color emptyOctColour = Color.web("#ffd675");
    private static final Color emptyRhoColour = Color.web("#bcbcbc");

    public static boolean DEV_MODE = Boolean.getBoolean("devMode");

    // get ID on click to pass to gameManager for validation
    @FXML
    void clickToMove(MouseEvent event)
    {
        Polygon playerNode = (Polygon) event.getSource();

        Move playerMove = gameManager.humanMove(playerNode);
        // if gameManager verifies the move set the tiles colour
        if(playerMove != null)
        {
            paintTile(playerMove);
            updateTurnLabel();

            if(gameManager.getTurnCount() == 1)
            {
                pieButton.setVisible(true);
            }
            else
            {
                pieButton.setVisible(false);
            }

            if(gameManager.isGameOver())
            {
                playAgainButton.setVisible(true);
            }

            callBotToMove();
        }
    }

    void callBotToMove()
    {
        PauseTransition pause = new PauseTransition(Duration.millis(650));
        // once pause is finished allow bot to move
        pause.setOnFinished(e -> {
            Move botMove = gameManager.botMove();
            if (botMove != null) {
                paintTile(botMove);
            }
            updateTurnLabel();

            if (gameManager.getTurnCount() == 1) {
                pieButton.setVisible(true);
            } else {
                pieButton.setVisible(false);
            }

            if (gameManager.isGameOver())
            {
                playAgainButton.setVisible(true);
            }
        });
        // start pause
        pause.play();


    }

    // paint a tile
    void paintTile(Move move)
    {
        Polygon tile = move.getTile();
        PlayerColour playerColour = move.getPlayer().getPlayerColour();
        tile.setFill(playerColour.switchToFXColour());
    }

    // activate pie rule
    @FXML
    void pieButtonClicked(MouseEvent event)
    {
        // activate pie rule then get bot to move
        gameManager.activatePieRule(gameManager.human);
        updateTurnLabel();
        pieButton.setVisible(false);

        // check if bot has to move now
        if(gameManager.getCurrentPlayer() == gameManager.bot)
        {
            callBotToMove();
        }
    }

    // update the UI label
    @FXML
    void updateTurnLabel()
    {
        PlayerColour colour = gameManager.getCurrentPlayer().getPlayerColour();
        // if game is over and there's a winner set the text to display who won
        if(gameManager.isGameOver() && gameManager.getWinner() != null)
        {
            playLabel.setText(gameManager.getWinner().getPlayerColour() + " wins");
        }
        // otherwise if game is over but winner remains null then we now board is full so display draw
        else if(gameManager.isGameOver() && gameManager.getWinner() == null)
        {
            playLabel.setText("Draw");
        }
        // otherwise game still on so display whose turn it is
        else
        {
            playLabel.setText(colour.getDisplayName() + " to play");
        }

        if(gameManager.getCurrentPlayer() == gameManager.human && DEV_MODE)
        {
            botEnabledBox.setVisible(true);
            strategyButton.setVisible(true);
        }
        else
        {
            botEnabledBox.setVisible(false);
            strategyButton.setVisible(false);
        }

        if(gameManager.isBotEnabled())
        {
            botEnabledBox.selectedProperty().set(true);
        }
        else
        {
            botEnabledBox.selectedProperty().set(false);
        }
    }

    // reset UI of board to play again
    @FXML
    void playAgainButtonClicked(MouseEvent event)
    {
        if(gameManager.resetGame())
        {
            // reset tile colours
           for(Polygon tile : tiles.values())
           {
               if(tile.getId().contains("Oct"))
               {
                   tile.setFill(emptyOctColour);
               }
               else
               {
                   tile.setFill(emptyRhoColour);
               }
           }

           updateTurnLabel();

           // check if bot moves first
           if(gameManager.getCurrentPlayer() == gameManager.bot)
           {
               callBotToMove();
           }

           playAgainButton.setVisible(false);
        }
    }

    @FXML
    void botEnabledBoxClicked()
    {
        if(!DEV_MODE)
        {
            return;
        }
        if(botEnabledBox.isSelected())
        {
            gameManager.setBotEnabled(true);
        }
        else
        {
            gameManager.setBotEnabled(false);
        }
        updateTurnLabel();
    }

    @FXML
    void showStrategyButtonClicked()
    {
        System.out.println("showStrategyButtonClicked");
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="OctA01"
    private Polygon OctA01; // Value injected by FXMLLoader

    @FXML // fx:id="OctA02"
    private Polygon OctA02; // Value injected by FXMLLoader

    @FXML // fx:id="OctA03"
    private Polygon OctA03; // Value injected by FXMLLoader

    @FXML // fx:id="OctA04"
    private Polygon OctA04; // Value injected by FXMLLoader

    @FXML // fx:id="OctA05"
    private Polygon OctA05; // Value injected by FXMLLoader

    @FXML // fx:id="OctA06"
    private Polygon OctA06; // Value injected by FXMLLoader

    @FXML // fx:id="OctA07"
    private Polygon OctA07; // Value injected by FXMLLoader

    @FXML // fx:id="OctA08"
    private Polygon OctA08; // Value injected by FXMLLoader

    @FXML // fx:id="OctA09"
    private Polygon OctA09; // Value injected by FXMLLoader

    @FXML // fx:id="OctA10"
    private Polygon OctA10; // Value injected by FXMLLoader

    @FXML // fx:id="OctA11"
    private Polygon OctA11; // Value injected by FXMLLoader

    @FXML // fx:id="OctB01"
    private Polygon OctB01; // Value injected by FXMLLoader

    @FXML // fx:id="OctB02"
    private Polygon OctB02; // Value injected by FXMLLoader

    @FXML // fx:id="OctB03"
    private Polygon OctB03; // Value injected by FXMLLoader

    @FXML // fx:id="OctB04"
    private Polygon OctB04; // Value injected by FXMLLoader

    @FXML // fx:id="OctB05"
    private Polygon OctB05; // Value injected by FXMLLoader

    @FXML // fx:id="OctB06"
    private Polygon OctB06; // Value injected by FXMLLoader

    @FXML // fx:id="OctB07"
    private Polygon OctB07; // Value injected by FXMLLoader

    @FXML // fx:id="OctB08"
    private Polygon OctB08; // Value injected by FXMLLoader

    @FXML // fx:id="OctB09"
    private Polygon OctB09; // Value injected by FXMLLoader

    @FXML // fx:id="OctB10"
    private Polygon OctB10; // Value injected by FXMLLoader

    @FXML // fx:id="OctB11"
    private Polygon OctB11; // Value injected by FXMLLoader

    @FXML // fx:id="OctC01"
    private Polygon OctC01; // Value injected by FXMLLoader

    @FXML // fx:id="OctC02"
    private Polygon OctC02; // Value injected by FXMLLoader

    @FXML // fx:id="OctC03"
    private Polygon OctC03; // Value injected by FXMLLoader

    @FXML // fx:id="OctC04"
    private Polygon OctC04; // Value injected by FXMLLoader

    @FXML // fx:id="OctC05"
    private Polygon OctC05; // Value injected by FXMLLoader

    @FXML // fx:id="OctC06"
    private Polygon OctC06; // Value injected by FXMLLoader

    @FXML // fx:id="OctC07"
    private Polygon OctC07; // Value injected by FXMLLoader

    @FXML // fx:id="OctC08"
    private Polygon OctC08; // Value injected by FXMLLoader

    @FXML // fx:id="OctC09"
    private Polygon OctC09; // Value injected by FXMLLoader

    @FXML // fx:id="OctC10"
    private Polygon OctC10; // Value injected by FXMLLoader

    @FXML // fx:id="OctC11"
    private Polygon OctC11; // Value injected by FXMLLoader

    @FXML // fx:id="OctD01"
    private Polygon OctD01; // Value injected by FXMLLoader

    @FXML // fx:id="OctD02"
    private Polygon OctD02; // Value injected by FXMLLoader

    @FXML // fx:id="OctD03"
    private Polygon OctD03; // Value injected by FXMLLoader

    @FXML // fx:id="OctD04"
    private Polygon OctD04; // Value injected by FXMLLoader

    @FXML // fx:id="OctD05"
    private Polygon OctD05; // Value injected by FXMLLoader

    @FXML // fx:id="OctD06"
    private Polygon OctD06; // Value injected by FXMLLoader

    @FXML // fx:id="OctD07"
    private Polygon OctD07; // Value injected by FXMLLoader

    @FXML // fx:id="OctD08"
    private Polygon OctD08; // Value injected by FXMLLoader

    @FXML // fx:id="OctD09"
    private Polygon OctD09; // Value injected by FXMLLoader

    @FXML // fx:id="OctD10"
    private Polygon OctD10; // Value injected by FXMLLoader

    @FXML // fx:id="OctD11"
    private Polygon OctD11; // Value injected by FXMLLoader

    @FXML // fx:id="OctE01"
    private Polygon OctE01; // Value injected by FXMLLoader

    @FXML // fx:id="OctE02"
    private Polygon OctE02; // Value injected by FXMLLoader

    @FXML // fx:id="OctE03"
    private Polygon OctE03; // Value injected by FXMLLoader

    @FXML // fx:id="OctE04"
    private Polygon OctE04; // Value injected by FXMLLoader

    @FXML // fx:id="OctE05"
    private Polygon OctE05; // Value injected by FXMLLoader

    @FXML // fx:id="OctE06"
    private Polygon OctE06; // Value injected by FXMLLoader

    @FXML // fx:id="OctE07"
    private Polygon OctE07; // Value injected by FXMLLoader

    @FXML // fx:id="OctE08"
    private Polygon OctE08; // Value injected by FXMLLoader

    @FXML // fx:id="OctE09"
    private Polygon OctE09; // Value injected by FXMLLoader

    @FXML // fx:id="OctE10"
    private Polygon OctE10; // Value injected by FXMLLoader

    @FXML // fx:id="OctE11"
    private Polygon OctE11; // Value injected by FXMLLoader

    @FXML // fx:id="OctF01"
    private Polygon OctF01; // Value injected by FXMLLoader

    @FXML // fx:id="OctF02"
    private Polygon OctF02; // Value injected by FXMLLoader

    @FXML // fx:id="OctF03"
    private Polygon OctF03; // Value injected by FXMLLoader

    @FXML // fx:id="OctF04"
    private Polygon OctF04; // Value injected by FXMLLoader

    @FXML // fx:id="OctF05"
    private Polygon OctF05; // Value injected by FXMLLoader

    @FXML // fx:id="OctF06"
    private Polygon OctF06; // Value injected by FXMLLoader

    @FXML // fx:id="OctF07"
    private Polygon OctF07; // Value injected by FXMLLoader

    @FXML // fx:id="OctF08"
    private Polygon OctF08; // Value injected by FXMLLoader

    @FXML // fx:id="OctF09"
    private Polygon OctF09; // Value injected by FXMLLoader

    @FXML // fx:id="OctF10"
    private Polygon OctF10; // Value injected by FXMLLoader

    @FXML // fx:id="OctF11"
    private Polygon OctF11; // Value injected by FXMLLoader

    @FXML // fx:id="OctG01"
    private Polygon OctG01; // Value injected by FXMLLoader

    @FXML // fx:id="OctG02"
    private Polygon OctG02; // Value injected by FXMLLoader

    @FXML // fx:id="OctG03"
    private Polygon OctG03; // Value injected by FXMLLoader

    @FXML // fx:id="OctG04"
    private Polygon OctG04; // Value injected by FXMLLoader

    @FXML // fx:id="OctG05"
    private Polygon OctG05; // Value injected by FXMLLoader

    @FXML // fx:id="OctG06"
    private Polygon OctG06; // Value injected by FXMLLoader

    @FXML // fx:id="OctG07"
    private Polygon OctG07; // Value injected by FXMLLoader

    @FXML // fx:id="OctG08"
    private Polygon OctG08; // Value injected by FXMLLoader

    @FXML // fx:id="OctG09"
    private Polygon OctG09; // Value injected by FXMLLoader

    @FXML // fx:id="OctG10"
    private Polygon OctG10; // Value injected by FXMLLoader

    @FXML // fx:id="OctG11"
    private Polygon OctG11; // Value injected by FXMLLoader

    @FXML // fx:id="OctH01"
    private Polygon OctH01; // Value injected by FXMLLoader

    @FXML // fx:id="OctH02"
    private Polygon OctH02; // Value injected by FXMLLoader

    @FXML // fx:id="OctH03"
    private Polygon OctH03; // Value injected by FXMLLoader

    @FXML // fx:id="OctH04"
    private Polygon OctH04; // Value injected by FXMLLoader

    @FXML // fx:id="OctH05"
    private Polygon OctH05; // Value injected by FXMLLoader

    @FXML // fx:id="OctH06"
    private Polygon OctH06; // Value injected by FXMLLoader

    @FXML // fx:id="OctH07"
    private Polygon OctH07; // Value injected by FXMLLoader

    @FXML // fx:id="OctH08"
    private Polygon OctH08; // Value injected by FXMLLoader

    @FXML // fx:id="OctH09"
    private Polygon OctH09; // Value injected by FXMLLoader

    @FXML // fx:id="OctH10"
    private Polygon OctH10; // Value injected by FXMLLoader

    @FXML // fx:id="OctH11"
    private Polygon OctH11; // Value injected by FXMLLoader

    @FXML // fx:id="OctI01"
    private Polygon OctI01; // Value injected by FXMLLoader

    @FXML // fx:id="OctI02"
    private Polygon OctI02; // Value injected by FXMLLoader

    @FXML // fx:id="OctI03"
    private Polygon OctI03; // Value injected by FXMLLoader

    @FXML // fx:id="OctI04"
    private Polygon OctI04; // Value injected by FXMLLoader

    @FXML // fx:id="OctI05"
    private Polygon OctI05; // Value injected by FXMLLoader

    @FXML // fx:id="OctI06"
    private Polygon OctI06; // Value injected by FXMLLoader

    @FXML // fx:id="OctI07"
    private Polygon OctI07; // Value injected by FXMLLoader

    @FXML // fx:id="OctI08"
    private Polygon OctI08; // Value injected by FXMLLoader

    @FXML // fx:id="OctI09"
    private Polygon OctI09; // Value injected by FXMLLoader

    @FXML // fx:id="OctI10"
    private Polygon OctI10; // Value injected by FXMLLoader

    @FXML // fx:id="OctI11"
    private Polygon OctI11; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ01"
    private Polygon OctJ01; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ02"
    private Polygon OctJ02; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ03"
    private Polygon OctJ03; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ04"
    private Polygon OctJ04; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ05"
    private Polygon OctJ05; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ06"
    private Polygon OctJ06; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ07"
    private Polygon OctJ07; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ08"
    private Polygon OctJ08; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ09"
    private Polygon OctJ09; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ10"
    private Polygon OctJ10; // Value injected by FXMLLoader

    @FXML // fx:id="OctJ11"
    private Polygon OctJ11; // Value injected by FXMLLoader

    @FXML // fx:id="OctK01"
    private Polygon OctK01; // Value injected by FXMLLoader

    @FXML // fx:id="OctK02"
    private Polygon OctK02; // Value injected by FXMLLoader

    @FXML // fx:id="OctK03"
    private Polygon OctK03; // Value injected by FXMLLoader

    @FXML // fx:id="OctK04"
    private Polygon OctK04; // Value injected by FXMLLoader

    @FXML // fx:id="OctK05"
    private Polygon OctK05; // Value injected by FXMLLoader

    @FXML // fx:id="OctK06"
    private Polygon OctK06; // Value injected by FXMLLoader

    @FXML // fx:id="OctK07"
    private Polygon OctK07; // Value injected by FXMLLoader

    @FXML // fx:id="OctK08"
    private Polygon OctK08; // Value injected by FXMLLoader

    @FXML // fx:id="OctK09"
    private Polygon OctK09; // Value injected by FXMLLoader

    @FXML // fx:id="OctK10"
    private Polygon OctK10; // Value injected by FXMLLoader

    @FXML // fx:id="OctK11"
    private Polygon OctK11; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA01"
    private Polygon RhoA01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA02"
    private Polygon RhoA02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA03"
    private Polygon RhoA03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA04"
    private Polygon RhoA04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA05"
    private Polygon RhoA05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA06"
    private Polygon RhoA06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA07"
    private Polygon RhoA07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA08"
    private Polygon RhoA08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA09"
    private Polygon RhoA09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoA10"
    private Polygon RhoA10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB01"
    private Polygon RhoB01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB02"
    private Polygon RhoB02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB03"
    private Polygon RhoB03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB04"
    private Polygon RhoB04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB05"
    private Polygon RhoB05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB06"
    private Polygon RhoB06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB07"
    private Polygon RhoB07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB08"
    private Polygon RhoB08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB09"
    private Polygon RhoB09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoB10"
    private Polygon RhoB10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC01"
    private Polygon RhoC01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC02"
    private Polygon RhoC02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC03"
    private Polygon RhoC03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC04"
    private Polygon RhoC04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC05"
    private Polygon RhoC05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC06"
    private Polygon RhoC06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC07"
    private Polygon RhoC07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC08"
    private Polygon RhoC08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC09"
    private Polygon RhoC09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoC10"
    private Polygon RhoC10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD01"
    private Polygon RhoD01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD02"
    private Polygon RhoD02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD03"
    private Polygon RhoD03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD04"
    private Polygon RhoD04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD05"
    private Polygon RhoD05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD06"
    private Polygon RhoD06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD07"
    private Polygon RhoD07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD08"
    private Polygon RhoD08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD09"
    private Polygon RhoD09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoD10"
    private Polygon RhoD10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE01"
    private Polygon RhoE01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE02"
    private Polygon RhoE02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE03"
    private Polygon RhoE03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE04"
    private Polygon RhoE04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE05"
    private Polygon RhoE05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE06"
    private Polygon RhoE06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE07"
    private Polygon RhoE07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE08"
    private Polygon RhoE08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE09"
    private Polygon RhoE09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoE10"
    private Polygon RhoE10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF01"
    private Polygon RhoF01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF02"
    private Polygon RhoF02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF03"
    private Polygon RhoF03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF04"
    private Polygon RhoF04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF05"
    private Polygon RhoF05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF06"
    private Polygon RhoF06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF07"
    private Polygon RhoF07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF08"
    private Polygon RhoF08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF09"
    private Polygon RhoF09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoF10"
    private Polygon RhoF10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG01"
    private Polygon RhoG01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG02"
    private Polygon RhoG02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG03"
    private Polygon RhoG03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG04"
    private Polygon RhoG04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG05"
    private Polygon RhoG05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG06"
    private Polygon RhoG06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG07"
    private Polygon RhoG07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG08"
    private Polygon RhoG08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG09"
    private Polygon RhoG09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoG10"
    private Polygon RhoG10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH01"
    private Polygon RhoH01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH02"
    private Polygon RhoH02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH03"
    private Polygon RhoH03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH04"
    private Polygon RhoH04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH05"
    private Polygon RhoH05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH06"
    private Polygon RhoH06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH07"
    private Polygon RhoH07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH08"
    private Polygon RhoH08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH09"
    private Polygon RhoH09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoH10"
    private Polygon RhoH10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI01"
    private Polygon RhoI01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI02"
    private Polygon RhoI02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI03"
    private Polygon RhoI03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI04"
    private Polygon RhoI04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI05"
    private Polygon RhoI05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI06"
    private Polygon RhoI06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI07"
    private Polygon RhoI07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI08"
    private Polygon RhoI08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI09"
    private Polygon RhoI09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoI10"
    private Polygon RhoI10; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ01"
    private Polygon RhoJ01; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ02"
    private Polygon RhoJ02; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ03"
    private Polygon RhoJ03; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ04"
    private Polygon RhoJ04; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ05"
    private Polygon RhoJ05; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ06"
    private Polygon RhoJ06; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ07"
    private Polygon RhoJ07; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ08"
    private Polygon RhoJ08; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ09"
    private Polygon RhoJ09; // Value injected by FXMLLoader

    @FXML // fx:id="RhoJ10"
    private Polygon RhoJ10; // Value injected by FXMLLoader

    @FXML // fx:id="botEnabledBox"
    private CheckBox botEnabledBox; // Value injected by FXMLLoader

    @FXML // fx:id="pieButton"
    private Button pieButton; // Value injected by FXMLLoader

    @FXML // fx:id="playAgainButton"
    private Button playAgainButton; // Value injected by FXMLLoader

    @FXML // fx:id="playLabel"
    private Label playLabel; // Value injected by FXMLLoader

    @FXML // fx:id="strategyButton"
    private Button strategyButton; // Value injected by FXMLLoader


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert OctA01 != null : "fx:id=\"OctA01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA02 != null : "fx:id=\"OctA02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA03 != null : "fx:id=\"OctA03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA04 != null : "fx:id=\"OctA04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA05 != null : "fx:id=\"OctA05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA06 != null : "fx:id=\"OctA06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA07 != null : "fx:id=\"OctA07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA08 != null : "fx:id=\"OctA08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA09 != null : "fx:id=\"OctA09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA10 != null : "fx:id=\"OctA10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctA11 != null : "fx:id=\"OctA11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB01 != null : "fx:id=\"OctB01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB02 != null : "fx:id=\"OctB02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB03 != null : "fx:id=\"OctB03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB04 != null : "fx:id=\"OctB04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB05 != null : "fx:id=\"OctB05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB06 != null : "fx:id=\"OctB06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB07 != null : "fx:id=\"OctB07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB08 != null : "fx:id=\"OctB08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB09 != null : "fx:id=\"OctB09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB10 != null : "fx:id=\"OctB10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctB11 != null : "fx:id=\"OctB11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC01 != null : "fx:id=\"OctC01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC02 != null : "fx:id=\"OctC02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC03 != null : "fx:id=\"OctC03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC04 != null : "fx:id=\"OctC04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC05 != null : "fx:id=\"OctC05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC06 != null : "fx:id=\"OctC06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC07 != null : "fx:id=\"OctC07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC08 != null : "fx:id=\"OctC08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC09 != null : "fx:id=\"OctC09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC10 != null : "fx:id=\"OctC10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctC11 != null : "fx:id=\"OctC11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD01 != null : "fx:id=\"OctD01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD02 != null : "fx:id=\"OctD02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD03 != null : "fx:id=\"OctD03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD04 != null : "fx:id=\"OctD04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD05 != null : "fx:id=\"OctD05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD06 != null : "fx:id=\"OctD06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD07 != null : "fx:id=\"OctD07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD08 != null : "fx:id=\"OctD08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD09 != null : "fx:id=\"OctD09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD10 != null : "fx:id=\"OctD10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctD11 != null : "fx:id=\"OctD11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE01 != null : "fx:id=\"OctE01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE02 != null : "fx:id=\"OctE02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE03 != null : "fx:id=\"OctE03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE04 != null : "fx:id=\"OctE04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE05 != null : "fx:id=\"OctE05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE06 != null : "fx:id=\"OctE06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE07 != null : "fx:id=\"OctE07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE08 != null : "fx:id=\"OctE08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE09 != null : "fx:id=\"OctE09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE10 != null : "fx:id=\"OctE10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctE11 != null : "fx:id=\"OctE11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF01 != null : "fx:id=\"OctF01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF02 != null : "fx:id=\"OctF02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF03 != null : "fx:id=\"OctF03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF04 != null : "fx:id=\"OctF04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF05 != null : "fx:id=\"OctF05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF06 != null : "fx:id=\"OctF06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF07 != null : "fx:id=\"OctF07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF08 != null : "fx:id=\"OctF08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF09 != null : "fx:id=\"OctF09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF10 != null : "fx:id=\"OctF10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctF11 != null : "fx:id=\"OctF11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG01 != null : "fx:id=\"OctG01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG02 != null : "fx:id=\"OctG02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG03 != null : "fx:id=\"OctG03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG04 != null : "fx:id=\"OctG04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG05 != null : "fx:id=\"OctG05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG06 != null : "fx:id=\"OctG06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG07 != null : "fx:id=\"OctG07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG08 != null : "fx:id=\"OctG08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG09 != null : "fx:id=\"OctG09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG10 != null : "fx:id=\"OctG10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctG11 != null : "fx:id=\"OctG11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH01 != null : "fx:id=\"OctH01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH02 != null : "fx:id=\"OctH02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH03 != null : "fx:id=\"OctH03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH04 != null : "fx:id=\"OctH04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH05 != null : "fx:id=\"OctH05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH06 != null : "fx:id=\"OctH06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH07 != null : "fx:id=\"OctH07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH08 != null : "fx:id=\"OctH08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH09 != null : "fx:id=\"OctH09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH10 != null : "fx:id=\"OctH10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctH11 != null : "fx:id=\"OctH11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI01 != null : "fx:id=\"OctI01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI02 != null : "fx:id=\"OctI02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI03 != null : "fx:id=\"OctI03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI04 != null : "fx:id=\"OctI04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI05 != null : "fx:id=\"OctI05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI06 != null : "fx:id=\"OctI06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI07 != null : "fx:id=\"OctI07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI08 != null : "fx:id=\"OctI08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI09 != null : "fx:id=\"OctI09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI10 != null : "fx:id=\"OctI10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctI11 != null : "fx:id=\"OctI11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ01 != null : "fx:id=\"OctJ01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ02 != null : "fx:id=\"OctJ02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ03 != null : "fx:id=\"OctJ03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ04 != null : "fx:id=\"OctJ04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ05 != null : "fx:id=\"OctJ05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ06 != null : "fx:id=\"OctJ06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ07 != null : "fx:id=\"OctJ07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ08 != null : "fx:id=\"OctJ08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ09 != null : "fx:id=\"OctJ09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ10 != null : "fx:id=\"OctJ10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctJ11 != null : "fx:id=\"OctJ11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK01 != null : "fx:id=\"OctK01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK02 != null : "fx:id=\"OctK02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK03 != null : "fx:id=\"OctK03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK04 != null : "fx:id=\"OctK04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK05 != null : "fx:id=\"OctK05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK06 != null : "fx:id=\"OctK06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK07 != null : "fx:id=\"OctK07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK08 != null : "fx:id=\"OctK08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK09 != null : "fx:id=\"OctK09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK10 != null : "fx:id=\"OctK10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert OctK11 != null : "fx:id=\"OctK11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA01 != null : "fx:id=\"RhoA01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA02 != null : "fx:id=\"RhoA02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA03 != null : "fx:id=\"RhoA03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA04 != null : "fx:id=\"RhoA04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA05 != null : "fx:id=\"RhoA05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA06 != null : "fx:id=\"RhoA06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA07 != null : "fx:id=\"RhoA07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA08 != null : "fx:id=\"RhoA08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA09 != null : "fx:id=\"RhoA09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoA10 != null : "fx:id=\"RhoA10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB01 != null : "fx:id=\"RhoB01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB02 != null : "fx:id=\"RhoB02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB03 != null : "fx:id=\"RhoB03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB04 != null : "fx:id=\"RhoB04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB05 != null : "fx:id=\"RhoB05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB06 != null : "fx:id=\"RhoB06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB07 != null : "fx:id=\"RhoB07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB08 != null : "fx:id=\"RhoB08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB09 != null : "fx:id=\"RhoB09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoB10 != null : "fx:id=\"RhoB10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC01 != null : "fx:id=\"RhoC01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC02 != null : "fx:id=\"RhoC02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC03 != null : "fx:id=\"RhoC03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC04 != null : "fx:id=\"RhoC04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC05 != null : "fx:id=\"RhoC05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC06 != null : "fx:id=\"RhoC06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC07 != null : "fx:id=\"RhoC07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC08 != null : "fx:id=\"RhoC08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC09 != null : "fx:id=\"RhoC09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoC10 != null : "fx:id=\"RhoC10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD01 != null : "fx:id=\"RhoD01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD02 != null : "fx:id=\"RhoD02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD03 != null : "fx:id=\"RhoD03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD04 != null : "fx:id=\"RhoD04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD05 != null : "fx:id=\"RhoD05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD06 != null : "fx:id=\"RhoD06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD07 != null : "fx:id=\"RhoD07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD08 != null : "fx:id=\"RhoD08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD09 != null : "fx:id=\"RhoD09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoD10 != null : "fx:id=\"RhoD10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE01 != null : "fx:id=\"RhoE01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE02 != null : "fx:id=\"RhoE02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE03 != null : "fx:id=\"RhoE03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE04 != null : "fx:id=\"RhoE04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE05 != null : "fx:id=\"RhoE05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE06 != null : "fx:id=\"RhoE06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE07 != null : "fx:id=\"RhoE07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE08 != null : "fx:id=\"RhoE08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE09 != null : "fx:id=\"RhoE09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoE10 != null : "fx:id=\"RhoE10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF01 != null : "fx:id=\"RhoF01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF02 != null : "fx:id=\"RhoF02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF03 != null : "fx:id=\"RhoF03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF04 != null : "fx:id=\"RhoF04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF05 != null : "fx:id=\"RhoF05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF06 != null : "fx:id=\"RhoF06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF07 != null : "fx:id=\"RhoF07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF08 != null : "fx:id=\"RhoF08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF09 != null : "fx:id=\"RhoF09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoF10 != null : "fx:id=\"RhoF10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG01 != null : "fx:id=\"RhoG01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG02 != null : "fx:id=\"RhoG02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG03 != null : "fx:id=\"RhoG03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG04 != null : "fx:id=\"RhoG04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG05 != null : "fx:id=\"RhoG05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG06 != null : "fx:id=\"RhoG06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG07 != null : "fx:id=\"RhoG07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG08 != null : "fx:id=\"RhoG08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG09 != null : "fx:id=\"RhoG09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoG10 != null : "fx:id=\"RhoG10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH01 != null : "fx:id=\"RhoH01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH02 != null : "fx:id=\"RhoH02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH03 != null : "fx:id=\"RhoH03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH04 != null : "fx:id=\"RhoH04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH05 != null : "fx:id=\"RhoH05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH06 != null : "fx:id=\"RhoH06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH07 != null : "fx:id=\"RhoH07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH08 != null : "fx:id=\"RhoH08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH09 != null : "fx:id=\"RhoH09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoH10 != null : "fx:id=\"RhoH10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI01 != null : "fx:id=\"RhoI01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI02 != null : "fx:id=\"RhoI02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI03 != null : "fx:id=\"RhoI03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI04 != null : "fx:id=\"RhoI04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI05 != null : "fx:id=\"RhoI05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI06 != null : "fx:id=\"RhoI06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI07 != null : "fx:id=\"RhoI07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI08 != null : "fx:id=\"RhoI08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI09 != null : "fx:id=\"RhoI09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoI10 != null : "fx:id=\"RhoI10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ01 != null : "fx:id=\"RhoJ01\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ02 != null : "fx:id=\"RhoJ02\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ03 != null : "fx:id=\"RhoJ03\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ04 != null : "fx:id=\"RhoJ04\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ05 != null : "fx:id=\"RhoJ05\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ06 != null : "fx:id=\"RhoJ06\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ07 != null : "fx:id=\"RhoJ07\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ08 != null : "fx:id=\"RhoJ08\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ09 != null : "fx:id=\"RhoJ09\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RhoJ10 != null : "fx:id=\"RhoJ10\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert pieButton != null : "fx:id=\"pieButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert playAgainButton != null : "fx:id=\"playAgainButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert playLabel != null : "fx:id=\"playLabel\" was not injected: check your FXML file 'hello-view.fxml'.";
        // hashmap setup
        tiles.put("OctA01", OctA01);
        tiles.put("OctA02", OctA02);
        tiles.put("OctA03", OctA03);
        tiles.put("OctA04", OctA04);
        tiles.put("OctA05", OctA05);
        tiles.put("OctA06", OctA06);
        tiles.put("OctA07", OctA07);
        tiles.put("OctA08", OctA08);
        tiles.put("OctA09", OctA09);
        tiles.put("OctA10", OctA10);
        tiles.put("OctA11", OctA11);
        tiles.put("OctB01", OctB01);
        tiles.put("OctB02", OctB02);
        tiles.put("OctB03", OctB03);
        tiles.put("OctB04", OctB04);
        tiles.put("OctB05", OctB05);
        tiles.put("OctB06", OctB06);
        tiles.put("OctB07", OctB07);
        tiles.put("OctB08", OctB08);
        tiles.put("OctB09", OctB09);
        tiles.put("OctB10", OctB10);
        tiles.put("OctB11", OctB11);
        tiles.put("OctC01", OctC01);
        tiles.put("OctC02", OctC02);
        tiles.put("OctC03", OctC03);
        tiles.put("OctC04", OctC04);
        tiles.put("OctC05", OctC05);
        tiles.put("OctC06", OctC06);
        tiles.put("OctC07", OctC07);
        tiles.put("OctC08", OctC08);
        tiles.put("OctC09", OctC09);
        tiles.put("OctC10", OctC10);
        tiles.put("OctC11", OctC11);
        tiles.put("OctD01", OctD01);
        tiles.put("OctD02", OctD02);
        tiles.put("OctD03", OctD03);
        tiles.put("OctD04", OctD04);
        tiles.put("OctD05", OctD05);
        tiles.put("OctD06", OctD06);
        tiles.put("OctD07", OctD07);
        tiles.put("OctD08", OctD08);
        tiles.put("OctD09", OctD09);
        tiles.put("OctD10", OctD10);
        tiles.put("OctD11", OctD11);
        tiles.put("OctE01", OctE01);
        tiles.put("OctE02", OctE02);
        tiles.put("OctE03", OctE03);
        tiles.put("OctE04", OctE04);
        tiles.put("OctE05", OctE05);
        tiles.put("OctE06", OctE06);
        tiles.put("OctE07", OctE07);
        tiles.put("OctE08", OctE08);
        tiles.put("OctE09", OctE09);
        tiles.put("OctE10", OctE10);
        tiles.put("OctE11", OctE11);
        tiles.put("OctF01", OctF01);
        tiles.put("OctF02", OctF02);
        tiles.put("OctF03", OctF03);
        tiles.put("OctF04", OctF04);
        tiles.put("OctF05", OctF05);
        tiles.put("OctF06", OctF06);
        tiles.put("OctF07", OctF07);
        tiles.put("OctF08", OctF08);
        tiles.put("OctF09", OctF09);
        tiles.put("OctF10", OctF10);
        tiles.put("OctF11", OctF11);
        tiles.put("OctG01", OctG01);
        tiles.put("OctG02", OctG02);
        tiles.put("OctG03", OctG03);
        tiles.put("OctG04", OctG04);
        tiles.put("OctG05", OctG05);
        tiles.put("OctG06", OctG06);
        tiles.put("OctG07", OctG07);
        tiles.put("OctG08", OctG08);
        tiles.put("OctG09", OctG09);
        tiles.put("OctG10", OctG10);
        tiles.put("OctG11", OctG11);
        tiles.put("OctH01", OctH01);
        tiles.put("OctH02", OctH02);
        tiles.put("OctH03", OctH03);
        tiles.put("OctH04", OctH04);
        tiles.put("OctH05", OctH05);
        tiles.put("OctH06", OctH06);
        tiles.put("OctH07", OctH07);
        tiles.put("OctH08", OctH08);
        tiles.put("OctH09", OctH09);
        tiles.put("OctH10", OctH10);
        tiles.put("OctH11", OctH11);
        tiles.put("OctI01", OctI01);
        tiles.put("OctI02", OctI02);
        tiles.put("OctI03", OctI03);
        tiles.put("OctI04", OctI04);
        tiles.put("OctI05", OctI05);
        tiles.put("OctI06", OctI06);
        tiles.put("OctI07", OctI07);
        tiles.put("OctI08", OctI08);
        tiles.put("OctI09", OctI09);
        tiles.put("OctI10", OctI10);
        tiles.put("OctI11", OctI11);
        tiles.put("OctJ01", OctJ01);
        tiles.put("OctJ02", OctJ02);
        tiles.put("OctJ03", OctJ03);
        tiles.put("OctJ04", OctJ04);
        tiles.put("OctJ05", OctJ05);
        tiles.put("OctJ06", OctJ06);
        tiles.put("OctJ07", OctJ07);
        tiles.put("OctJ08", OctJ08);
        tiles.put("OctJ09", OctJ09);
        tiles.put("OctJ10", OctJ10);
        tiles.put("OctJ11", OctJ11);
        tiles.put("OctK01", OctK01);
        tiles.put("OctK02", OctK02);
        tiles.put("OctK03", OctK03);
        tiles.put("OctK04", OctK04);
        tiles.put("OctK05", OctK05);
        tiles.put("OctK06", OctK06);
        tiles.put("OctK07", OctK07);
        tiles.put("OctK08", OctK08);
        tiles.put("OctK09", OctK09);
        tiles.put("OctK10", OctK10);
        tiles.put("OctK11", OctK11);
        tiles.put("RhoA01", RhoA01);
        tiles.put("RhoA02", RhoA02);
        tiles.put("RhoA03", RhoA03);
        tiles.put("RhoA04", RhoA04);
        tiles.put("RhoA05", RhoA05);
        tiles.put("RhoA06", RhoA06);
        tiles.put("RhoA07", RhoA07);
        tiles.put("RhoA08", RhoA08);
        tiles.put("RhoA09", RhoA09);
        tiles.put("RhoA10", RhoA10);
        tiles.put("RhoB01", RhoB01);
        tiles.put("RhoB02", RhoB02);
        tiles.put("RhoB03", RhoB03);
        tiles.put("RhoB04", RhoB04);
        tiles.put("RhoB05", RhoB05);
        tiles.put("RhoB06", RhoB06);
        tiles.put("RhoB07", RhoB07);
        tiles.put("RhoB08", RhoB08);
        tiles.put("RhoB09", RhoB09);
        tiles.put("RhoB10", RhoB10);
        tiles.put("RhoC01", RhoC01);
        tiles.put("RhoC02", RhoC02);
        tiles.put("RhoC03", RhoC03);
        tiles.put("RhoC04", RhoC04);
        tiles.put("RhoC05", RhoC05);
        tiles.put("RhoC06", RhoC06);
        tiles.put("RhoC07", RhoC07);
        tiles.put("RhoC08", RhoC08);
        tiles.put("RhoC09", RhoC09);
        tiles.put("RhoC10", RhoC10);
        tiles.put("RhoD01", RhoD01);
        tiles.put("RhoD02", RhoD02);
        tiles.put("RhoD03", RhoD03);
        tiles.put("RhoD04", RhoD04);
        tiles.put("RhoD05", RhoD05);
        tiles.put("RhoD06", RhoD06);
        tiles.put("RhoD07", RhoD07);
        tiles.put("RhoD08", RhoD08);
        tiles.put("RhoD09", RhoD09);
        tiles.put("RhoD10", RhoD10);
        tiles.put("RhoE01", RhoE01);
        tiles.put("RhoE02", RhoE02);
        tiles.put("RhoE03", RhoE03);
        tiles.put("RhoE04", RhoE04);
        tiles.put("RhoE05", RhoE05);
        tiles.put("RhoE06", RhoE06);
        tiles.put("RhoE07", RhoE07);
        tiles.put("RhoE08", RhoE08);
        tiles.put("RhoE09", RhoE09);
        tiles.put("RhoE10", RhoE10);
        tiles.put("RhoF01", RhoF01);
        tiles.put("RhoF02", RhoF02);
        tiles.put("RhoF03", RhoF03);
        tiles.put("RhoF04", RhoF04);
        tiles.put("RhoF05", RhoF05);
        tiles.put("RhoF06", RhoF06);
        tiles.put("RhoF07", RhoF07);
        tiles.put("RhoF08", RhoF08);
        tiles.put("RhoF09", RhoF09);
        tiles.put("RhoF10", RhoF10);
        tiles.put("RhoG01", RhoG01);
        tiles.put("RhoG02", RhoG02);
        tiles.put("RhoG03", RhoG03);
        tiles.put("RhoG04", RhoG04);
        tiles.put("RhoG05", RhoG05);
        tiles.put("RhoG06", RhoG06);
        tiles.put("RhoG07", RhoG07);
        tiles.put("RhoG08", RhoG08);
        tiles.put("RhoG09", RhoG09);
        tiles.put("RhoG10", RhoG10);
        tiles.put("RhoH01", RhoH01);
        tiles.put("RhoH02", RhoH02);
        tiles.put("RhoH03", RhoH03);
        tiles.put("RhoH04", RhoH04);
        tiles.put("RhoH05", RhoH05);
        tiles.put("RhoH06", RhoH06);
        tiles.put("RhoH07", RhoH07);
        tiles.put("RhoH08", RhoH08);
        tiles.put("RhoH09", RhoH09);
        tiles.put("RhoH10", RhoH10);
        tiles.put("RhoI01", RhoI01);
        tiles.put("RhoI02", RhoI02);
        tiles.put("RhoI03", RhoI03);
        tiles.put("RhoI04", RhoI04);
        tiles.put("RhoI05", RhoI05);
        tiles.put("RhoI06", RhoI06);
        tiles.put("RhoI07", RhoI07);
        tiles.put("RhoI08", RhoI08);
        tiles.put("RhoI09", RhoI09);
        tiles.put("RhoI10", RhoI10);
        tiles.put("RhoJ01", RhoJ01);
        tiles.put("RhoJ02", RhoJ02);
        tiles.put("RhoJ03", RhoJ03);
        tiles.put("RhoJ04", RhoJ04);
        tiles.put("RhoJ05", RhoJ05);
        tiles.put("RhoJ06", RhoJ06);
        tiles.put("RhoJ07", RhoJ07);
        tiles.put("RhoJ08", RhoJ08);
        tiles.put("RhoJ09", RhoJ09);
        tiles.put("RhoJ10", RhoJ10);
        // display or hide dev tools based on if dev mode is enabled
        if(DEV_MODE)
        {
            botEnabledBox.setVisible(true);
            strategyButton.setVisible(true);
        }
        else
        {
            botEnabledBox.setVisible(false);
            strategyButton.setVisible(false);
        }
        // randomly pick who starts as black and white
        int random = (int) (Math.random() * 2);
        if (random == 0)
        {
            gameManager.setUpGame(gameManager.human);
        }
        else
        {
            gameManager.setUpGame(gameManager.bot);
            callBotToMove();
        }
    }

}
