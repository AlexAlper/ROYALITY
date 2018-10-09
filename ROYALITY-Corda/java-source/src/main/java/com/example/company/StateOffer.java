package com.example.company;



import com.example.DTO.OfferDTO;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;
import net.corda.core.serialization.CordaSerializable;
import org.jetbrains.annotations.NotNull;
import com.example.schema.DocShemaV3;

import java.util.List;
import java.util.Set;

@CordaSerializable

public class StateOffer implements LinearState, QueryableState {
    private final UniqueIdentifier linearId;
    private final String id;
    private final String phone;
    private final String city;
    private final String name;
    private final Set<Party> parties;


    public  StateOffer( String id, String phone, String city, String name, Set<Party> parties, UniqueIdentifier linearId){
        this.linearId = linearId;
        this.id = id;
        this.phone = phone;
        this.city = city;
        this.name = name;
        this.parties = parties;

    }

    public String getId(){
        return  id;
    }

    public String getPhone() {return phone;}

    public String getCity(){return city;}

    public String getName(){return name;}


    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    @NotNull
    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new DocShemaV3());
    }

    @NotNull
    @Override
    public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof DocShemaV3) {
            return new DocShemaV3.PersistentDoc(
                    this.linearId.getId(),
                    this.id,
                    this.phone,
                    this.city,
                    this.name
            );
        } else {

            return null;
        }
    }


    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Lists.newArrayList(this.parties);
    }

    public OfferDTO createDTO() {
        OfferDTO CorStateDTO = new OfferDTO();
        CorStateDTO.setLinearId(this.linearId);
        CorStateDTO.setId(this.id);
        CorStateDTO.setPhone(this.phone);
        CorStateDTO.setCity(this.city);
        CorStateDTO.setName(this.name);
        return CorStateDTO;
    }

    public Set<Party> getParties() {
        return parties;
    }
}