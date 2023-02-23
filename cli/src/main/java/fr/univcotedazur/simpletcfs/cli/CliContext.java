package fr.univcotedazur.simpletcfs.cli;

import fr.univcotedazur.simpletcfs.cli.model.CliAccount;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CliContext {

    @Getter
    private Map<String, CliMember> memberAccounts;


    public CliContext()
    {
        this.memberAccounts = new HashMap<>();
    }

    @Override
    public String toString() {
        return memberAccounts.keySet().stream()
                .map(key -> key + "=" + memberAccounts.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
