package com.example.user;

import co.paralleluniverse.fibers.Suspendable;
import com.example.core.helpers.Commands;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.StateAndContract;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.flows.*;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;
import com.example.flow.BaseFlow;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static net.corda.core.contracts.ContractsDSL.requireThat;

public abstract class FlowUser {

    @InitiatingFlow
    @StartableByRPC
    public static class Initiator extends BaseFlow {

        private final String cor_id;
        private final String cor_name;
        private final String phone;
        private final String cor_bill_1;
        private final String cor_bill_2;
        private final String city;

        private final ProgressTracker.Step GENERATING_TRANSACTION = new ProgressTracker.Step("Generating transaction based on new IOU.");
        private final ProgressTracker.Step VERIFYING_TRANSACTION = new ProgressTracker.Step("Verifying contract constraints.");
        private final ProgressTracker.Step SIGNING_TRANSACTION = new ProgressTracker.Step("Signing transaction with our private key.");
        private final ProgressTracker.Step GATHERING_SIGS = new ProgressTracker.Step("Gathering the counterparty's signature.") {
            @Override
            public ProgressTracker childProgressTracker() {
                return CollectSignaturesFlow.Companion.tracker();
            }
        };
        private final ProgressTracker.Step FINALISING_TRANSACTION = new ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
            @Override
            public ProgressTracker childProgressTracker() {
                return FinalityFlow.Companion.tracker();
            }
        };

        // The progress tracker checkpoints each stage of the flow and outputs the specified messages when each
        // checkpoint is reached in the code. See the 'progressTracker.currentStep' expressions within the call()
        // function.
        private final ProgressTracker progressTracker = new ProgressTracker(
                GENERATING_TRANSACTION,
                VERIFYING_TRANSACTION,
                SIGNING_TRANSACTION,
                GATHERING_SIGS,
                FINALISING_TRANSACTION
        );


        public Initiator(String cor_id, String cor_name, String phone, String cor_bill_1, String cor_bill_2, String city) {
            this.cor_id = cor_id;
            this.cor_name = cor_name;
            this.phone = phone;
            this.cor_bill_1 = cor_bill_1;
            this.cor_bill_2 = cor_bill_2;
            this.city = city;
        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {
            final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);
            Set<Party> otherParties = this.getAllPartiesWithoutMe();

            // Шаг 1
            progressTracker.setCurrentStep(GENERATING_TRANSACTION);
            // Генерация неподписанной транзакции
            Party currentParty = getServiceHub().getMyInfo().getLegalIdentities().get(0);
            Set<Party> allParties = new HashSet<>();
            allParties.add(currentParty);
            allParties.addAll(otherParties);
            StateUser state = new StateUser(cor_id, cor_name, phone, cor_bill_1, cor_bill_2, city,  allParties, new UniqueIdentifier());

            final Command<Commands.Create> txCommand = new Command<>(
                    new Commands.Create(),
                    state.getParticipants().stream().map(AbstractParty::getOwningKey).collect(Collectors.toList())
            );
            final TransactionBuilder txBuilder = new TransactionBuilder(notary).withItems(
                    new StateAndContract(state, ContractUser.DOC_CONTRACT_ID), txCommand
            );

            // Шаг 2
            progressTracker.setCurrentStep(VERIFYING_TRANSACTION);
            // Валидация транзакции
            txBuilder.verify(getServiceHub());

            // Шаг 3
            progressTracker.setCurrentStep(SIGNING_TRANSACTION);
            // Подпись
            final SignedTransaction partialSignedTx = getServiceHub().signInitialTransaction(txBuilder);

            Set<FlowSession> flowSessions = new HashSet<>();

            for (Party otherParty : otherParties) {
                FlowSession otherPartySession = initiateFlow(otherParty);
                flowSessions.add(otherPartySession);
            }

            // Шаг 4
            progressTracker.setCurrentStep(GATHERING_SIGS);
            // Сбор подписей
            final SignedTransaction fullySignedTx = subFlow(
                    new CollectSignaturesFlow(partialSignedTx, flowSessions, CollectSignaturesFlow.Companion.tracker()));

            // Шаг 5
            progressTracker.setCurrentStep(FINALISING_TRANSACTION);
            // Нотаризируем и записываем в хранилища всех участников
            return subFlow(new FinalityFlow(fullySignedTx));
        }

        @Override
        public ProgressTracker getProgressTracker() {
            return progressTracker;
        }
    }

    @InitiatedBy(Initiator.class)
    public static class Acceptor extends FlowLogic<SignedTransaction> {

        private final FlowSession otherPartyFlow;

        public Acceptor(FlowSession otherPartyFlow) {
            this.otherPartyFlow = otherPartyFlow;
        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {
            class SignTxFlow extends SignTransactionFlow {
                private SignTxFlow(FlowSession otherPartyFlow, ProgressTracker progressTracker) {
                    super(otherPartyFlow, progressTracker);
                }

                @Override
                protected void checkTransaction(SignedTransaction stx) {
                    requireThat(require -> {
                        ContractState output = stx.getTx().getOutputs().get(0).getData();
                        require.using("This must be an DocState transaction.", output instanceof StateUser);
                        return null;
                    });
                }
            }

            return subFlow(new SignTxFlow(otherPartyFlow, SignTransactionFlow.Companion.tracker()));
        }
    }
}