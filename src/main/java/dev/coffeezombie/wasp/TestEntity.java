package dev.coffeezombie.wasp;

import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class TestEntity {

    private Long id;

    public TestEntity(){
        var m = new ModelMapper();
    }

}
