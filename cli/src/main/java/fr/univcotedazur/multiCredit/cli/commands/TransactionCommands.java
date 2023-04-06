package fr.univcotedazur.multiCredit.cli.commands;

import fr.univcotedazur.multiCredit.cli.CliContext;
import fr.univcotedazur.multiCredit.cli.model.CliPurchase;
import fr.univcotedazur.multiCredit.cli.model.CliTransaction;
import fr.univcotedazur.multiCredit.cli.model.CliUsePoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class TransactionCommands {

    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;

    public TransactionCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Get all transactions")
    public List<CliTransaction> transactions() {
        CliTransaction[] res = restTemplate.getForObject("/transactions", CliTransaction[].class);

        return List.of(res);
    }

    @ShellMethod("create a purchase ( MemberId, shopID, CreditCardNumber, ItemId, ItemQuantity)")
    public CliPurchase createPurchaseWithCard(  long memberId,long shopId, String creditCardNumber, @ShellOption(arity = 1000) long[] itemIds, @ShellOption(arity = 1000) int[] itemQuantities)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CliPurchase cliPurchase = new CliPurchase( memberId,shopId,creditCardNumber, itemIds, itemQuantities);
        cliPurchase.setDate(LocalDate.now().format(formatter));
        return restTemplate.postForObject("/transactions/purchase/creditCard", cliPurchase, CliPurchase.class);
    }

    @ShellMethod("create a purchase (MemberId, shopID,ItemId, ItemQuantity)")
    public CliPurchase createPurchaseWithCash(long memberId,long shopId, @ShellOption(arity = 1000) long[] itemIds, @ShellOption(arity = 1000) int[] itemQuantities)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CliPurchase cliPurchase = new CliPurchase( memberId,shopId, itemIds, itemQuantities);
        cliPurchase.setDate(LocalDate.now().format(formatter));
        return restTemplate.postForObject("/transactions/purchase/cash", cliPurchase, CliPurchase.class);
    }

    @ShellMethod("create a purchase (MemberId, shopID, ItemId, ItemQuantity)")
    public CliPurchase createPurchaseWithMembershipCard(long memberId,long shopId, @ShellOption(arity = 1000) long[] itemIds, @ShellOption(arity = 1000) int[] itemQuantities)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CliPurchase cliPurchase = new CliPurchase( memberId,shopId, itemIds, itemQuantities);
        cliPurchase.setDate(LocalDate.now().format(formatter));
        return restTemplate.postForObject("/transactions/purchase/membershipCard", cliPurchase, CliPurchase.class);
    }

    @ShellMethod("spend points for a gift (MemberId, shopID, giftId)")
    public CliUsePoints spendPointsForGift(long memberId,long shopId, long giftId)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CliUsePoints cliUsePoints = new CliUsePoints( memberId,shopId, giftId);
        cliUsePoints.setDate(LocalDate.now().format(formatter));
        return restTemplate.postForObject("/transactions/UsePoints", cliUsePoints, CliUsePoints.class);
    }



}
