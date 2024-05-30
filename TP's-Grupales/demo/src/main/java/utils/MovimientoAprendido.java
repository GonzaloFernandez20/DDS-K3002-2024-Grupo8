package utils;

import dominio.Move;

public class MovimientoAprendido {
    private Move move;

    public Move getMove() { return move; }

    public void setMove(Move move) { this.move = move; }

    public String conseguirName() { return move.getName(); }
}
