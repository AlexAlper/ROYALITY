package com.example.company;

import com.example.core.helpers.Commands;
import com.example.core.helpers.IterableHelper;
import com.example.core.helpers.StringHelper;
import net.corda.core.contracts.Contract;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.transactions.LedgerTransaction;

import java.util.List;
import java.util.stream.Collectors;
import static net.corda.core.contracts.ContractsDSL.requireThat;

public class ContractOffer implements Contract {
    public static final String DOC_CONTRACT_ID = "com.example.company.ContractOffer";

    @Override
    public void verify(LedgerTransaction tx) throws IllegalArgumentException {
        final List<CommandWithParties<CommandData>> commands = tx.getCommands().stream().filter(
                it -> it.getValue() instanceof Commands
        ).collect(Collectors.toList());
        final CommandWithParties<CommandData> command = IterableHelper.onlyElementOf(commands);

        if (command.getValue() instanceof Commands.Create) {
            final StateOffer state = tx.outputsOfType(StateOffer .class).get(0);
            // final CommandWithParties<Commands.Create> command = requireSingleCommand(tx.getCommands(), Commands.Create.class);
            requireThat(requirements -> {

                requirements.using(" Поле ID не заполнено ",
                        !StringHelper.isEmptyOrWhitespace(state.getId()));

                return null;
            });
        } else if (command.getValue() instanceof Commands.Edit) {
            final StateOffer  state = tx.outputsOfType(StateOffer .class).get(0);
            requireThat(requirements -> {
                requirements.using("Поле оферта не заполнено ",
                        !StringHelper.isEmptyOrWhitespace(state.getId()));

                return null;
            });
        } else if (command.getValue() instanceof Commands.Delete) {
            requireThat(requirements -> {
                requirements.using("Outputs should not exist", tx.getOutputs().isEmpty());

                return null;
            });

        } else {
            throw new IllegalArgumentException("Command is not defined");
        }
    }
}
