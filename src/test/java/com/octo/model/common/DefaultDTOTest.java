package com.octo.model.common;

class DefaultDTOTest {

    class TestDTO extends DefaultDTO {
        private String name;

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }
    }
}
