package com.example.user;

import com.example.DTO.UserDTO;
import net.corda.core.contracts.LinearState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.serialization.CordaSerializable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import org.jetbrains.annotations.NotNull;
import com.example.schema.DocSchemaV2;

import java.util.List;
import java.util.Set;

@CordaSerializable

public class StateUser implements LinearState, QueryableState{
    private final UniqueIdentifier linearId;
    private final String cor_id;
    private final String cor_name;
    private final String phone;
    private final String cor_bill_1;
    private final String cor_bill_2;
    private final String city;
    private final Set<Party> parties;



    public StateUser(String cor_id, String cor_name, String phone, String cor_bill_1, String cor_bill_2, String city, Set<Party> parties, UniqueIdentifier linearId){
        this.linearId = linearId;
        this.cor_id = cor_id;
        this.cor_name = cor_name;
        this.phone = phone;
        this.cor_bill_1 = cor_bill_1;
        this.cor_bill_2 = cor_bill_2;
        this.city = city;
        this.parties = parties;
    }


    public String getCor_id(){
        return  cor_id;
    }

    public String getCor_name(){
        return cor_name;
    }

    public String getPhone(){
        return phone;
    }

    public String getCor_bill_1(){
        return cor_bill_1;
    }

    public String getCor_bill_2(){
        return cor_bill_2;
    }

    public String getCity() {return city;}

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new DocSchemaV2());
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof DocSchemaV2) {
            return new DocSchemaV2.PersistentDoc(
                    this.linearId.getId(),
                    this.cor_id,
                    this.cor_name,
                    this.phone,
                    this.cor_bill_1,
                    this.cor_bill_2,
                    this.city
            );
        } else {
            //throw new IllegalAccessException("Неопознанная схема $schema");
            return null;
        }
    }


    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Lists.newArrayList(this.parties);
    }

    public UserDTO createDTO() {
        UserDTO CorStateDTO = new UserDTO();
        CorStateDTO.setLinearId(this.linearId);
        CorStateDTO.setCor_id(this.cor_id);
        CorStateDTO.setCor_name(this.cor_name);
        CorStateDTO.setPhone(this.phone);
        CorStateDTO.setCor_bill_1(this.cor_bill_1);
        CorStateDTO.setCor_bill_2(this.cor_bill_2);
        CorStateDTO.setCity(this.city);
        return CorStateDTO;
    }



    public Set<Party> getParties() {
        return parties;
    }
}
