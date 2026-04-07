package org.numahop.numahop.domain.user;

import org.numahop.numahop.domain.AbstractDomainObject;
import jakarta.persistence.*;

/**
 * Tableau de bord d'un usager
 */
@Entity
@Table(name = Dashboard.TABLE_NAME)
public class Dashboard extends AbstractDomainObject {

	/**
	 * Table de l'entité
	 */
	public static final String TABLE_NAME = "user_dashboard";

	/**
	 * Le tableau de bord sous forme de string
	 */
	@Column(name = "dashboard", columnDefinition = "longtext")
	private String dashboard;

	public String getDashboard() {
		return dashboard;
	}

	public void setDashboard(final String dashboard) {
		this.dashboard = dashboard;
	}

}
