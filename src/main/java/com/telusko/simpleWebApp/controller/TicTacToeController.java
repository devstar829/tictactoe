package com.telusko.simpleWebApp.controller;

import com.telusko.simpleWebApp.model.GameState;
import com.telusko.simpleWebApp.model.TicTacToeAI;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tictactoe")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class TicTacToeController {
    private TicTacToeAI ai = new TicTacToeAI();

    @PostMapping("/move")
    public GameState makeMove(@RequestBody GameState gameState, @RequestParam boolean isPlayerMove) {
        if (isPlayerMove) {
            gameState.setCurrentPlayer('O');
            int[] aiMove = ai.findBestMove(gameState.getBoard());
            gameState.getBoard()[aiMove[0]][aiMove[1]] = 'O';
            gameState.setCurrentPlayer('X');
        }

        return gameState;
    }
}
