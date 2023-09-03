package com.movietheater.manager.movietheatermanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class TicketController {

    @FXML
    private ImageView seatA1, seatA2, seatA3, seatB1, seatB2, seatB3, seatC1, seatC2, seatC3;

    private Image freeImage, occupiedImage, takenImage;

    private ImageView[][] seats;

    public void initialize() {
        occupiedImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("unavailable_seat.png")), 40, 40, false, false);
        freeImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("seat.png")), 40, 40, false, false);
        takenImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("seat_taken.png")), 40, 40, false, false);

        seats = new ImageView[][]{
                {seatA1, seatA2, seatA3},
                {seatB1, seatB2, seatB3},
                {seatC1, seatC2, seatC3}
        };

        for (ImageView[] seat : seats) {
            for (ImageView imageView : seat) {
                imageView.setImage(freeImage);
            }
        }

    }

    @FXML
    private void finish() {
        for (ImageView[] seat : seats) {
            for (ImageView imageView : seat) {
                if (imageView.getImage() == takenImage) {
                    imageView.setImage(occupiedImage);
                }
            }
        }
    }
    @FXML
    private void onSeatClick(MouseEvent event) {
        ImageView clickedSeat = (ImageView) event.getSource();

        int rowIndex = -1;
        int colIndex = -1;

        for (int row = 0; row < seats.length && rowIndex == -1; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] == clickedSeat) {
                    rowIndex = row;
                    colIndex = col;
                    break;
                }
            }
        }

        Image image = clickedSeat.getImage();
        if (image != occupiedImage) {
            seats[rowIndex][colIndex].setImage(image == freeImage ? takenImage : freeImage);
        }
    }
}
