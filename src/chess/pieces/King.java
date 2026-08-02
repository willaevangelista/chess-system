package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position position = new Position(0, 0);

        // above
        position.setValues(this.position.getRow() - 1, this.position.getColumn());
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // below
        position.setValues(this.position.getRow() + 1, this.position.getColumn());
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // rigth
        position.setValues(this.position.getRow(), this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // left
        position.setValues(this.position.getRow(), this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // nw
        position.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // ne
        position.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // sw
        position.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // se
        position.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            matrix[position.getRow()][position.getColumn()] = true;
        }

        // #specialmove castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // #specialmove castling kingside rook
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    matrix[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            // #specialmove castling queenside rook
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    matrix[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return matrix;
    }
}
