package com.kelton.gol.viewmodel;

import com.kelton.gol.model.Board;
import com.kelton.gol.util.Property;

import java.util.LinkedList;
import java.util.List;

public class BoardViewModel {

    private Property<Board> board = new Property<>();

    public Property<Board> getBoard() {
        return board;
    }
}
