package com.github.fisherhe12.serialize.jdk;

import com.github.fisherhe12.common.domain.PersonDTO;

import java.io.*;

/**
 * 序列化实现深度克隆
 *
 * @author fisher
 */
public class DeepCloneDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PersonDTO personDTO = new PersonDTO("fisher", 100);
        PersonDTO dto = deepClone(personDTO);
        System.out.println(dto);

    }



    public static PersonDTO deepClone(PersonDTO personDTO) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(personDTO);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (PersonDTO) ois.readObject();
    }
    
    
}
