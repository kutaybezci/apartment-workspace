package com.kutaybezci.apartment.shell.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.CellMatcher;
import org.springframework.shell.table.SimpleHorizontalAligner;
import org.springframework.shell.table.SimpleVerticalAligner;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import com.kutaybezci.apartment.core.bl.PersonBo;
import com.kutaybezci.apartment.core.bl.dto.Gender;
import com.kutaybezci.apartment.core.bl.dto.Person;

@ShellComponent
public class PersonCommand {
	private static final String UNTOUCHED_ARG = "*";
	@Autowired
	private PersonBo personBo;

	public static Table table(String data[][]) {
		TableModel model = new ArrayTableModel(data);
		TableBuilder tableBuilder = new TableBuilder(model);
		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 2; c++) {
				tableBuilder.on(at(r, c)).addAligner(SimpleHorizontalAligner.values()[c]);
				tableBuilder.on(at(r, c)).addAligner(SimpleVerticalAligner.values()[r]);
			}
		}
		return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
	}

	public static CellMatcher at(final int theRow, final int col) {
		return new CellMatcher() {
			@Override
			public boolean matches(int row, int column, TableModel model) {
				return row == theRow && column == col;
			}
		};
	}

	private String[][] convert(List<Person> personList) {
		String[][] data = new String[personList.size() + 1][7];
		data[0][0] = "id";
		data[0][1] = "name";
		data[0][2] = "M/F";
		data[0][3] = "birth";
		data[0][4] = "phone";
		data[0][5] = "email";
		data[0][6] = "active";
		for (int r = 1; r <= personList.size(); r++) {
			Person person = personList.get(r - 1);
			data[r][0] = person.getPersonCode();
			data[r][1] = person.getFullName();
			data[r][2] = person.getGender() == null ? "" : person.getGender().getCode();
			data[r][3] = person.getBirthDate() == null ? "" : person.getBirthDate().toString();
			data[r][4] = person.getPhone();
			data[r][5] = person.getEmail();
			data[r][6] = person.isActive() ? "T" : "F";
		}
		return data;
	}

	@ShellMethod("Create new person")
	public String createPerson(String fullname, @ShellOption(defaultValue = "") String email,
			@ShellOption(defaultValue = "", help = "non numeric characters will be ignored") String phone,
			@ShellOption(defaultValue = "", help = "DD.MM.YYYY") String birthDate,
			@ShellOption(defaultValue = "", help = "(M)ale, (F)emale") String gender) throws ParseException {
		Person person = new Person();
		person.setFullName(fullname);
		person.setEmail(email);
		person.setPhone(phone);
		person.setGender(Gender.valueOf(gender));
		person.setActive(true);
		if (!StringUtils.isBlank(birthDate)) {
			person.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(birthDate));
		}
		String personCode = personBo.create(person);
		return String.format("New person created with id:%s", personCode);
	}

	@ShellMethod("Update existing person")
	public String updatePerson(String personCode, @ShellOption(defaultValue = "*") String fullname,
			@ShellOption(defaultValue = "*") String email,
			@ShellOption(defaultValue = "*", help = "non numeric characters will be ignored") String phone,
			@ShellOption(defaultValue = "*", help = "DD.MM.YYYY") String birthDate) throws ParseException {
		Person person = personBo.getPersonByCode(personCode);
		if (!UNTOUCHED_ARG.equals(fullname)) {
			person.setFullName(fullname);
		}
		if (!UNTOUCHED_ARG.equals(email)) {
			person.setEmail(email);
		}
		if (!UNTOUCHED_ARG.equals(phone)) {
			person.setPhone(phone);
		}
		if (!UNTOUCHED_ARG.equals(birthDate)) {
			if (!StringUtils.isBlank(birthDate)) {
				person.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(birthDate));
			} else {
				person.setBirthDate(null);
			}
		}
		try {
			personBo.update(person);
		} catch (Exception e) {
			return e.getMessage();
		}
		return String.format("Person updated");
	}

	@ShellMethod("Query with partial name")
	public Table queryName(String partialname) {
		List<Person> personList = personBo.getPersonByName(partialname);
		String[][] data = convert(personList);
		return table(data);
	}

}
