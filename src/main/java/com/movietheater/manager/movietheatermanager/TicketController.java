package com.movietheater.manager.movietheatermanager;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketController extends Observable {

    public static final TicketController instance = new TicketController();
    private Queue<ReservationRequest> reservationQueue = new LinkedList<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @FXML
    private ImageView seatA1, seatA2, seatA3, seatB1, seatB2, seatB3, seatC1, seatC2, seatC3;

    private Image freeImage, occupiedImage;
    private Boolean[][] seatsOccupied;

    private ImageView[][] seats;

    public TicketController() {
        this.initialize();
    }

    public void initialize() {
        occupiedImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("unavailable_seat.png")), 40, 40, false, false);
        freeImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("seat.png")), 40, 40, false, false);

        seats = new ImageView[][]{
                {seatA1, seatA2, seatA3},
                {seatB1, seatB2, seatB3},
                {seatC1, seatC2, seatC3}
        };

        seatsOccupied = new Boolean[][]{
                {false, false, false},
                {false, false, false},
                {false, false, false}
        };
        for (ImageView[] seat : seats) {
            for (int j = 0; j < seat.length; j++) {
                ImageView imageView = seat[j];
                if (imageView == null) {
                    imageView = new ImageView(freeImage);
                    seat[j] = imageView;
                } else {
                    if (imageView.getImage() == null) {
                        imageView.setImage(freeImage);
                    }
                }
            }
        }

    }

    public static TicketController getInstance() {
        return instance;
    }

    private class ReservationRequest extends Task<Void> {
        private final Boolean[][] seatsToFinish;
        private final String name;

        public ReservationRequest(Boolean[][] seatsToFinish, String name) {
            this.seatsToFinish = seatsToFinish;
            this.name = name;
        }

        @Override
        protected Void call() throws Exception {
            Random random = new Random();
            int minSleepTime = 2000;
            int maxSleepTime = 5000;
            int sleepTime = random.nextInt(maxSleepTime - minSleepTime + 1) + minSleepTime;

            for(ReservationRequest s : reservationQueue) {
                System.out.println(s.name.toString());
            }

            Thread.sleep(sleepTime);

            synchronized (reservationQueue) {
                if (reservationQueue.peek() == this) {
                    for (int i = 0; i < instance.seats.length; i++) {
                        for (int j = 0; j < instance.seats[i].length; j++) {
                            if (seatsToFinish[i][j]) {
                                if (instance.seatsOccupied[i][j]) {
                                    String errorMessage = String.format("Sistema %s - Erro: O lugar %d %d já está ocupado.", name, i, j);
                                    System.out.println(errorMessage);
                                } else {
                                    seats[i][j].setImage(instance.occupiedImage);
                                    instance.seatsOccupied[i][j] = true;
                                }
                            }
                        }
                    }

                    Boolean[][] seatsToEdit = new Boolean[][]{
                            {false, false, false},
                            {false, false, false},
                            {false, false, false}
                    };
                    for (int row = 0; row < instance.seats.length; row++) {
                        for (int col = 0; col < instance.seats[row].length; col++) {
                            seatsToEdit[row][col] = instance.seats[row][col].getImage() == occupiedImage;
                        }
                    }

                    setChanged();
                    notifyObservers(seatsToEdit);
                    reservationQueue.poll();
                }
            }
            return null;
        }
    }

    public void finish(Boolean[][] seatsToFinish, String name) {
        ReservationRequest request = new ReservationRequest(seatsToFinish, name);

        synchronized (reservationQueue) {
            reservationQueue.add(request);
        }

        executorService.submit(request);
    }
}
