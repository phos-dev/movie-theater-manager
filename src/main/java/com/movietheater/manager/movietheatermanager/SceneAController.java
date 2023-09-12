package com.movietheater.manager.movietheatermanager;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class SceneAController implements Observer {

    TicketController data = TicketController.getInstance();

    @FXML
    private ImageView seatA1, seatA2, seatA3, seatB1, seatB2, seatB3, seatC1, seatC2, seatC3;

    private Image freeImage, occupiedImage, takenImage;

    private ImageView[][] seats;

    public void initialize() {
        occupiedImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("unavailable_seat.png")), 40, 40, false, false);
        freeImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("seat.png")), 40, 40, false, false);
        takenImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("seat_taken.png")), 40, 40, false, false);
        data.addObserver(this);
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
    public void finish() {
        Boolean[][] seatsToEdit = new Boolean[][]{
                        {false, false, false},
                        {false, false, false},
                        {false, false, false}
                };
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                seatsToEdit[row][col] = seats[row][col].getImage() == takenImage;
            }
        }

        data.finish(seatsToEdit, "A");
    }



    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TicketController && arg instanceof Boolean[][]) {
            Boolean[][] updatedSeats = (Boolean[][]) arg;

            for (int row = 0; row < seats.length; row++) {
                for (int col = 0; col < seats[row].length; col++) {
                    if (updatedSeats[row][col]) {
                        seats[row][col].setImage(occupiedImage);
                    } else {
                        seats[row][col].setImage(freeImage);
                    }
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
