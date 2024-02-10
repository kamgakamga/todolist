package com.if5.todolist.models.enumerations;

public enum Sexe {
	
	HOMME(0, "Homme"),
	FEMME(1, "Femme");
	
	private final String value;

	public String getValue() {
		return value;
	}

	private Sexe(int key, String value) {
		this.value = value;
	}
	

}
