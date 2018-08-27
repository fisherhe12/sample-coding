package com.github.fisherhe12.serialize.jdk;

import com.github.fisherhe12.common.domain.PersonDTO;

import java.io.*;

/**
 * 普通的序列化与反序列化操作
 *
 * @author fisher
 */
public class SimpleDemo {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("person")));

		PersonDTO personDTO = new PersonDTO("fisher", 22);
		oo.writeObject(personDTO);

		oo.close();

		ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("person")));
		PersonDTO person = (PersonDTO) oi.readObject();
		System.out.println(person);
		System.out.println(person.index);
		oi.close();

	}

}
