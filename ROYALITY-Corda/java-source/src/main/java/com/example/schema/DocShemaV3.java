package com.example.schema;
import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

public class DocShemaV3 extends MappedSchema {

    public DocShemaV3() { super(IOUSchema.class, 1, ImmutableList.of(com.example.schema.DocShemaV3.PersistentDoc.class)); }

    @Entity
    @Table(name = "doc_states")
    public static class PersistentDoc extends PersistentState {
        @Column(name = "linear_id")
        private final UUID linearId;
        @Column(name = "id")
        private final String id;
        @Column(name = "phone")
        private final String phone;
        @Column(name = "city")
        private final String city;
        @Column(name = "name")
        private final String name;


        public PersistentDoc(UUID linearId, String id, String phone, String city, String name
        ) {
            this.linearId = linearId;
            this.id = id;
            this.phone = phone;
            this.city = city;
            this.name = name;

        }



        public String getId(){
            return  id;
        }

        public String getPhone() {return phone;}

        public String getCity(){return city;}

        public String getName(){return name;}

    }
}