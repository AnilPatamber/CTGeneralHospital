package com.citiustech.usermanagement.utililty;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;

import com.citiustech.usermanagement.entity.Patient;
import com.citiustech.usermanagement.entity.Person;

public class CustomIdGenerator implements IdentifierGenerator {

	@Value("${entity.patient.idprefix.maxlength}")
	private String idMaxlength;

	@Override

	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		Connection conn = session.connection();

		try {
			Statement statement = conn.createStatement();

			if (object instanceof Patient) {

				String prefix = "patient";

				String query = "select max(patient_id) from patient";

				ResultSet rs = statement.executeQuery(query);

				if (rs.next()) {

					String currentId = rs.getString(1);
					int noOfZerosToAdd = 0;
					String nextId = "";

					if (currentId == null) {
						noOfZerosToAdd = Integer.parseInt(idMaxlength);
						nextId = prefix + getNextId("0", noOfZerosToAdd - 1);
					} else {
						currentId = currentId.replace(prefix, "");
						noOfZerosToAdd = Integer.parseInt(idMaxlength)
								- String.valueOf(Integer.parseInt(currentId)).length();
						nextId = prefix + getNextId(currentId, noOfZerosToAdd);
					}

					if ((rs.getString(1) != null && nextId != null) && nextId.length() > rs.getString(1).length()) {
						nextId = nextId.replace(prefix + "0", prefix);
					}

					return nextId;

				}

			}
			if (object instanceof Person) {

				String prefix = "person";

				String query = "select max(person_id) from person";

				ResultSet rs = statement.executeQuery(query);

				if (rs.next()) {

					String currentId = rs.getString(1);
					int noOfZerosToAdd = 0;
					String nextId = "";

					if (currentId == null) {
						noOfZerosToAdd = Integer.parseInt(idMaxlength);
						nextId = prefix + getNextId("0", noOfZerosToAdd - 1);
					} else {
						currentId = currentId.replace(prefix, "");
						noOfZerosToAdd = Integer.parseInt(idMaxlength)
								- String.valueOf(Integer.parseInt(currentId)).length();
						nextId = prefix + getNextId(currentId, noOfZerosToAdd);
					}

					if ((rs.getString(1) != null && nextId != null) && nextId.length() > rs.getString(1).length()) {
						nextId = nextId.replace(prefix + "0", prefix);
					}

					return nextId;

				}

			}
		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	public String getNextId(String id, int noOfZerosToAdd) {

		long idL = Long.parseLong(id);
		idL = idL + 1;
		String nextId = String.valueOf(idL);

		for (int i = 0; i < noOfZerosToAdd; i++) {
			nextId = "0" + nextId;
		}

		return nextId;

	}

}
