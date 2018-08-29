package com.example.schema;
import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import com.example.schema.IOUSchema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

public class DocSchemaV2 extends MappedSchema {

    public DocSchemaV2() {
        super(IOUSchema.class, 1, ImmutableList.of(com.example.schema.DocSchemaV2.PersistentDoc.class));
    }

    @Entity
    @Table(name = "doc_states")
    public static class PersistentDoc extends PersistentState {
        @Column(name = "linear_id")
        private final UUID linearId;
        @Column(name = "cor_id")
        private final String cor_id;
        @Column(name = "cor_name")
        private final String cor_name;
        @Column(name = "phone")
        private final String phone;
        @Column(name = "cor_bill_1")
        private final String cor_bill_1;
        @Column(name = "cor_bill_2")
        private final String cor_bill_2;
        @Column(name = "city")
        private final String city;


        public PersistentDoc(UUID linearId, String cor_id,
                             String cor_name, String phone, String cor_bill_1, String cor_bill_2, String city
                             ) {
            this.linearId = linearId;
            this.cor_id = cor_id;
            this.cor_name = cor_name;
            this.phone = phone;
            this.cor_bill_1 = cor_bill_1;
            this.cor_bill_2 = cor_bill_2;
            this.city = city;
        }

        //region Геттеры
        public String getCor_id() {
            return cor_id;
        }

        public String getCor_name() {
            return cor_name;
        }

        public String getPhone() {
            return phone;
        }


        public String getCor_bill_1() {
            return cor_bill_1;
        }


        public String getCor_bill_2() {
            return cor_bill_2;
        }

        public String getCity() {return city;}
        //endregion
    }
}