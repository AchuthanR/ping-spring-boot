package com.incedo.ping.admin_service.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.incedo.ping.admin_service.model.User;

public class NotificationTemplateMapping {

	public static final List<String> variables = new ArrayList<>(Arrays.asList("USERNAME", "FIRSTNAME", "LASTNAME", "EMAIL", "ROLE"));
	
	public static String replaceVariables(String line, User user) {
		for (String variable : variables) {
			String value = "";
			if ("USERNAME".equals(variable)) {
				value = user.getUsername();
			}
			else if ("FIRSTNAME".equals(variable)) {
				value = user.getFirstName();
			}
			else if ("LASTNAME".equals(variable)) {
				value = user.getLastName();
			}
			else if ("EMAIL".equals(variable)) {
				value = user.getEmail();
			}
			else if ("ROLE".equals(variable)) {
				value = user.getRole().toLowerCase();
			}
			line = line.replaceAll("\\$\\$" + variable + "\\$\\$", value);
		}
		return line;
	}
	
}
