package com.example.hbv4d;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class ToursController {
    public Label fxLoggedIn;
    public TextField searchBar;
    public ListView<Tour> tourList = new ListView<>();
    @FXML
    private ComboBox<String> priceFilter;
    @FXML
    private ComboBox<String> cityFilter;
    @FXML
    private DatePicker dateFilter;


    public AnchorPane infoPane;
    public Text descriptionText;
    public Label descriptionTitle;
    public Label dateLabel;

    private final ObservableList<Tour> tours = FXCollections.observableArrayList();
    private FilteredList<Tour> filteredTours;


    @FXML
    public void initialize(){

        // Mock data
        tours.addAll(
                new Tour(1, "Golden Circle", "Visit the golden circle in one day!", 12000, LocalDate.of(2024, 3, 10),"Reykjavik"),
                new Tour(2, "Northern Lights", "See the northen lights in the north", 8000, LocalDate.of(2024, 3, 15),"Akureyri"),
                new Tour(3, "Blue Lagoon","Spend a day in the blue lagoon", 35000, LocalDate.of(2024, 3, 20), "Reykjavik"),
                new Tour(4, "Volcano Hike","Volcano hike in the south", 9000, LocalDate.of(2024, 3, 12), "Vik")
        );

        filteredTours = new FilteredList<>(tours, p -> true);
        tourList.setItems(filteredTours);

        searchBar.textProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        priceFilter.valueProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        cityFilter.valueProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        dateFilter.valueProperty().addListener((obs, oldValue, newValue) -> applyFilters());

        String user = User.getLoggedIn();
        if (user != null) {
            fxLoggedIn.setText("User: " + user);
        }
    }

    private void getDescription(Tour tour){
        descriptionTitle.setText(tour.getTourName());
        dateLabel.setText(tour.getDate().toString().formatted("%d/%m/%Y"));
        descriptionText.setText(tour.getDescription());
        if (infoPane.isVisible()) {
            return;
        }
        infoPane.setVisible(true);
    }

    public void onClickedTour(){
        Tour selectedTour = tourList.getSelectionModel().getSelectedItem();
        if(selectedTour == null){
            return;
        }
        getDescription(selectedTour);
    }

    private void applyFilters() {
        String searchText = searchBar.getText().toLowerCase();
        String selectedPrice = priceFilter.getValue();
        String selectedCity = cityFilter.getValue();
        LocalDate selectedDate = dateFilter.getValue();

        filteredTours.setPredicate(tour -> {
            if (!searchText.isEmpty()) {
                if (!tour.getTourName().toLowerCase().contains(searchText)) {
                    return false;
                }
            }

            if (selectedPrice != null) {
                switch (selectedPrice) {
                    case "Under 10,000kr":
                        if (tour.getPrice() >= 10000) return false;
                        break;
                    case "Under 20,000kr":
                        if (tour.getPrice() >= 20000) return false;
                        break;
                    case "Under 30,000kr":
                        if (tour.getPrice() >= 30000) return false;
                        break;
                    case "Under 40,000kr":
                        if (tour.getPrice() >= 40000) return false;
                        break;
                }
            }

            if (selectedCity != null && !selectedCity.equals("All Cities")) {
                if (!tour.getCity().equalsIgnoreCase(selectedCity)) {
                    return false;
                }
            }

            if (selectedDate != null) {
                if (!tour.getDate().equals(selectedDate)) {
                    return false;
                }
            }

            return true;
        });
    }
}
