package fr.univcotedazur.simpletcfs.cli.commands;


import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliParking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class ParkingCommands {
    public static final String BASE_URI = "/parking";

    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;
    public ParkingCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("use parking time (parking CAR_REGISTRATION_NUMBER MAIL PARKING_SPOT)")
    public CliParking startParking(String carRegistrationNumber, String mail, int parkingSpotNumber)
    {
        CliParking res = restTemplate.postForObject(BASE_URI + "/parking",new CliParking(carRegistrationNumber,mail,parkingSpotNumber), CliParking.class);
        return res;
    }
}
