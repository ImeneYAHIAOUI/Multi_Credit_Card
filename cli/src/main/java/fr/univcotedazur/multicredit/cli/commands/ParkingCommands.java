package fr.univcotedazur.multicredit.cli.commands;


import fr.univcotedazur.multicredit.cli.CliContext;
import fr.univcotedazur.multicredit.cli.model.CliParking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class ParkingCommands {
    public static final String BASE_URI = "/parking";
    private final CliContext cliContext;
    @Autowired
    RestTemplate restTemplate;

    public ParkingCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("use parking time (parking CAR_REGISTRATION_NUMBER MAIL PARKING_SPOT)")
    public CliParking startParking(String carRegistrationNumber, String mail, int parkingSpotNumber) {
        return restTemplate.postForObject(BASE_URI, new CliParking(carRegistrationNumber, mail, parkingSpotNumber), CliParking.class);
    }
}
